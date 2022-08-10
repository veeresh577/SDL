import pytest

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("OCRA-2C3C", "sample5_1", 500, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRA-6C3C", "sample5_2", 501, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRA-6C8C", "sample5_2", 502, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRA-8C8C", "sample5_3", 503, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRA-8C12C", "sample5_3", 504, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRA-12C12C", "sample5_4", 505, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRA-2C3C", "sample5_5", 506, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRA-6C3C", "sample5_6", 507, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRA-6C8C", "sample5_6", 508, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRA-8C8C", "sample5_7", 509, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRA-8C12C", "sample5_7", 510, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRA-12C12C", "sample5_8", 511, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRA-2C3C", "sample5_9", 512, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRA-6C3C", "sample5_10", 513, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRA-6C8C", "sample5_10", 514, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRA-8C8C", "sample5_11", 515, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRA-8C12C", "sample5_11", 516, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRA-12C12C", "sample5_12", 517, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRB-2C3C", "sample5_13", 518, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRB-6C3C", "sample5_14", 519, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRB-6C8C", "sample5_14", 520, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRB-8C8C", "sample5_15", 521, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRB-8C12C", "sample5_15", 522, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRB-12C12C", "sample5_16", 523, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRB-2C3C", "sample5_17", 524, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRB-6C3C", "sample5_18", 525, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRB-6C8C", "sample5_18", 526, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRB-8C8C", "sample5_19", 527, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRB-8C12C", "sample5_19", 528, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRB-12C12C", "sample5_20", 529, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRB-2C3C", "sample5_21", 530, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRB-6C3C", "sample5_22", 531, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRB-6C8C", "sample5_22", 532, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRB-8C8C", "sample5_23", 533, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("OCRB-8C12C", "sample5_23", 534, ['TESTSTRING'], ['NR'], 0, 1),
    ("OCRB-12C12C", "sample5_24", 535, ['TESTSTRING'], ['TESTSTRING'], 0, 1),
    ("MICR-2C3C", "sample5_25", 536, ['987656789'], ['NR'], 0, 1),
    ("MICR-6C3C", "sample5_26", 537, ['987656789'], ['987656789'], 0, 1),
    ("MICR-6C8C", "sample5_26", 538, ['987656789'], ['NR'], 0, 1),
    ("MICR-8C8C", "sample5_27", 539, ['987656789'], ['987656789'], 0, 1),
    ("MICR-8C12C", "sample5_27", 540, ['987656789'], ['NR'], 0, 1),
    ("MICR-12C12C", "sample5_28", 541, ['987656789'], ['987656789'], 0, 1)
]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_OCRQuietZone(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for OCR Quiet Zone Testing"""
    print barcode, output
    if output == barcode:
        assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
    else:
        assert barcode != device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
