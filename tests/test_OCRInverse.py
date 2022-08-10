import pytest

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("OCRA-RegInvReg", "sample4_1", 542, ['29487901'], ['29487901'], 0, 1),
    ("OCRA-RegInvInv", "sample4_1", 543, ['29487901'], ['NR'], 0, 1),
    ("OCRA-RegInvAuto", "sample4_1", 544, ['29487901'], ['29487901'], 0, 1),
    ("OCRA-InvInvReg", "sample4_2", 545, ['29487901'], ['NR'], 0, 1),
    ("OCRA-InvInvInv", "sample4_2", 546, ['29487901'], ['29487901'], 0, 1),
    ("OCRA-InvInvAuto", "sample4_2", 547, ['29487901'], ['29487901'], 0, 1)
]  # yapf: disable


@pytest.mark.parametrize("desc,imageName,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_OCRInverse(device, desc, imageName, subtest, barcode, output, size, use):
    """SDL validation for OCR Inverse Testing"""
    print barcode, output
    assert output == device.scanner.scan(imageName, subtest, barcode[0], size, use)
