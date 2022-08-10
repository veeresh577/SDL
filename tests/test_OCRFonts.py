import pytest

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("OCRA-OCRDisable", "sample3_1", 548, ['UNLIMITED POWER'], ['NR'], 0, 1),
    ("OCRA-Disable", "sample3_1", 549, ['UNLIMITED POWER'], ['NR'], 0, 1),
    ("OCRA-Enable", "sample3_1", 550, ['UNLIMITED POWER'], ['UNLIMITED POWER'], 0, 1),
    ("OCRA-EnableRes1", "sample3_2", 551, ['FAST CA$H + 300.1 IS **FUN**'], ['FAST CA$H + 300.1 IS **FUN**'], 0, 1),
    ("OCRA-EnableRes2", "sample3_3", 552, ['<SQUID PARTY>'], ['<SQUID PARTY>'], 0, 1),
    ("OCRA-EnableBank", "sample3_4", 553, ['><1625f68h25c'], ['><1625f68h25c'], 0, 1),
    ("OCRA-Similar", "sample3_5", 554, ['1!LIO0EFPRBDS5$+-'], ['1!LIOOEFPRBDS5$+-'], 0, 1),
    ("OCRB-Disable", "sample3_6", 555, ['TESTING THE TEXT DECODE'], ['NR'], 0, 1),
    ("OCRB-Enable", "sample3_6", 556, ['TESTING THE TEXT DECODE'], ['TESTING THE TEXT DECODE'], 0, 1),
    ("OCRB-EnaBank", "sample3_7", 557, ['#198513218<JNP>|'], ['#198513218<JNP>|'], 0, 1),
    ("OCRB-EnaLimit", "sample3_8", 558, ['+468719/8ACVX'], ['+468719/8ACVX'], 0, 1),
    ("OCRB-EnaISBN10", "sample3_9", 559, ['157231446X'], ['157231446X'], 0, 1),
    ("OCRB-EnaISBN13", "sample3_10", 560, ['9781430216261'], ['9781430216261'], 0, 1),
    ("OCRB-EnaTravel1", "sample3_11", 561,
     ['CIUSAD231458907A123X5328434D23', '3407127M9507122USA<<<<<<<<<<<6', 'STEVENSON<<PETER<<<<<<<<<<<<<<'],
     ['CIUSAD231458907A123X5328434D23', '3407127M9507122USA<<<<<<<<<<<6', 'STEVENSON<<PETER<<<<<<<<<<<<<<'], 0, 1),
    (
        "OCRB-EnaTravel2", "sample3_12", 562,
        ['I<USASTEVENSON<<PETER<<<<<<<<<<<<<<<', 'D231458907USA3407127M9507122<<<<<<<2'],
        ['I<USASTEVENSON<<PETER<<<<<<<<<<<<<<<', 'D231458907USA3407127M9507122<<<<<<<2'], 0, 1),
    ("OCRB-EnaTraAuto", "sample3_11", 563,
     ['CIUSAD231458907A123X5328434D23', '3407127M9507122USA<<<<<<<<<<<6', 'STEVENSON<<PETER<<<<<<<<<<<<<<'],
     ['CIUSAD231458907A123X5328434D23', '3407127M9507122USA<<<<<<<<<<<6', 'STEVENSON<<PETER<<<<<<<<<<<<<<'], 0, 1),
    ("OCRB-EnaPP", "sample3_13", 564,
     ['P<USAERIKSSON<<ANNA<MARIA<<<<<<<<<<<<<<<<<<<', 'L898902C<3USA6908061F9406236ZE184226B<<<<<14'],
     ['P<USAERIKSSON<<ANNA<MARIA<<<<<<<<<<<<<<<<<<<', 'L898902C<3USA6908061F9406236ZE184226B<<<<<14'], 0, 1),
    ("OCRB-EnaVisaA", "sample3_14", 565,
     ['V<USAERIKSSON<<JOHN<ARTHUR<<<<<<<<<<<<<<<<<<', 'L898902C<3USA4009078M9612109ZE6184226B<<<<<<'],
     ['V<USAERIKSSON<<JOHN<ARTHUR<<<<<<<<<<<<<<<<<<', 'L898902C<3USA4009078M9612109ZE6184226B<<<<<<'], 0, 1),
    ("OCRB-EnaVisaB", "sample3_15", 566, ['V<USAERIKSSON<<JOHN<ARTHUR<<<<<<<<<<', 'L898902C<3USA4009078M9612109<<<<<<<<'],
     ['V<USAERIKSSON<<JOHN<ARTHUR<<<<<<<<<<', 'L898902C<3USA4009078M9612109<<<<<<<<'], 0, 1),
    ("OCRB-EnaICAO", "sample3_14", 567,
     ['V<USAERIKSSON<<JOHN<ARTHUR<<<<<<<<<<<<<<<<<<', 'L898902C<3USA4009078M9612109ZE6184226B<<<<<<'],
     ['V<USAERIKSSON<<JOHN<ARTHUR<<<<<<<<<<<<<<<<<<', 'L898902C<3USA4009078M9612109ZE6184226B<<<<<<'], 0, 1),
    ("OCRB-Similar", "sample3_17", 568, ['1!LIOOEFPRBDS5$+-C(<'], ['1!LIOOEFPRBDS5$+-C(<'], 0, 1),
    ("MICR-Disable", "sample3_18", 569, ['51t86o361a381d'], ['NR'], 0, 1),
    ("MICR-Enable", "sample3_18", 570, ['51t86o361a381d'], ['51t86o361a381d'], 0, 1),
    ("USCurrencyDis", "sample3_19", 571, ['EC22775765F'], ['NR'], 0, 1),
    ("USCurrencyEna", "sample3_19", 572, ['EC22775765F'], ['EC22775765F'], 0, 1)

]  # yapf: disable


@pytest.mark.parametrize("desc,imageName,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_OCRFonts(device, desc, imageName, subtest, barcode, output, size, use):
    """SDL validation for OCR Fonts Testing"""
    print barcode, output
    assert output == device.scanner.scan(imageName, subtest, barcode[0], size, use)
