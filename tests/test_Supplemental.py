import pytest
from sdlconstants import Symbology, Size

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("EAN-13 5-Supps", Symbology.EAN135.value, 52, ['449876530153512345'], ['4498765301535'], Size.EAN135.value, 0),
    (
        "EAN-13 5-Supps", Symbology.EAN135.value, 53, ['449876530153512345'], ['449876530153512345'], Size.EAN135.value,
        0),
    ("EAN-13 5-Supps", Symbology.EAN13.value, 54, ['4498765301535'], ['NR'], Size.EAN13.value, 0),
    (
        "EAN-13 5-Supps", Symbology.EAN135.value, 55, ['449876530153512345'], ['449876530153512345'], Size.EAN135.value,
        0),
    ("EAN-13 5-Supps", Symbology.EAN13.value, 56, ['4498765301535'], ['4498765301535'], Size.EAN13.value, 0),
    (
        "EAN-13 5-Supps", Symbology.EAN135.value, 57, ['414123456789412345'], ['414123456789412345'], Size.EAN135.value,
        0),
    ("Bookland", "test7_bookland", 58, ['97934535223280001'], ['97934535223280001'], Size.ISBN135.value, 1),
    (
        "EAN-13 5-Supps", Symbology.EAN135.value, 59, ['414123456789412345'], ['414123456789412345'], Size.EAN135.value,
        0),
    (
        "EAN-13 5-Supps", Symbology.EAN135.value, 60, ['977123456789812345'], ['977123456789812345'], Size.EAN135.value,
        0),
    (
        "EAN-13 5-Supps", Symbology.EAN135.value, 61, ['449876530153512345'], ['449876530153512345'], Size.EAN135.value,
        0),
    ("EAN-13 5-Supps", Symbology.EAN135.value, 62, ['414123456789412345'], ['414123456789412345'], Size.EAN135.value, 0)
]  # yapf: disable


@pytest.mark.parametrize("desc,imageName,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0].split('.')[0] for barcode in barcodes])
def test_Supplemental(device, desc, imageName, subtest, barcode, output, size, use):
    """SDL validation of Supplemental Testing"""
    print barcode, output
    assert output == device.scanner.scan(imageName, subtest, barcode[0], size, use)
