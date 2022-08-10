import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("UPC-A", Symbology.UPCA.value, 144, ["012345678905"], ["NR"], Size.UPCA.value, 0),
    ("UPC-E", Symbology.UPCE.value, 145, ["01236432"], ["NR"], Size.UPCE.value, 0),
    ("UPC-E1", Symbology.UPCE1.value, 146, ['12345670'], ["NR"], Size.UPCE1.value, 1),
    ("EAN-8", Symbology.EAN8.value, 147, ['40153476'], ['NR'], Size.EAN8.value, 0),
    ("EAN-13", Symbology.EAN13.value, 148, ['4498765301535'], ["NR"], Size.EAN13.value, 0),
    ("ISSN", Symbology.ISSN.value, 149, ['9778765301533'], ["NR"], Size.ISSN.value, 0),
    ("Code 128b", Symbology.Code128b.value, 150, ['abcdefghijklm'], ["NR"], Size.Code128b.value, 0),
    ("EAN-128", Symbology.EAN128.value, 151, ['EAN128'], ["NR"], Size.EAN128.value, 0),
    ("ISBT-128", Symbology.ISBT128.value, 152, ['=%BLDGP'], ["NR"], Size.ISBT128.value, 1),
    ("Code 39", Symbology.Code39.value, 153, ['CODE'], ["NR"], Size.Code39.value, 0),
    ("Code 39 Sample with Full ASCII and the symbology enabled", Symbology.Code39ASCII.value, 154, ['CODE@7'], ['NR'],
     Size.Code39ASCII.value, 0),
    ("Code 93", Symbology.Code93.value, 155, ['CODE 93'], ["NR"], Size.Code93.value, 0),
    ("Interleaved 2 of 5", Symbology.Interleaved2of5.value, 156, ['01234567890128'], ["NR"], Size.Interleaved2of5.value,
     0),
    ("Discrete 2 of 5", Symbology.Discrete2of5.value, 157, ['14567'], ["NR"], Size.Discrete2of5.value, 1),
    ("Matrix 2 of 5", Symbology.Matrix2of5.value, 158, ['01234567890128'], ["NR"], Size.Matrix2of5.value, 0),
    ("Codabar", Symbology.Codabar.value, 159, ['A987654321098B'], ["NR"], Size.Codabar.value, 0),
    ("MSI", Symbology.MSI.value, 160, ['2020597'], ["NR"], Size.MSI.value, 0),
    ("GS1 Databar 14", Symbology.GS1Databar14.value, 161, ['20012345678909'], ["NR"], Size.GS1Databar14.value, 0),
    ("GS1 Databar Limited", Symbology.GS1DatabarLimited.value, 162, ['15012345678907'], ["NR"],
     Size.GS1DatabarLimited.value, 0),
    ("PDF417", Symbology.PDF417.value, 163, ['ABCDEFGHIJK'], ["NR"], Size.PDF417.value, 0),
    ("MicroPDF", Symbology.MicroPDF.value, 164, ['ABC123ABC'], ['NR'], Size.MicroPDF.value, 0),
    ("Datamatrix", Symbology.Datamatrix.value, 165, ['01234567890512345'], ["NR"], Size.Datamatrix.value, 0),
    ("Maxicode", Symbology.Maxicode.value, 166, ['01234567890512345'], ["NR"], Size.Maxicode.value, 1),
    ("QR Code", Symbology.QRCode.value, 167, ['01234567890512345'], ["NR"], Size.QRCode.value, 0),
    ("MicroQR", Symbology.MicroQR.value, 168, ['ABC123ABC'], ["NR"], Size.MicroQR.value, 0),
    ("Aztec", Symbology.Aztec.value, 169, ['1234567890ABCabc'], ["NR"], Size.Aztec.value, 0),
    ("Han-Xin", Symbology.HanXin.value, 170, ['Han-Xin'], ["NR"], Size.HanXin.value, 0)

]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_DisableAll(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for DisableAll Testing"""
    print barcode, output
    assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
