#basestring //"""sdl module to expose functions to be consumed by test scripts."""
import kindle
import time


class Scanner(object):
    """sdl class."""

    def __init__(self, device):
        """__init__ for sdl class."""
        self.device = device
        self.kindle = kindle.Kindle()
        self.kindle.brightness(25)

    def scan(self, symbology_num, subtest, data, size, use, tbarcode_option=None,
             testAppActivity="sdl.android.testapp/.SW_Decode_Android_TestApp_Activity",
             testAppService='sdl.android.testapp/.NextSubtestService'):

	msg = 'lipc-set-prop com.lab126.powerd flIntensity 25\r'
        self.kindle.kindle_send(msg, True)

        """Scan barcode."""
        self.device.server.adb.cmd('logcat -c')
        self.use = use
        if (use == 1):
            self.kindle.image(symbology_num + ".png")
        else:
            self.kindle.barcode(symbology_num, data, size, tbarcode_option)
        self.device.shell("am start -n " + testAppActivity)
        time.sleep(5)

        self.device.wait.update(timeout=5000)
        self.device.server.adb.cmd('logcat -c') #additional
        self.device.shell('am startservice -n ' + testAppService + ' -e "subtest" ' + str(subtest))

        barcodes = None

        while True:
            
            barcodes = self.device.server.adb.cmd('logcat -d | grep -i', 'SDL_SCAN').communicate()[0]
            if "Scan_Complete" in barcodes:
                break
        print "the::::"+ barcodes
        
        return [barcode.split(': ')[1] for barcode in list(barcodes.split("\n"))[:-2]]



    def setparam(self, subtest, testAppActivity="sdl.android.testapp/.SW_Decode_Android_TestApp_Activity",
                 testAppService='sdl.android.testapp/.NextSubtestService'):
        """ Method to set any properties of Barcode reader """
        self.device.shell('am start -n' + testAppActivity)
        self.device.wait.update(timeout=5000)
        
        self.device.shell('am startservice -n ' + testAppService + ' -e "subtest" ' + str(subtest))
