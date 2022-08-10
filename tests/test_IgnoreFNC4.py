# coding: utf-8 unicode
import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("Code 128", "c128_fnc4_ignore_001", 255, ['ABCabcz'], ['�BC�bc�'], Size.Code128.value, 1),
    ("Code 128", "c128_fnc4_ignore_002", 256, ['2900000100437'], ['290000010043�'], Size.Code128.value, 1),
    ("Code 128", "c128_fnc4_ignore_003", 257, ['2900000176350'], ['290000017635�'], Size.Code128.value, 1),
    ("Code 128", "c128_fnc4_ignore_004", 258, ['2900000901004'], ['290000090100�'], Size.Code128.value, 1),
    ("Code 128", "c128_fnc4_ignore_005", 259, ['2900000280025'], ['290000028002�'], Size.Code128.value, 1),
    ("Code 128", "c128_fnc4_ignore_001", 260, ['ABCabcz'], ['ABCabcz'], Size.Code128.value, 1),
    ("Code 128", "c128_fnc4_ignore_002", 261, ['2900000100437'], ['2900000100437'], Size.Code128.value, 1),
    ("Code 128", "c128_fnc4_ignore_003", 262, ['2900000176350'], ['2900000176350'], Size.Code128.value, 1),
    ("Code 128", "c128_fnc4_ignore_004", 263, ['2900000901004'], ['2900000901004'], Size.Code128.value, 1),
    ("Code 128", "c128_fnc4_ignore_005", 264, ['2900000280025'], ['2900000280025'], Size.Code128.value, 1)

]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_IgnoreFNC4(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for IgnoreFNC4 Testing"""
    print barcode, output
    assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
