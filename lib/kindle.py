#!/usr/bin/env python

import serial
import os
import time
import logging
import portdetect
"""
###PSEUDOCODE###
# accept arguments for symbology, data and mil size(optional)
# tbarcode - generate barcode using input
# imagemagick - resize and make correct png format
# uuencode - encode as temp ascii file
# push file - open new file on kindle and write temp ascii file
# uudecode - decode ascii data to temp image
# eips - display image and return status
#######END######
"""

# TODO apply DRY to image and barcode functions
# TODO add some kind of checksum

DISPLAY_SLEEP = .6


class Kindle:
    def __init__(self):
        self.log = logging.getLogger(__name__)
        #   self.port_name = portdetect.kindle()
        self.port_name = '/dev/ttyUSB0'
        self.port = serial.Serial(self.port_name, 115200)
        self.log.info("Kindle port={}".format(self.port_name))
        self.data_matrix_param = False

    # symbologies list
    symbologies = [
        'Not a valid type', 'Code 11', 'Code 2 of 5 Standard', 'Interleaved 2 of 5 Standard', 'Code 2 of 5 IATA', 'Code 2 of 5 Matrix', 'Code 2 of 5 Data Logic', 'Code 2 of 5 Industrial', 'Code 3 of 9 (Code 39)', 'Code 3 of 9 (Code 39) ASCII', 'EAN8',
        'EAN8 - 2 digits add on', 'EAN8 - 5 digits add on', 'EAN13', 'EAN13 - 2 digits add on', 'EAN13 - 5 digits add on', 'EAN128', 'UPC 12 Digits', 'Codabar (2 widths)', 'Reserved', 'Code128', 'Deutsche Post Leitcode', 'Deutsche Post Identcode',
        'ISBN 13 - 5 digits add on', 'ISMN', 'Code 93', 'ISSN', 'ISSN - 2 digits addon', 'Flattermarken', 'GS1 DataBar (RSS-14)', 'GS1 DataBar Limited (RSS Limited)', 'GS1 DataBar Expanded (RSS Expanded)', 'Telepen Alpha', 'UCC128 (= EAN128)', 'UPC A',
        'UPC A - 2 digit add on', 'UPC A - 5 digit add on', 'UPC E', 'UPC E - 2 digit add on', 'UPC E - 5 digit add on', 'USPS PostNet-5 (ZIP 5 digits)', 'USPS PostNet-6 (ZIP 5 digits + check digit)', 'USPS PostNet -9 (ZIP + 4)',
        'USPS PostNet-10 (ZIP + 4 + check digit)', 'USPS PostNet-11 (ZIP + 4 + 2)', 'USPS PostNet -12 (ZIP + 4 + 2+ check digit)', 'Plessey Code', 'MSI Plessey Code', 'SSCC18', 'Reserved', 'LOGMARS', 'Pharmacode One-Track',
        'PZN (Pharma Zentral Nummer Germany)', 'Pharmacode Two-Track', 'Brazilian CEPNet', 'PDF417', 'PDF417 Truncated', 'MaxiCode', 'QR-Code', 'Code128 (Subset A)', 'Code128 (Subset B)', 'Code128 (Subset C)', 'Code 93 Ascii',
        'Australian Post standard customer barcode', 'Australian Post customer barcode 2', 'Australian Post customer barcode 3', 'Australian Post Reply Paid barcode', 'Australian Post Routing barcode', 'Australian Post Redirection barcode',
        'ISBN 13 (=EAN13P5)', 'Royal Mail 4 State customer code (RM4SCC)', 'Data Matrix', 'EAN-14', 'VIN - FIN', 'Codablock-F', 'NVE-18', 'Japanese Postal customer code', 'Korean Postal Authority Code', 'GS1 DataBar Truncated (RSS-14 Truncated)',
        'GS1 DataBar Stacked (RSS-14 Stacked)', 'GS1 DataBar Stacked Omnidirectional (RSS-14 Stacked Omnidirectional)', 'GS1 DataBar Expanded Stacked (RSS Expanded Stacked)', 'Planet Code 12 digits', 'Planet Code 14 digits', 'MicroPDF417',
        'USPS Intelligent Mail Barcode', 'Plessey Code with bidirectional reading support', 'Telepen', 'GS1-128 (EAN-UCC-128)', 'ITF-14', 'KIX', 'Code 32 (Italian Pharmacode)', 'Aztec Code', 'DAFT Code', 'Italian Postal 2 of 5', 'Italian Postal 3 of 9',
        'DPD Code', 'Micro QR-Code', 'HIBC LIC 128', 'HIBC LIC 39', 'HIBC PAS 128', 'HIBC PAS 39', 'HIBC LIC Data Matrix', 'HIBC PAS Data Matrix', 'HIBC LIC QR-Code', 'HIBC PAS QR-Code', 'HIBC LIC PDF417', 'HIBC PAS PDF417', 'HIBC LIC MicroPDF417',
        'HIBC PAS MicroPDF417', 'HIBC LIC Codablock-F', 'HIBC PAS Codablock-F', 'QR-Code 2005', 'PZN8 (Pharma Zentral Nummer Germany 8 digits)', 'Reserved', 'DotCode', 'Han Xin Code', 'USPS Intelligent Mail Package Barcode',
        'Swedish Postal Shipment Item ID Barcode', 'Royal Mail CMDM Mailmark'
    ]

    def kindle_send(self, msg, expect_response):
        self.port.write(msg)
        self.port.flush()

    # time.sleep(WRITE_SLEEP)

    def clear(self):
        """
        Clears the display.
        
        :return: 
        """

        cmd_clear = 'eips -c\r'

        self.log.debug(cmd_clear)
        self.kindle_send(cmd_clear, True)
        self.log.info("display cleared")

        time.sleep(DISPLAY_SLEEP)

    def param(self, data):
        """
        Creates either a Code128 or DataMatrix parameter barcode and displays it.

        Use the data_matrix_param property to set type.

        :param data: Parameter data
        :return:
        """
        if self.data_matrix_param:
            self.barcode(symbology_num=71, data=data, mil=30, tbarcode_option='--DMformat=5', translation=False)
        else:
            self.barcode(symbology_num=20, data='\\212' + data, mil=16, translation=True)

    def image(self, name):
        """
        Display a png image onto kindle.

        :param name: Name of the image without path or extension
        :return:
        """

        # temp files
        temp_dir = "/tmp/"
        temp_uue = "temp.uue"
        temp_dir_uue = temp_dir + temp_uue
        temp_png = "temp.png"
        temp_dir_png = temp_dir + temp_png

        # check if user added .png
        name = str(name).split('.png')[0]

        path = os.path.dirname(os.path.dirname(os.path.realpath(__file__)))
        filename = '{}/images/{}.png'.format(path, name)

        # check if file exists
        if not os.path.isfile(filename):
            raise Exception("File does not exist: {}".format(filename))

        self.log.info("Displaying image: {}".format(name))

        cmd_convert = """convert '{0}' -rotate 90 -define png:color-type=0 -gravity center -background white -extent 1072x1448 '{1}'""".format(filename, temp_dir_png)
        cmd_encode = """uuencode '{0}' '{1}' > '{2}'""".format(temp_dir_png, temp_png, temp_dir_uue)
        cmd_kindle_start = """cat > '{}' \r""".format(temp_uue)
        cmd_kindle_close = '\x03'
        cmd_kindle_decode = "/mnt/us/busybox uudecode {} \r".format(temp_uue)
        cmd_kindle_display = "eips -g {} -f \r".format(temp_png)

        # imagemagick - resize and make correct png format
        self.log.debug(cmd_convert)
        os.system(cmd_convert)

        # uuencode - encode as temp ascii file
        self.log.debug(cmd_encode)
        os.system(cmd_encode)

        # open new file on kindle and write temp ascii file
        file_to_send = open(temp_dir_uue, "rb").read() + '\r'
        self.log.debug("file opened")

        self.kindle_send(cmd_kindle_start, False)
        self.log.debug(cmd_kindle_start)
        self.log.debug("prepared to send")

        self.kindle_send(file_to_send, False)
        self.log.debug("file sent")

        self.kindle_send(cmd_kindle_close, True)
        self.log.debug("file closed")

        # uudecode - decode ascii data to temp image
        self.kindle_send(cmd_kindle_decode, True)
        self.log.debug(cmd_kindle_decode)
        self.log.debug("decoded")

        # eips - display image and return status
        self.kindle_send(cmd_kindle_display, True)
        self.log.debug(cmd_kindle_display)
        self.log.debug("displayed")

        time.sleep(DISPLAY_SLEEP)

    def barcode(self, symbology_num, data, mil, tbarcode_option=None, translation=False):
        # setting values
        temp_dir = '/tmp/'
        temp_uue = 'temp.uue'
        temp_dir_uue = temp_dir + temp_uue
        temp_png = 'temp.png'
        temp_dir_png = temp_dir + temp_png
        # print tbarcode_option

        # debug code to write file name

        cmd_tbarcode = '''/usr/local/share/tbarcode11/tbarcode -fIMAGE -iPNG -o"{0}" -b{1} -d"{2}" --colormode=GRAY -O --decoder=hardware --dpi=300 -r270'''.format(temp_dir_png, symbology_num, data)
        cmd_convert = """convert '{0}' -define png:color-type=0 -gravity center -background white -extent 1072x1448 '{0}'""".format(temp_dir_png)
        cmd_encode = """uuencode '{0}' '{1}' > '{2}'""".format(temp_dir_png, temp_png, temp_dir_uue)
        cmd_kindle_start = """cat > '{}' \r""".format(temp_uue)  # this is how we paste the image
        cmd_kindle_close = '\x03'  # same as CTRL + C
        cmd_kindle_decode = '/mnt/us/busybox uudecode {} \r'.format(temp_uue)
        cmd_kindle_display = 'eips -g {} -f \r'.format(temp_png)

        cmd_tbarcode += ' --sizemode=module -m{}'.format(mil)

        if(tbarcode_option):
            print "tbarcode_option is: {0}".format(tbarcode_option)
            cmd_tbarcode += ' ' + tbarcode_option

        if translation:
            cmd_tbarcode += ' --translation=on'

        # --formatstring="B##C####B&"
        # tbarcode - generate barcode using input
        # An example using shorthand vs verbose tbarcode command
        # /usr/local/share/tbarcode11/tbarcode -fIMAGE -iPNG -ofile.png -b20 -O -d"somedata" --dpi300 -w300 -h150
        # /usr/local/share/tbarcode11/tbarcode --format=IMAGE -imageformat=PNG --output=file.png --barcode=20 -O --data="somedata" --dpi=300 --width=300 --height=150

        # Sizemode considerations
        # Use --sizemode=minimal as default for best fit
        # Use --sizemode=module for user defined mil sizes

        logged_symbol = symbology_num
        if len(Kindle.symbologies) > symbology_num:  # check if we have a name for the symbology
            logged_symbol = Kindle.symbologies[symbology_num]

        with_translation = ""
        if translation:
            with_translation = " with translation"

        self.log.info("Displaying {}, {}mil, {}{}".format(logged_symbol, mil, data, with_translation))

        self.log.debug(cmd_tbarcode)
        print cmd_tbarcode
        os.system(cmd_tbarcode)

        # imagemagick - resize and make correct png format
        self.log.debug(cmd_convert)
        os.system(cmd_convert)

        # uuencode - encode as temp ascii file
        self.log.debug(cmd_encode)
        os.system(cmd_encode)

        # open new file on kindle and write temp ascii file
        file_to_send = open(temp_dir_uue, 'rb').read() + '\r'
        self.log.debug('file opened')

        self.kindle_send(cmd_kindle_start, False)
        # self.log.debug(cmd_kindle_start)
        # self.log.debug('prepared to send')

        self.kindle_send(file_to_send, False)
        self.log.debug('file sent')

        self.kindle_send(cmd_kindle_close, True)
        # self.log.debug('file closed')

        # uudecode - decode ascii data to temp image
        self.kindle_send(cmd_kindle_decode, True)
        # self.log.debug(cmd_kindle_decode)
        # self.log.debug('decoded')

        # eips - display image and return status
        self.kindle_send(cmd_kindle_display, True)
        # self.log.debug(cmd_kindle_display)
        self.log.debug('displayed')

        time.sleep(DISPLAY_SLEEP)

    def brightness(self, level):
        if level is None or not isinstance(level, int) or not 0 <= level <= 25:
            self.log.error('Brightness level out of range 0-25, default to 0')
            level = 0

        self.log.info("Setting kindle brightness: {}".format(level))

        msg = 'lipc-set-prop com.lab126.powerd flIntensity {}\r'.format(level)
        self.kindle_send(msg.encode(), True)
        time.sleep(DISPLAY_SLEEP)


if __name__ == "__main__":
    # noinspection PyClassHasNoInit
    class Tests:
        k = Kindle()

        def test_image_time(self):
            from datetime import datetime
            runs = 5

            deltas = []
            for x in xrange(runs):
                start = datetime.now()
                if x % 2 == 0:
                    self.k.image('rss_115')
                else:
                    self.k.image('rss_114')
                end = datetime.now()
                delta = end - start
                deltas.append(delta.total_seconds())
                print delta.total_seconds()
            avg = float(sum(deltas) / len(deltas))
            print("AVG: {}".format(avg))
            assert avg < 2.1
