import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("UPC-A", Symbology.UPCA.value, 447, ["012345678905"], ["]E00012345678905"], Size.UPCA.value, 0),
    ("UPC-A", Symbology.UPCA.value, 448, ["012345678905"], ["A012345678905"], Size.UPCA.value, 0)
    ]

@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_CodeId(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for CodeID Testing"""
    print "actual:expected :", barcode ,  output
    assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
