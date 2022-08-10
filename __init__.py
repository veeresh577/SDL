"""SDL Android Device class extending UIAutomator."""
from uiautomator import AutomatorDevice
import subprocess
import os
import time
import shlex
from lib.scanner import Scanner
from lib import kindle

__author__ = 'vgm368'


class Device(AutomatorDevice):
    """SDL Android Device class."""

    def __init__(self, serial=None, local_port=None, adb_server_host=None, adb_server_port=None):
        """__init__ for SDL Device class."""
        super(self.__class__, self).__init__(serial=serial, local_port=local_port, adb_server_host=adb_server_host, adb_server_port=adb_server_port)
        self.scanner = Scanner(self)
        self.kindle = kindle.Kindle()

    def platform(self):
        """Return platform label i.e. API level."""
        version = self.shell('getprop ro.build.version.sdk')[0].strip()

        return version

    def product(self):
        """Return product name."""
        product = self.shell('getprop PN')[0].strip()

        return product[:5]

    def clear_text(self, txtlabel, editlabel):
        """Clear text in Edit field."""
        d1 = self
        while d1(text=txtlabel).right(className="android.widget.EditText").text != editlabel:
            d1(text=txtlabel).right(className="android.widget.EditText").clear_text()

    def reboot(self):
        """Reboot the device"""

        self.adb('reboot')
        self.adb('nodaemon wait-for-device')
        timeout = 600
        while '1' != self.adb.shell('getprop sys.boot_completed')[0].strip() and timeout > 0:
            time.sleep(2)
            timeout -= 0.5

    def install(self, apkname):
        '''Install the apk on the device'''
        #print ("i am in install function")
        
        return self.server.adb.cmd(*("install " + os.path.dirname(os.path.realpath(__file__)) + os.sep + "apk" + os.sep + apkname).split()).communicate()[0]
        #return self.adb("install " + apkname)[0]

    def reinstall(self, apkname):
        '''Re-Install the apk on the device'''

        return self.adb("install -r " + apkname)[0]

    def uninstall(self, pkgname):
        '''Uninstall the package in the device'''

        return self.adb("uninstall " + pkgname)[0]

    def check(self, pkgname):
        '''Check if the package exists in the device, retrun True if exists or else False'''

        return set([pkgname]).issubset(self.adb('shell pm list packages ')[0].replace('package:', '').split())

    def push(self, source, destination):
        '''Push source file/folder to destination file/folder on the device '''

        return self.adb("push " + source + " " + destination)

    def pull(self, source, destination=""):
        '''Pull destination file/folder from device to source file/folder '''

        return self.adb("pull " + source + " " + destination)

    def shell(self, command):
        '''Execute a shell command on the  device '''

        return [x.decode() for x in self.server.adb.cmd(*("-s " + self.server.adb.device_serial() + " shell " + command).split()).communicate()]

    def adb(self, args):
        '''execute adb command on PC'''

        return [x.decode() for x in self.server.adb.cmd(*(("-s " + self.server.adb.device_serial() + " " + args).split())).communicate()]

    def logcat_wait_str(self, line, timeout=3000):
        """Wait for a string in logcat , default timeout is 3000."""
        found = False

        self.server.adb.raw_cmd(" -s " + self.server.adb.device_serial() + " logcat -c ").communicate()

        command = "adb -s " + self.server.adb.device_serial() + " logcat -v time"
        process = subprocess.Popen(shlex.split(command), stdout=subprocess.PIPE)
        while True:
            output = process.stdout.readline()
            if line in output:
                print output.strip()
                found = True
                break
            if output:
                print output.strip()
        process.poll()

        return found
