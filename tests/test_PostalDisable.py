import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("Japanese Postal", Symbology.Japanese.value, 9, ['1234567'], ['NR'], Size.Japanese.value, 0),
    ("Korean Postal", Symbology.Korean.value, 10, ['123456'], ['NR'], Size.Korean.value, 1),
    ("US Planet ", Symbology.Planet.value, 11, ['123456789014'], ['NR'], Size.Planet.value, 0),
    ("US Postnet", Symbology.Postnet.value, 12, ['123455'], ['NR'], Size.Postnet.value, 0),
    ("Netherlands Postal", Symbology.Netherlands.value, 13, ['2500GG30250'], ['NR'], Size.Netherlands.value, 1),
    ("Australian Postal", Symbology.Australian.value, 14, ['12345678'], ['NR'], Size.Australian.value, 0),
    ("UK Royal Mail", Symbology.UKPostal.value, 15, ['12345678'], ['NR'], Size.UKPostal.value, 0),
    ("Intellimail", Symbology.Intellimail.value, 16, ['2054398789801234567811788'], ['NR'], Size.Intellimail.value, 0)
]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_PostalDisable(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for Postal Disable Testing"""
    print barcode, output
    assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
