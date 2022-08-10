import pytest

# barcodes=[(Symbology_Type, Image_Name/Symbology_Num, Subtest, ["Barcode_Data"],["OutPut_Data"], Size(for Tbarcode
# use sdlconstants else 0), Function_Use(tbarcode=0/Image=1)]

barcodes = [
    ("RQZ 1", "Sdl_Rqz_BC27", 215, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 2", "Sdl_Rqz_BC28", 216, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 3", "Sdl_Rqz_BC34", 217, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 4", "Sdl_Rqz_BC39", 218, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 5", "Sdl_Rqz_BC27", 219, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 6", "Sdl_Rqz_BC28", 220, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 7", "Sdl_Rqz_BC34", 221, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 8", "Sdl_Rqz_BC39", 222, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 9", "Sdl_Rqz_BC27", 223, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 10", "Sdl_Rqz_BC28", 224, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 11", "Sdl_Rqz_BC34", 225, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 12", "Sdl_Rqz_BC39", 226, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 13", "Sdl_Rqz_BC27", 227, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 14", "Sdl_Rqz_BC28", 228, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 15", "Sdl_Rqz_BC34", 229, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 16", "Sdl_Rqz_BC39", 230, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 17", "Sdl_Rqz_BC27", 231, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 18", "Sdl_Rqz_BC28", 232, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 19", "Sdl_Rqz_BC34", 233, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 20", "Sdl_Rqz_BC39", 234, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 21", "Sdl_Rqz_BC27", 235, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 22", "Sdl_Rqz_BC28", 236, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 23", "Sdl_Rqz_BC34", 237, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 24", "Sdl_Rqz_BC39", 238, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 25", "Sdl_Rqz_BC27", 239, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 26", "Sdl_Rqz_BC28", 240, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 27", "Sdl_Rqz_BC34", 241, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 28", "Sdl_Rqz_BC39", 242, ['09876547'], ['09876547', 'NR'], 0, 1),
    ("RQZ 29", "Sdl_Rqz_BC27", 243, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 30", "Sdl_Rqz_BC28", 244, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 31", "Sdl_Rqz_BC34", 245, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 32", "Sdl_Rqz_BC39", 246, ['09876547'], ['09876547'], 0, 1),
    ("RQZ 33", "Sdl_Rqz_BC13", 247, ['12345'], ['12345'], 0, 1),
    ("RQZ 34", "Sdl_Rqz_BC23", 248, ['123abc'], ['123abc'], 0, 1),
    ("RQZ 35", "Sdl_Rqz_BC49", 249, ['09876545'], ['09876545'], 0, 1),
    ("RQZ 36", "Sdl_Rqz_BC65", 250, ['1234567890128'], ['1234567890128'], 0, 1),
    ("RQZ 37", "Sdl_Rqz_BC75", 251, ['123456789012'], ['123456789012'], 0, 1),
    ("RQZ 38", "Sdl_Rqz_BC101", 252, ['1234567089876'], ['1234567089876'], 0, 1),
    ("RQZ 39", "Sdl_Rqz_BC130", 253, ['9912760275851810113490850160710'], ['9912760275851810113490850160710'], 0, 1),
    ("RQZ 40", "Sdl_Rqz_BC143", 254, ['=W12340312345600=%55Y0'], ['=W12340312345600=%55Y0'], 0, 1)
]  # yapf: disable


@pytest.mark.parametrize("desc,symbology_num,subtest,barcode,output,size,use", barcodes,
                         ids=[barcode[0] for barcode in barcodes])
def test_ReduceQuietZone(device, desc, symbology_num, subtest, barcode, output, size, use):
    """Verify SDL's behaviour while scanning different levels of Reduced Quiet Zone barcodes."""
    print barcode, output
    all(x in output for x in (device.scanner.scan(symbology_num, subtest, barcode[0], size, use)))
