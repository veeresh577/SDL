# coding=utf-8
import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]


# barcodes = [ #     ("EAN 13 With CC-A ",Symbology.EAN13.value, 17, ['449876530153511111111111111111111'],
# ['449876530153511111111111111111111'],Size.EAN13.value,0, "--CCtype=A"), #     ("EAN 8 With CC-A",
# Symbology.EAN8.value, 18, ['4015347611111111111111111111'], ['4015347611111111111111111111'],Size.EAN8.value,0,
# "--CCtype=A"), #     ("UPC-A With CC-A",Symbology.UPCA.value, 19, ['01234567890511111111111111111111'],
# ['01234567890511111111111111111111'],Size.UPCA.value,0, "--CCtype=A"), #     ("UPC-E With CC-A",
# Symbology.UPCE.value, 20, ['0123643211111111111111111111'], ['0123643211111111111111111111'],Size.UPCE.value,0,
# "--CCtype=A"), #     ("EAN 13 With CC-B",Symbology.EAN13.value, 21, ['449876530153511111111111111111111'],
# ['449876530153511111111111111111111'],Size.EAN13.value,0, "--CCtype=B"), #     ("EAN 8 With CC-B",
# Symbology.EAN8.value, 22, ['4015347611111111111111111111'], ['4015347611111111111111111111'],Size.EAN8.value,0,
# "--CCtype=B"), #     ("UPC-A With CC-B",Symbology.UPCA.value, 23, ['01234567890511111111111111111111'],
# ['01234567890511111111111111111111'],Size.UPCA.value,0, "--CCtype=B"), #     ("UPC-E With CC-B",
# Symbology.UPCE.value, 24, ['0123643211111111111111111111'], ['0123643211111111111111111111'],Size.UPCE.value,0,
# "--CCtype=B") # ]  # yapf: disable # # # @pytest.mark.parametrize("desc,imageName,subtest,barcode,output,size,use,
# option", barcodes, ids=[barcode[0].split('.')[0] for barcode in barcodes]) # def test_Composite(device,desc,
# imageName, subtest, barcode,output,size,use,option): #     """3748|(Standard Mode) SDL validation of Inverse
# Barcodes""" # #     assert output == device.scanner.scan(imageName, subtest,barcode[0],size,use,option)


barcodes = [
    ("EAN 13 With CC-A ", "Sdl_Comp_BC1", 17, ['449876530153511111111111111111111'],
     ['449876530153511111111111111111111'], Size.EAN13.value, 1),
    ("EAN 8 With CC-A", "Sdl_Comp_BC2", 18, ['4015347611111111111111111111'], ['4015347611111111111111111111'],
     Size.EAN8.value, 1),
    ("UPC-A With CC-A", "Sdl_Comp_BC3", 19, ['01234567890511111111111111111111'], ['01234567890511111111111111111111'],
     Size.UPCA.value, 1),
    ("UPC-E With CC-A", "Sdl_Comp_BC4", 20, ['0123643211111111111111111111'], ['0123643211111111111111111111'],
     Size.UPCE.value, 1),
    ("EAN 13 With CC-B", "Sdl_Comp_BC5", 21, ['449876530153511111111111111111111'],
     ['449876530153511111111111111111111'], Size.EAN13.value, 1),
    ("EAN 8 With CC-B", "Sdl_Comp_BC6", 22, ['4015347611111111111111111111'], ['4015347611111111111111111111'],
     Size.EAN8.value, 1),
    ("UPC-A With CC-B", "Sdl_Comp_BC7", 23, ['01234567890511111111111111111111'], ['01234567890511111111111111111111'],
     Size.UPCA.value, 1),
    ("UPC-E With CC-B", "Sdl_Comp_BC8", 24, ['0123643211111111111111111111'], ['0123643211111111111111111111'],
     Size.UPCE.value, 1)
]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_Composite(device, desc, symbology_num, subtest, barcode, output, size, use):
    """SDL validation for Composite Testing"""
    print barcode, output
    assert output == device.scanner.scan(symbology_num, subtest, barcode[0], size, use)
