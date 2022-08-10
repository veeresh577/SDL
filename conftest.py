"""pytest fixtures for sdl automation."""

import pytest
import os
from Oreo_W import Device
import sys
import subprocess
import datetime
import shlex



__author__ = 'vgm368'


@pytest.fixture(scope="session")
def serial(request):
    """Fixture for getting serial number of android device."""
    return request.config.getoption("--serial")


@pytest.fixture(scope="session", autouse=True)
def device(serial):
    """Fixture for getting  android device instance."""
    if serial:
        return Device(serial)
    else:
        try:
            return Device()
        except EnvironmentError as env:
            pytest.exit("No Android device attached, please check if adb devices detects the device")
            print env


'''
@pytest.fixture(scope="session")
def kindle():
    """Fixture for getting kindle device instance."""
    return kindle.Kindle()
'''


@pytest.fixture(scope="session", autouse=True)
def imeOnOff(request, device):
    """Trun OFF Input Method on/off based on --ime command line argument."""

    def reset_keyboard():
        device.shell("ime set com.android.inputmethod.latin/.LatinIME "
                     "mSettingsActivityName=com.android.inputmethod.latin.SettingsActivity")

    if request.config.getoption("--ime"):
        autoKeyboardClose(device)
    else:
        disableKeyboard(device)
    request.addfinalizer(reset_keyboard)


def disableKeyboard(device):
    """Disable default keyboard and activate Null keyboard."""
    apk = str(pytest.config.rootdir) + os.sep + 'apk' + os.sep + 'com.apedroid.hwkeyboardhelperfree'
    if not device.check(apk):
        device.install(apk)
    device.shell('ime set com.apedroid.hwkeyboardhelperfree/.HWKeyboardHelperIME '
                 'mSettingsActivityName=com.apedroid.hwkeyboardhelperfree.Preferences')


def autoKeyboardClose(device):
    """Auto close keyboard if default keyboard is active."""

    def keyboardClose():
        if device.shell("dumpsys window windows | grep mInputMethodWindow")[0] != '':
            device.press.back()  # to close the keyboard

    device.shell(
        'ime set com.android.inputmethod.latin/.LatinIME '
        'mSettingsActivityName=com.android.inputmethod.latin.SettingsActivity')
    device.handlers.on(keyboardClose)


@pytest.fixture(scope="session", autouse=True)
def auto_force_close(device):
    """Handle all unwanted dialogs i.e.force close, ant etc..."""

    def force_close(device):
        device.log.debug("force_close")
        filename = pytest.logfilename[:-len("_adb.log")]

        if device(textStartsWith="Unfortunately,").exists:
            device.screenshot(filename + "exception.png")
            device(text="OK").click.wait()
        elif device(textContains="isn't responding.").exists and device(textContains="Do you want to close it?").exists:
            device.screenshot(filename + "anr.png")
            device(text="OK").click.wait()

        return True

    device.handlers.on(force_close)


def pytest_addoption(parser):
    """Pytest command line parameters declaration."""
    parser.addoption("--serial", action="store", default=None, help="Device Serial Number")

    parser.addoption("--ime", action="store_true", help="Run tests with keyboard ON")


