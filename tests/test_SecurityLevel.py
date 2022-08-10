import pytest

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("Security Level 1", "Sdl_Sec_BC1_123456", [171, 182, 193, 204], ['123456'], ['123456'], 0, 1),
    ("Security Level 2", "Sdl_Sec_BC2_123456", [172, 183, 194, 205], ['123456'], ['123456'], 0, 1),
    ("Security Level 3", "Sdl_Sec_BC3_ABCDEFG", [173, 184, 195, 206], ['123456'], ['ABCDEFG'], 0, 1),
    ("Security Level 4", "Sdl_Sec_BC4_ABCDEFG", [174, 185, 196, 207], ['123456'], ['ABCDEFG'], 0, 1),
    ("Security Level 5", "Sdl_Sec_BC5_ABCDEFG", [175, 186, 197, 208], ['123456'], ['ABCDEFG'], 0, 1),
    ("Security Level 6", "Sdl_Sec_BC6", [176, 187, 198, 209], ['123456'], ['2932000038777'], 0, 1),
    ("Security Level 7", "Sdl_Sec_BC7", [177, 188, 199, 210], ['123456'],
     ['01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567'], 0, 1),
    ("Security Level 8", "Sdl_Sec_BC8", [178, 189, 200, 211], ['123456'],
     ['01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567'] * 2, 0, 1),
    ("Security Level 9", "Sdl_Sec_BC9", [179, 190, 201, 212], ['123456'],
     ['01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567'] * 4, 0, 1),
    ("Security Level 10", "Sdl_Sec_BC10", [180, 191, 202, 213], ['123456'],
     ['01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567'] * 6, 0, 1),
    ("Security Level 11", "Sdl_Sec_BC11", [181, 192, 203, 214], ['123456'],
     ['01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567'] * 8, 0, 1),
]  # yapf: disable

NO_OF_DECODES = 3


@pytest.mark.parametrize("desc,symbology_num,subtests,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_SecurityLevel(device, desc, symbology_num, subtests, barcode, output, size, use):
    """SDL validation of data from the BC based on the Security Level"""
    sec_level_decodes = []
    for subtest in subtests:
        count = 0
        for decode in range(NO_OF_DECODES):
            if barcode not in device.scanner.scan(symbology_num, subtest, barcode[0], size, use):
                count += 1
        sec_level_decodes.append(count)
    assert sec_level_decodes == sorted(sec_level_decodes)

