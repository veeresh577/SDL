package sdl.android.testapp;

public class RQZHandler {

    static class RQZData {
        public boolean Enable = false;
        public int Level = 0;
        public int Type = 0;
        public boolean Decode = false; //true = Always Decode, false = Decode or NR

        public RQZData() {
            this.Enable = false;
            this.Level = 0;
            this.Type = 0;
            this.Decode = false;
        }

    }

    private static final String UPCEExpected = "09876547";
    private static final String Code39Expected = "12345";
    private static final String Code128Expected = "123abc";
    private static final String EAN8Expected = "09876545";
    private static final String EAN13Expected = "1234567890128";
    private static final String I25Expected = "123456789012";
    private static final String UPCESuppExpected = "0123456578";
    private static final String EAN8SuppExpected = "1234567089876";
    private static final String EAN13SuppExpected = "987654321123156";
    private static final String OldCouponExpected = "9912760275851810113490850160710";
    private static final String ISBT_ConcatExpected = "=W12340312345600=%55Y0";

    //private static RQZData[] TestData = new RQZData[33];
    private static RQZData[] TestData = new RQZData[41];

    public static void Initialize() {

        //for(int x = 0; x < 33; x++){
        for (int x = 0; x < 41; x++) {
            TestData[x] = new RQZData();
        }
        TestData[1].Enable = false;
        TestData[1].Level = 0;
        TestData[1].Type = 0;
        TestData[1].Decode = true;
        TestData[2].Enable = false;
        TestData[2].Level = 0;
        TestData[2].Type = 1;
        TestData[2].Decode = false;
        TestData[3].Enable = false;
        TestData[3].Level = 0;
        TestData[3].Type = 2;
        TestData[3].Decode = false;
        TestData[4].Enable = false;
        TestData[4].Level = 0;
        TestData[4].Type = 3;
        TestData[4].Decode = false;
        TestData[5].Enable = false;
        TestData[5].Level = 1;
        TestData[5].Type = 0;
        TestData[5].Decode = true;
        TestData[6].Enable = false;
        TestData[6].Level = 1;
        TestData[6].Type = 1;
        TestData[6].Decode = true;
        TestData[7].Enable = false;
        TestData[7].Level = 1;
        TestData[7].Type = 2;
        TestData[7].Decode = false;
        TestData[8].Enable = false;
        TestData[8].Level = 1;
        TestData[8].Type = 3;
        TestData[8].Decode = false;
        TestData[9].Enable = false;
        TestData[9].Level = 2;
        TestData[9].Type = 0;
        TestData[9].Decode = true;
        TestData[10].Enable = false;
        TestData[10].Level = 2;
        TestData[10].Type = 1;
        TestData[10].Decode = true;
        TestData[11].Enable = false;
        TestData[11].Level = 2;
        TestData[11].Type = 2;
        TestData[11].Decode = false;
        TestData[12].Enable = false;
        TestData[12].Level = 2;
        TestData[12].Type = 3;
        TestData[12].Decode = false;
        TestData[13].Enable = false;
        TestData[13].Level = 3;
        TestData[13].Type = 0;
        TestData[13].Decode = true;
        TestData[14].Enable = false;
        TestData[14].Level = 3;
        TestData[14].Type = 1;
        TestData[14].Decode = true;
        TestData[15].Enable = false;
        TestData[15].Level = 3;
        TestData[15].Type = 2;
        TestData[15].Decode = false;
        TestData[16].Enable = false;
        TestData[16].Level = 3;
        TestData[16].Type = 3;
        TestData[16].Decode = false;
        TestData[17].Enable = true;
        TestData[17].Level = 0;
        TestData[17].Type = 0;
        TestData[17].Decode = true;
        TestData[18].Enable = true;
        TestData[18].Level = 0;
        TestData[18].Type = 1;
        TestData[18].Decode = false;
        TestData[19].Enable = true;
        TestData[19].Level = 0;
        TestData[19].Type = 2;
        TestData[19].Decode = false;
        TestData[20].Enable = true;
        TestData[20].Level = 0;
        TestData[20].Type = 3;
        TestData[20].Decode = false;
        TestData[21].Enable = true;
        TestData[21].Level = 1;
        TestData[21].Type = 0;
        TestData[21].Decode = true;
        TestData[22].Enable = true;
        TestData[22].Level = 1;
        TestData[22].Type = 1;
        TestData[22].Decode = true;
        TestData[23].Enable = true;
        TestData[23].Level = 1;
        TestData[23].Type = 2;
        TestData[23].Decode = false;
        TestData[24].Enable = true;
        TestData[24].Level = 1;
        TestData[24].Type = 3;
        TestData[24].Decode = false;
        TestData[25].Enable = true;
        TestData[25].Level = 2;
        TestData[25].Type = 0;
        TestData[25].Decode = true;
        TestData[26].Enable = true;
        TestData[26].Level = 2;
        TestData[26].Type = 1;
        TestData[26].Decode = true;
        TestData[27].Enable = true;
        TestData[27].Level = 2;
        TestData[27].Type = 2;
        TestData[27].Decode = true;
        TestData[28].Enable = true;
        TestData[28].Level = 2;
        TestData[28].Type = 3;
        TestData[28].Decode = false;
        TestData[29].Enable = true;
        TestData[29].Level = 3;
        TestData[29].Type = 0;
        TestData[29].Decode = true;
        TestData[30].Enable = true;
        TestData[30].Level = 3;
        TestData[30].Type = 1;
        TestData[30].Decode = true;
        TestData[31].Enable = true;
        TestData[31].Level = 3;
        TestData[31].Type = 2;
        TestData[31].Decode = true;
        TestData[32].Enable = true;
        TestData[32].Level = 3;
        TestData[32].Type = 3;
        TestData[32].Decode = true;
    }

