import pytest

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("OCR-AllChar", "sample6_1", 580, ['A3B2C1'], ['A3B2C1'], 0, 1),
    ("OCR-SomeChar", "sample6_2", 581, ['A2C1'], ['A2C1'], 0, 1),
    ("OCR-OutChar", "sample6_3", 582, ['A2XC1'], ['NR'], 0, 1),
    ("OCR-RARDOAnOAInvalid", "sample6_3", 583, ['A2XC1'], ['NR'], 0, 1),
    ("OCR-RARDOAnOAValid", "sample6_4", 584, ['AB98C2D'], ['AB98C2D'], 0, 1),
    ("OCR-AorDOSmallSpecInvalid", "sample6_4", 585, ['AB98C2D'], ['NR'], 0, 1),
    ("OCR-AorDOSmallSpecValid", "sample6_5", 586, ['TRY1B*-'], ['TRY1B*-'], 0, 1),
    ("OCR-DOrFAOrFODOSInvalid", "sample6_7", 587, ['AB+XY'], ['NR'], 0, 1),
    ("OCR-DOrFAOrFODOSValid", "sample6_6", 588, ['1> R<2'], ['1> R<2'], 0, 1),
    ("OCR-LStringInvalid", "sample6_6", 589, ['1> R<2'], ['NR'], 0, 1),
    ("OCR-LStringValid", "sample6_7", 590, ['AB+XY'], ['AB+XY'], 0, 1),
    ("OCR-Newline", "sample6_8", 591, ['31498', 'HMSTK'], ['31498', 'HMSTK'], 0, 1),
    ("OCR-StrExtract", "sample6_9", 592, ['DART>ABCDE>BOARD'], ['>ABCDE>'], 0, 1),
    ("OCR-IgnoretoEnd", "sample6_10", 593, ['315978645687'], ['31597'], 0, 1),
    ("OCR-SkipUntill", "sample6_11", 594, ['107DRMARIO'], ['DRMARIO'], 0, 1),
    ("OCR-SkipUntillNot", "sample6_12", 595, ['USSENTERPRISE'], ['ENTERPRISE'], 0, 1),
    ("OCR-RepeatInvalid", "sample6_11", 596, ['107DRMARIO'], ['NR'], 0, 1),
    ("OCR-RepeatValid", "sample6_13", 597, ['OF10821083'], ['OF10821083'], 0, 1),
    ("OCR-Scroll", "sample6_14", 598, ['87A12345'], ['12345'], 0, 1)

]  # yapf: disable


@pytest.mark.parametrize("desc,imageName,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_OCRTemplate(device, desc, imageName, subtest, barcode, output, size, use):
    """SDL validation for OCR Template Testing"""
    print barcode, output
    assert output == device.scanner.scan(imageName, subtest, barcode[0], size, use)
