import pytest

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("OCRA-ProAddLtoR", "sample2_1", 573, ['TERRIFYINGB'], ['TERRIFYINGB'], 0, 1),
    ("OCRA-ProAddRtoL", "sample2_2", 574, ['100BOXES1'], ['100BOXES1'], 0, 1),
    ("OCRA-DigiAddLtoR", "sample2_3", 575, ['*5673>*'], ['*5673>*'], 0, 1),
    ("OCRA-DigitAddRtoL", "sample2_4", 576, ['029364190262'], ['029364190262'], 0, 1),
    ("OCRA-ProAddRtoLRemind", "sample2_5", 577, ['11289'], ['11289'], 0, 1),
    ("OCRA-DigitAddLtoRRemind", "sample2_6", 578, ['HEROES9'], ['HEROES9'], 0, 1),
    ("OCRA-Health", "sample2_7", 579, ['+V3820LK392001C'], ['+V3820LK392001C'], 0, 1)
]  # yapf: disable


@pytest.mark.parametrize("desc,imageName,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_OCRCheckDigit(device, desc, imageName, subtest, barcode, output, size, use):
    """SDL validation for OCR Check Digit Testing"""
    print barcode, output
    assert output == device.scanner.scan(imageName, subtest, barcode[0], size, use)
