import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("Japanese Postal", Symbology.Japanese.value, 1, ['1234567'], ['1234567'], Size.Japanese.value, 0),
    ("Korean Postal", Symbology.Korean.value, 2, ['123456'], ['123456'], Size.Korean.value, 1),
    ("US Planet ", Symbology.Planet.value, 3, ['123456789014'], ['123456789014'], Size.Planet.value, 0),
    ("US Postnet", Symbology.Postnet.value, 4, ['123455'], ['123455'], Size.Postnet.value, 0),
    ("Netherlands Postal", Symbology.Netherlands.value, 5, ['2500GG30250'], ['2500GG30250'], Size.Netherlands.value, 1),
    ("Australian Postal", Symbology.Australian.value, 6, ['12345678'], ['11,12345678,26 44 19 15'],
     Size.Australian.value, 0),
    ("UK Royal Mail", Symbology.UKPostal.value, 7, ['12345678'], ['12345678P'], Size.UKPostal.value, 0),
    ("Intellimail", Symbology.Intellimail.value, 8, ['2054398789801234567811788'], ['2054398789801234567811788'],
     Size.Intellimail.value, 0)
]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes ])
def test_PostalEnable(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for Postal Disable Testing"""
    print barcode, output
    assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
