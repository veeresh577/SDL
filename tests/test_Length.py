import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("Code 128b", Symbology.Code128b.value, 43, ['abcdefghijklm'], ['abcdefghijklm'], Size.Code128b.value, 0),
    ("Code 128b", Symbology.Code128b.value, 44, ['abcdefghijklm'], ['abcdefghijklm'], Size.Code128b.value, 0),
    ("Code 39", Symbology.Code39.value, 45, ['CODE'], ['CODE'], Size.Code39.value, 0),
    ("Code 93", Symbology.Code93.value, 46, ['CODE 93'], ['CODE 93'], Size.Code93.value, 0),
    ("Interleaved 2 of 5", Symbology.Interleaved2of5.value, 47, ['01234567890128'], ['01234567890128'],
     Size.Interleaved2of5.value, 0),
    ("Discrete 2 of 5", Symbology.Discrete2of5.value, 48, ['14567'], ['14567'], Size.Discrete2of5.value, 1),
    ("Codabar", Symbology.Codabar.value, 49, ['A987654321098B'], ['A987654321098B'], Size.Codabar.value, 0),
    ("MSI", Symbology.MSI.value, 50, ['20205977'], ['NR'], Size.MSI.value, 0),
    ("Matrix 2 of 5", Symbology.Matrix2of5.value, 51, ['01234567890128'], ['NR'], Size.Matrix2of5.value, 0)
]  # yapf: disable


@pytest.mark.parametrize("desc,imageName,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_Length(device, desc, imageName, subtest, barcode, output, size, use):
    """SDL validation for Length Testing"""
    print barcode, output
    assert output == device.scanner.scan(imageName, subtest, barcode[0], size, use)