@pytest.fixture(scope="function", autouse=True)
def log(device, logfolder, request):
    """Fixture for capturing adb and dmsg logs automatically for each test case."""
    if device:
        serial = device.server.adb.device_serial()
        device.shell("logcat -c")

        testName = logfolder + os.sep + request.node.nodeid.replace('/', '.').replace("::", ".")

        filename1 = testName + "_adb.log"
        cmd = 'adb -s ' + serial + " logcat -v time UIAutomatorStub:S UiAutomatorBridge:S JsonRpcServer:S"
        proc1 = subprocess.Popen(shlex.split(cmd), stdout=open(filename1, 'w'), stderr=subprocess.PIPE,
                                 close_fds=sys.platform not in ['win32', 'cygwin'])

        pytest.logfilename = filename1

        debug = device.shell("getprop ro.build.type")[0].strip() != "user"
        if debug:
            filename2 = testName + "_dmsg.log"
            cmd = 'adb -s ' + serial + " shell cat /proc/kmsg"
            proc2 = subprocess.Popen(shlex.split(cmd), stdout=open(filename2, 'w'), stderr=subprocess.PIPE,
                                     close_fds=sys.platform not in ['win32', 'cygwin'])

        def closeLogs():
            try:
                proc1.kill()
                proc1.wait()
                if debug:
                    proc2.kill()
                    proc2.wait()
            except Exception as ex:
                print(ex)

            if request.node.rep_setup.failed:
                if request.node.rep_call.failed:
                    adbSuffixLen = len("_adb.log")
                    filename = pytest.logfilename[:-adbSuffixLen]
                    device.screenshot(filename + "_failed.png")
                    os.rename(filename1, filename1[:-adbSuffixLen] + "_failed.log")
                    if debug:
                        os.rename(filename2, filename + "_failed.log")

        request.addfinalizer(closeLogs)


def pytest_namespace():
    """Creating logfolder,logfilename variables in pytest namespace"""
    return {'logfolder': "logs", 'logfilename': None}


def pytest_cmdline_preparse(args):
    """Prase command line arguments to create logs directory with time stamp to store --resultlog, --junitxml and
    --html logs """

    timestamp = datetime.datetime.now().strftime("%d%m%Y_%H%M%S.%f")
    createfolder = pytest.logfolder + os.sep + timestamp

    if not os.path.exists(createfolder):
        os.makedirs(createfolder)

    pytest.logfolder = createfolder


    for report in ('--resultlog', '--junitxml', '--html'):
        arg = [arg for arg in args if report in arg]
        if arg:
            arg = arg[0]
            args.remove(arg)
            index = arg.rfind("=") + 1
            args[:] = [arg[:index] + createfolder + os.sep + arg[index:]] + args


@pytest.fixture(scope="session", autouse=True)
def logfolder(device):
    """"Creates a device serial number folder under logfolder."""
    logfolder = pytest.logfolder + os.sep + device.server.adb.device_serial()
    os.makedirs(logfolder)
    return logfolder


@pytest.hookimpl(tryfirst=True, hookwrapper=True)
def pytest_runtest_makereport(item, call):
    """Pytest hook to generate reports."""
    # execute all other hooks to obtain the report object
    outcome = yield
    rep = outcome.get_result()

    # set a report attribute for each phase of a call, which can
    # be "setup", "call", "teardown"
    setattr(item, "rep_" + rep.when, rep)


@pytest.fixture
def report_doc_string(request):
    """Pytest fixture to generate test case docstring information."""
    yield

    if request.node.rep_call:

        if request.node.nodeid._obj.__doc__:
            request.node.nodeid = request.node.nodeid + 3 * '-' + request.node.nodeid.__doc__.strip()
        else:
            request.node.nodeid = request.node.nodeid + 3 * '-' + "Test Case Description is missing, please add"


@pytest.fixture(scope="function", params=[5000], autouse=True)  # 5000 - restoreDefaults
def restoreDefaults(request, device):
    def func():
        device.scanner.setparam(request.param)

    request.addfinalizer(func)


@pytest.fixture(scope="session", params=[5001], autouse=True)  # 5001 - set Standard mode
def Standardize(request, device):
    def func():
        device.scanner.setparam(request.param)


@pytest.fixture(scope="function", autouse=True)  # close app
def Close_test_app(request, device):
    # def func():
    #     device(text = 'EXIT').click.wait()
    # request.addfinalizer(func)

    def func():
        serial = device.server.adb.device_serial()
        cmd = 'adb -s ' + serial + " shell am force-stop sdl.android.testapp1"
        proc1 = subprocess.Popen(shlex.split(cmd), stdout=-1, stderr=subprocess.PIPE,
                                 close_fds=sys.platform not in ['win32', 'cygwin'])
        proc1.kill()
        proc1.wait()

    request.addfinalizer(func)
