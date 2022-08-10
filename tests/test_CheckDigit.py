import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("UPC-A", Symbology.UPCA.value, 36, ["012345678905"], ["1234567890"], Size.UPCA.value, 0),
    ("UPC-E", Symbology.UPCE.value, 37, ['01236432'], ['123643'], Size.UPCE.value, 0),
    ("UPC-E1", Symbology.UPCE1.value, 38, ['12345670'], ['234567'], Size.UPCE1.value, 1),
    ("Code39", Symbology.Code39.value, 39, ['1234A'], ['1234A'], Size.Code39.value, 0),
    ("Interleaved 2 of 5", Symbology.Interleaved2of5.value, 40, ['01234566'], ['01234566'], Size.Interleaved2of5.value,
     0),
    ("MSI-CheckDigit1", Symbology.MSI_1.value, 41, ['20205977'], ['20205977'], Size.MSI.value, 1),
    ("MSI-CheckDigit2", Symbology.MSI_2.value, 42, ['202059770'], ['202059770'], Size.MSI.value, 1),
]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_CheckDigit(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for Check Digit Testing"""
    print barcode, output
    assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
