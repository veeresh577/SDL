import pytest

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("Code 39", "Sdl_Inv_BC1", 25, ['CODE'], ['CODE'], 0, 1),
    ("Inverse Datamatrix", "Sdl_Inv_BC2", 26, ['01234567890512345'], ['01234567890512345'], 0, 1),
    ("Inverse QR", "Sdl_Inv_BC3", 27, ['01234567890512345'], ['01234567890512345'], 0, 1),
    ("Inverse Aztec", "Sdl_Inv_BC4", 28, ['1234567890123'], ['1234567890123'], 0, 1),
    ("Inverse HanXin", "Sdl_Inv_BC5", 29, ['Han-Xin'], ['Han-Xin'], 0, 1)
]  # yapf: disable


@pytest.mark.parametrize("desc,imageName,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_Inverse(device, desc, imageName, subtest, barcode, output, size, use):
    """SDL validation for Inverse Testing"""
    print barcode, output
    assert output == device.scanner.scan(imageName, subtest, barcode[0], size, use)