    public static boolean TestForMatch(String Symbology, int TestNum, String data) {
        String expectedData;

        if (Symbology.equals("UPCE")) {
            expectedData = UPCEExpected;
        } else if (Symbology.equals("Code39")) {
            expectedData = Code39Expected;
        } else if (Symbology.equals("Code128")) {
            expectedData = Code128Expected;
        } else if (Symbology.equals("EAN8")) {
            expectedData = EAN8Expected;
        } else if (Symbology.equals("EAN13")) {
            expectedData = EAN13Expected;
        } else if (Symbology.equals("I25")) {
            expectedData = I25Expected;
        } else if (Symbology.equals("UPCESupp")) {
            expectedData = UPCESuppExpected;
        } else if (Symbology.equals("EAN8Supp")) {
            expectedData = EAN8SuppExpected;
        } else if (Symbology.equals("EAN13Supp")) {
            expectedData = EAN13SuppExpected;
        } else if (Symbology.equals("OldCoupon")) {
            expectedData = OldCouponExpected;
        } else if (Symbology.equals("ISBT_Concat")) {
            expectedData = ISBT_ConcatExpected;
        } else {
            expectedData = "Invalid Symbology";
        }

        //A correct decode is always PASS
        if (expectedData.equals(data)) {
            return true;
        }

        //Sometimes NR is also acceptable
        //if (TestNum < 33){
        if (!TestData[TestNum].Decode) {
            expectedData.equals("NR");
            return true;
        }//}
        /*else {
			expectedData.equals("NR");
			return true;
		}*/

        return false;
    }

