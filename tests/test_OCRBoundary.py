import pytest

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("OCR-B I, One Line", "sample1_1", 599, ['THIS IS A TEST LINE'], ['THIS IS A TEST LINE'], 0, 1),
    ("OCR-B III, Two Lines", "sample1_2", 600, ['HERE IS ANOTHER PAIR', 'OF TEST LINES FOR YOU'],
     ['HERE IS ANOTHER PAIR', 'OF TEST LINES FOR YOU'], 0, 1),
    ("OCR-B IV, Three Lines", "sample1_3", 601, ['YOU MUST ANSWER ME', 'THESE QUESTIONS THREE', 'IF THE OTHER SIDE YOUD SEE'],
     ['YOU MUST ANSWER ME', 'THESE QUESTIONS THREE', 'IF THE OTHER SIDE YOUD SEE'], 0, 1),
    ("OCR-B I, Three Lines", "sample1_4", 602, ['THIS IS A TEST LINE', 'THIS IS A TEST LINE', 'THIS IS A TEST LINE'],
     ['THIS IS A TEST LINE', 'THIS IS A TEST LINE'], 0, 1),
    ("OCR-A I, 8 characters", "sample1_5", 603, ['CROSSBOW'], ['NR'], 0, 1),
    ("OCR-A I, 8 characters", "sample1_5", 604, ['CROSSBOW'], ['CROSSBOW'], 0, 1),
    ("OCR-A I, 11 characters", "sample1_6", 605, ['ZWEIHANDERS'], ['NR'], 0, 1),
    ("OCR-A I, 11 characters", "sample1_6", 606, ['ZWEIHANDERS'], ['ZWEIHANDERS'], 0, 1)
 ] # yapf: disable


@pytest.mark.parametrize("desc,imageName,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_OCRBoundary(device, desc, imageName, subtest, barcode, output, size, use):
    """SDL validation for OCR Boundary Testing"""
    print barcode, output
    assert output == device.scanner.scan(imageName, subtest, barcode[0], size, use)
