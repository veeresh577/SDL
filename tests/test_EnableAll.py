import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("UPC-A", Symbology.UPCA.value, 117, ["012345678905"], ["012345678905"], Size.UPCA.value, 0),
    ("UPC-E", Symbology.UPCE.value, 118, ["01236432"], ["01236432"], Size.UPCE.value, 0),
    ("UPC-E1", Symbology.UPCE1.value, 119, ['12345670'], ['12345670'], Size.UPCE1.value, 1),
    ("EAN-8", Symbology.EAN8.value, 120, ['40153476'], ['40153476'], Size.EAN8.value, 0),
    ("EAN-13", Symbology.EAN13.value, 121, ['4498765301535'], ['4498765301535'], Size.EAN13.value, 0),
    ("ISSN", Symbology.ISSN.value, 122, ['9778765301533'], ['83013016'], Size.ISSN.value, 0),
    ("Code 128b", Symbology.Code128b.value, 123, ['abcdefghijklm'], ['abcdefghijklm'], Size.Code128b.value, 0),
    ("EAN-128", Symbology.EAN128.value, 124, ['EAN128'], ['EAN128'], Size.EAN128.value, 0),
    ("ISBT-128", Symbology.ISBT128.value, 125, ['=%BLDGP'], ['=%BLDGP'], Size.ISBT128.value, 1),
    ("Code 39", Symbology.Code39.value, 126, ['CODE'], ['CODE'], Size.Code39.value, 0),
    ("Code 39 Sample with Full ASCII and the symbology enabled", Symbology.Code39ASCII.value, 127, ['CODE@7'],
     ['CODE@7'], Size.Code39ASCII.value, 0),
    ("Code 93", Symbology.Code93.value, 128, ['CODE 93'], ['CODE 93'], Size.Code93.value, 0),
    ("Interleaved 2 of 5", Symbology.Interleaved2of5.value, 129, ['01234567890128'], ['01234567890128'],
     Size.Interleaved2of5.value, 0),
    ("Discrete 2 of 5", Symbology.Discrete2of5.value, 130, ['14567'], ['14567'], Size.Discrete2of5.value, 1),
    (
        "Matrix 2 of 5", Symbology.Matrix2of5.value, 131, ['01234567890128'], ['01234567890128'], Size.Matrix2of5.value,
        0),
    ("Codabar", Symbology.Codabar.value, 132, ['A987654321098B'], ['A987654321098B'], Size.Codabar.value, 0),
    ("MSI", Symbology.MSI.value, 133, ['2020597'], ['2020597'], Size.MSI.value, 0),
    ("GS1 Databar 14", Symbology.GS1Databar14.value, 134, ['20012345678909'], ['0120012345678909'],
     Size.GS1Databar14.value, 0),
    ("GS1 Databar Limited", Symbology.GS1DatabarLimited.value, 135, ['15012345678907'], ['0115012345678907'],
     Size.GS1DatabarLimited.value, 0),
    ("PDF417", Symbology.PDF417.value, 136, ['ABCDEFGHIJK'], ['ABCDEFGHIJK'], Size.PDF417.value, 0),
    ("MicroPDF", Symbology.MicroPDF.value, 137, ['ABC123ABC'], ['ABC123ABC'], Size.MicroPDF.value, 0),
    ("Datamatrix", Symbology.Datamatrix.value, 138, ['01234567890512345'], ['01234567890512345'], Size.Datamatrix.value,
     0),
    ("Maxicode", Symbology.Maxicode.value, 139, ['01234567890512345'], ['01234567890512345'], Size.Maxicode.value, 1),
    ("QR Code", Symbology.QRCode.value, 140, ['01234567890512345'], ['01234567890512345'], Size.QRCode.value, 0),
    ("MicroQR", Symbology.MicroQR.value, 141, ['ABC123ABC'], ['ABC123ABC'], Size.MicroQR.value, 0),
    ("Aztec", Symbology.Aztec.value, 142, ['1234567890ABCabc'], ['1234567890ABCabc'], Size.Aztec.value, 0),
    ("Han-Xin", Symbology.HanXin.value, 143, ['Han-Xin'], ['Han-Xin'], Size.HanXin.value, 0)

]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_EnableAll(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for EnableAll Testing"""
    print barcode, output
    assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