    public static int get_Barcode(String Symbology, int Type) {

        int barcode = 0;

        if (Symbology.equals("UPCE")) {
            switch (Type) {
                case 0:
                    barcode = 27;
                    break;
                case 1:
                    barcode = 28;
                    break;
                case 2:
                    barcode = 34;
                    break;
                case 3:
                    barcode = 36;
                    break;
            }
        } else if (Symbology.equals("Code39")) {
            switch (Type) {
                case 0:
                    barcode = 1;
                    break;
                case 1:
                    barcode = 2;
                    break;
                case 2:
                    barcode = 8;
                    break;
                case 3:
                    barcode = 13;
                    break;
            }
        } else if (Symbology.equals("Code128")) {
            switch (Type) {
                case 0:
                    barcode = 14;
                    break;
                case 1:
                    barcode = 15;
                    break;
                case 2:
                    barcode = 21;
                    break;
                case 3:
                    barcode = 23;
                    break;
            }
        } else if (Symbology.equals("EAN8")) {
            switch (Type) {
                case 0:
                    barcode = 40;
                    break;
                case 1:
                    barcode = 41;
                    break;
                case 2:
                    barcode = 47;
                    break;
                case 3:
                    barcode = 49;
                    break;
            }
        } else if (Symbology.equals("EAN13")) {
            switch (Type) {
                case 0:
                    barcode = 53;
                    break;
                case 1:
                    barcode = 56;
                    break;
                case 2:
                    barcode = 0; // Not applicable
                    break;
                case 3:
                    barcode = 65;
                    break;
            }
        } else if (Symbology.equals("I25")) {
            switch (Type) {
                case 0:
                    barcode = 66;
                    break;
                case 1:
                    barcode = 67;
                    break;
                case 2:
                    barcode = 73;
                    break;
                case 3:
                    barcode = 75;
                    break;
            }
        } else if (Symbology.equals("UPCESupp")) {
            switch (Type) {
                case 0:
                    barcode = 79;
                    break;
                case 1:
                    barcode = 80;
                    break;
                case 2:
                    barcode = 86;
                    break;
                case 3:
                    barcode = 88;
                    break;
            }
        } else if (Symbology.equals("EAN8Supp")) {
            switch (Type) {
                case 0:
                    barcode = 92;
                    break;
                case 1:
                    barcode = 93;
                    break;
                case 2:
                    barcode = 99;
                    break;
                case 3:
                    barcode = 101;
                    break;
            }
        } else if (Symbology.equals("EAN13Supp")) {
            switch (Type) {
                case 0:
                    barcode = 105;
                    break;
                case 1:
                    barcode = 107;
                    break;
                case 2:
                    barcode = 113;
                    break;
                case 3:
                    barcode = 117;
                    break;
            }
        } else if (Symbology.equals("OldCoupon")) {
            switch (Type) {
                case 0:
                    barcode = 118;
                    break;
                case 1:
                    barcode = 120;
                    break;
                case 2:
                    barcode = 130;
                    break;
                case 3:
                    barcode = 0; // Not Applicable
                    break;
            }
        } else if (Symbology.equals("ISBT_Concat")) {
            switch (Type) {
                case 0:
                    barcode = 131;
                    break;
                case 1:
                    barcode = 132;
                    break;
                case 2:
                    barcode = 138;
                    break;
                case 3:
                    barcode = 143;
                    break;
            }
        }

        return barcode;
    }

    public static String next_Symbology(String Symbology) {
        //UPC-E + Supplementals and EAN-13 + Supplementals are not currently supported
        String next;

        if (Symbology.equals("UPCE")) {
            next = "Code39";
        } else if (Symbology.equals("Code39")) {
            next = "Code128";
        } else if (Symbology.equals("Code128")) {
            next = "EAN8";
        } else if (Symbology.equals("EAN8")) {
            next = "EAN13";
        } else if (Symbology.equals("EAN13")) {
            next = "I25";
        } else if (Symbology.equals("I25")) {
            next = "EAN8Supp";
        } else if (Symbology.equals("EAN8Supp")) {
            next = "OldCoupon";
        } else if (Symbology.equals("OldCoupon")) {
            next = "ISBT_Concat";
        } else if (Symbology.equals("ISBT_Concat")) {
            next = "END";
        } else {
            next = "Invalid Symbology";
        }

        return next;
    }

    public static boolean get_Enable(int nextTest) {
        return TestData[nextTest].Enable;
    }

    public static int get_Level(int nextTest) {
        return TestData[nextTest].Level;
    }

    public static int get_Type(int nextTest) {
        return TestData[nextTest].Type;
    }

    public static boolean get_Decode(int nextTest) {
        return TestData[nextTest].Decode;
    }
}


