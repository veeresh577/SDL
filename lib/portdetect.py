#!/usr/bin/env python
"""
portdetect.py

Written by Justin Babcock 11/15/2016

Automatic port detection for various devices.
"""

import serial
import serial.tools.list_ports_linux
import logging
import time


def rs232():
    """
	Returns the default RS232 serial port.

	:return: '/dev/ttyAMA0'
	"""
    return '/dev/ttyAMA0'


def ssi():
    """
	Returns the default RS232 serial port.

	:return: '/dev/ttyAMA0'
	"""
    return '/dev/ttyAMA0'


def decode_timer():
    """
	Returns the first DecodeTimer port found using the following information

	Device: /dev/ttyACM#
	Name: ttyACM#
	Description: USB Serial
	HWID: USB VID:PID=16C0:0483
	VID: 5824
	PID: 1155
	Product: USB Serial
	Interface: None

	:return: decode timer port in string format '/dev/ttyACM#'
	"""

    return _find_port(device='Decode Timer', expected='Decode Timer', vid=5824, pid=1155, prefix='ACM')


def kindle():
    """
	Returns the first Kindle port found using the following information

	Device: /dev/ttyUSB#
	Name: ttyUSB#
	Description: FT230X Basic UART
	HWID: USB VID:PID=0403:6015
	VID: 1027
	PID: 24597
	Product: FT230X Basic UART
	Interface: None



	:return: kindle port in string format '/dev/ttyUSB#'
	"""
    device = 'Kindle'
    ports = _find_ports(device=device, expected=None, vid=1027, pid=24597, prefix='USB')

    possible_kindles = []
    for port in ports:
        if _is_kindle(port):
            possible_kindles.append(port)

    if len(possible_kindles) > 1:
        _raise_multiple(device, possible_kindles)
    if len(possible_kindles) < 1:
        _raise_not_detected(device)

    return possible_kindles[0]


def gunsling():
    """
	Device: /dev/ttyUSB#
	Name: ttyUSB#
	Description: FT232R USB UART
	HWID: USB VID:PID=0403:6001 SER=A800crOl LOCATION=1-1.5.7.4
	VID: 1027
	PID: 24577
	Serial: A800crOl
	Location: 1-1.5.7.4
	Product: FT232R USB UART
	Interface: None

	:return:
	"""
    return _find_port(device='Gunsling', expected=None, vid=1027, pid=24577, prefix='USB')


def flappy_arm():
    """
	Device: /dev/ttyACM#
	Name: ttyACM#
	Description: Arduino Micro
	HWID: USB VID:PID=2341:8037 LOCATION=1-1.5.3
	VID: 9025
	PID: 32823
	Serial: None
	Location: 1-1.5.3
	Product: Arduino Micro
	Interface: None

	:return:
	"""
    return _find_port(device='Flappy Arm', expected='Flappy Arm', vid=9025, pid=32823, prefix='ACM')


def atlas():
    """
	Device: /dev/ttyACM#
	Name: ttyACM#
	Description: USB Serial
	HWID: USB VID:PID=16C0:0483 SER=1865570 LOCATION=1-1.4.7.1
	VID: 5824
	PID: 1155
	Serial: 1865570
	Location: 1-1.4.7.1
	Product: USB Serial
	Interface: None

	:return:
	"""
    return _find_port(device='Atlas', expected='Atlas', vid=5824, pid=1155, prefix='ACM')


def motorized_carriage():
    """
	Device: /dev/ttyACM#
	Name: ttyACM#
	Description: USB Serial
	HWID: USB VID:PID=16C0:0483 SER=1092670 LOCATION=1-1.4.7.1
	VID: 5824
	PID: 1155
	Serial: 1092670
	Location: 1-1.4.7.1
	Product: USB Serial
	Interface: None

	:return:
	"""
    return _find_port(device='Motorized Carriage', expected='Motorized Carriage', vid=5824, pid=1155, prefix='ACM')


def _is_kindle(port):
    success = False
    try:
        ser = serial.Serial(port, baudrate=115200)
        ser.write('\r')
        timeout = 1
        resp = None
        timeout_time = time.time() + timeout
        while True:
            if ser.inWaiting() > 0:
                time.sleep(.05)  # wait 50 ms for all data
                resp = ser.read_all()
                break
            if time.time() > timeout_time:
                break
        if 'kindle' in resp and 'root' in resp:
            success = True
    except:
        pass
    return success


def _check_identification(port, expected):
    cmd = 'E;'
    timeout = 3
    success = False
    resp = None
    try:
        ser = serial.Serial(port)
        ser.write(cmd)
        ser.flush()
        log.debug("{} SEND: {}".format(port, cmd))

        timeout_time = time.time() + timeout
        while True:
            if ser.inWaiting() > 0:
                time.sleep(.05)  # wait 50 ms for all data
                resp = ser.read_all()
                break
            if time.time() > timeout_time:
                break

        if resp:
            log.debug("{} RECV: {}".format(port, resp))
            if expected in resp:
                success = True

    except:
        pass

    return success, resp


def _find_ports(device, expected, vid, pid, prefix=None):
    """
		Find first serial port based on vid, pid and a prefix

		:param device: Name of the device for logging
		:param expected: Expected return from 'E' command, 'None' will skip
		:param vid: Decimal VID
		:param pid: Decimal PID
		:param prefix: Such as 'ACM' or 'USB'
		:return: return first port in string format '/dev/ttyprefix#', 'None' will skip
		"""

    ports = _get_ports()
    possible_ports = []
    for port in ports:
        if port.vid == vid and port.pid == pid:
            if prefix is None or prefix in port.device:
                if expected is not None:
                    success, response = _check_identification(port.device, expected)
                    if not success:
                        if response:
                            log.error("Incorrect response to 'E': {}: expected: {} RESP:{}".format(port.device, expected, response))
                            continue
                        else:
                            log.error("No response to 'E': {}".format(port.device))
                            continue

                possible_ports.append(port.device)

    if len(possible_ports) < 1:
        _raise_not_detected(device)

    return possible_ports


def _find_port(device, expected, vid, pid, prefix=None):
    possible_ports = _find_ports(device, expected, vid, pid, prefix)
    if len(possible_ports) > 1:
        _raise_multiple(device, possible_ports)
    else:
        return possible_ports[0]


def _get_ports():
    return serial.tools.list_ports_linux.comports()


def _raise_multiple(device, ports):
    raise serial.SerialException("Multiple ports detected for device: {}: {}".format(device, ''.join(ports)))


def _raise_not_detected(device):
    raise serial.SerialException("No ports detected for device: {}".format(device))

# logging.basicConfig(level=logging.DEBUG)
log = logging.getLogger(__name__)

# print "dt: " + decode_timer()
# print "kindle: " + kindle()
