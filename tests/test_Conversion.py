import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("UPC-E", Symbology.UPCE.value, 30, ["01236432"], ["012300000642"], Size.UPCE.value, 0),
    ("UPC-E1", Symbology.UPCE1.value, 31, ['12345670'], ['123456000070'], Size.UPCE1.value, 1),
    ("EAN-8", Symbology.EAN8.value, 32, ['40153476'], ['0000040153476'], Size.EAN8.value, 0),
    ("Interleaved 2 of 5", Symbology.Interleaved2of5.value, 33, ['09780890815687'], ['A9780890815687'], Size.Interleaved2of5.value, 0),
    ("Codabar", Symbology.Codabar.value, 34, ['A01234567890123B'], ['0 1234 56789 0123'], Size.Codabar.value, 0),
    ("GS1 Databar 14", Symbology.GS1Databar14.value, 35, ['09498765301530'], ['A9498765301530'],
                 Size.GS1Databar14.value, 0)
]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_Conversion(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for Conversion Testing"""
    print barcode, output
    assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
