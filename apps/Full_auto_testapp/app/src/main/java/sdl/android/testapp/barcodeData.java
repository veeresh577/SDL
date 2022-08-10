package sdl.android.testapp;

/**
 * Created by Dgupta1 on 10/10/2019.
 */

public class barcodeData {

    static class barcodeDataTable {

        int paramNum;
        int paramVal;
        int paramNum2;
        int paramVal2;
        int paramNum3;
        int paramVal3;
        String type;
        String data;

        public barcodeDataTable(int newNum, int newVal, int newNum2, int newVal2, int newNum3, int newVal3, String newType, String newData) {

            this.paramNum = newNum;
            this.paramVal = newVal;
            this.paramNum2 = newNum2;
            this.paramVal2 = newVal2;
            this.paramNum3 = newNum3;
            this.paramVal3 = newVal3;
            this.type = newType;
            this.data = newData;

        }

    }

    static barcodeDataTable[] PostalData = new barcodeDataTable[8];
    static barcodeDataTable[] CompositeData = new barcodeDataTable[8];
    static barcodeDataTable[] InverseData = new barcodeDataTable[5];
    static barcodeDataTable[] ConversionData = new barcodeDataTable[6];
    static barcodeDataTable[] CheckDigitData = new barcodeDataTable[7];
    static barcodeDataTable[] LengthsData = new barcodeDataTable[9];
    static barcodeDataTable[] SupplementalData = new barcodeDataTable[11];
    static barcodeDataTable[] EnableDisableData = new barcodeDataTable[27];
    static barcodeDataTable[] CodeID = new barcodeDataTable[2];
    static barcodeDataTable[] RQZ = new barcodeDataTable[40];
    static barcodeDataTable[] IgnoreFNC4 = new barcodeDataTable[10];

    static barcodeDataTable[] OCRQuietZone = new barcodeDataTable[42];
    static barcodeDataTable[] OCRInverse = new barcodeDataTable[6];
    static barcodeDataTable[] OCRFonts = new barcodeDataTable[25];
    static barcodeDataTable[] OCRCheckDigit = new barcodeDataTable[7];
    static barcodeDataTable[] OCRTemplate = new barcodeDataTable[19];
    static barcodeDataTable[] OCRBoundary = new barcodeDataTable[8];

    static void Initialize() {

        PostalData[0] = new barcodeDataTable(290, 1, 0, 0, 0, 0, "Japanese Postal", "1234567");
        PostalData[1] = new barcodeDataTable(581, 1, 0, 0, 0, 0, "Korean Postal", "123456");
        PostalData[2] = new barcodeDataTable(90, 1, 0, 0, 0, 0, "US Planet", "123456789014");
        PostalData[3] = new barcodeDataTable(89, 1, 0, 0, 0, 0, "US Postnet", "123455");
        PostalData[4] = new barcodeDataTable(326, 1, 0, 0, 0, 0, "Netherlands KIX", "2500GG30250");
        PostalData[5] = new barcodeDataTable(291, 1, 0, 0, 0, 0, "Australian Postal", "11,12345678,26 44 19 15");
        PostalData[6] = new barcodeDataTable(91, 1, 0, 0, 0, 0, "UK Royal Mail", "12345678P");
        PostalData[7] = new barcodeDataTable(592, 1, 0, 0, 0, 0, "Intellimail", "2054398789801234567811788");

        CompositeData[0] = new barcodeDataTable(342, 1, 344, 1, 0, 0, "EAN13-CCA", "449876530153511111111111111111111");
        CompositeData[1] = new barcodeDataTable(342, 1, 344, 1, 0, 0, "EAN8-CCA", "4015347611111111111111111111");
        CompositeData[2] = new barcodeDataTable(342, 1, 344, 1, 0, 0, "UPCA-CCA", "01234567890511111111111111111111");
        CompositeData[3] = new barcodeDataTable(342, 1, 344, 1, 0, 0, "UPCE-CCA", "0123643211111111111111111111");
        CompositeData[4] = new barcodeDataTable(342, 1, 344, 1, 0, 0, "EAN13-CCB", "449876530153511111111111111111111");
        CompositeData[5] = new barcodeDataTable(342, 1, 344, 1, 0, 0, "EAN8-CCB", "4015347611111111111111111111");
        CompositeData[6] = new barcodeDataTable(342, 1, 344, 1, 0, 0, "UPCA-CCB", "01234567890511111111111111111111");
        CompositeData[7] = new barcodeDataTable(342, 1, 344, 1, 0, 0, "UPCE-CCB", "0123643211111111111111111111");

        InverseData[0] = new barcodeDataTable(0, 1, 586, 1, 0, 0, "Code 39", "CODE");
        InverseData[1] = new barcodeDataTable(292, 1, 588, 1, 0, 0, "Datamatrix", "01234567890512345");
        InverseData[2] = new barcodeDataTable(293, 1, 587, 1, 0, 0, "QR Code", "01234567890512345");
        InverseData[3] = new barcodeDataTable(574, 1, 589, 1, 0, 0, "Aztec", "1234567890ABCabc");
        InverseData[4] = new barcodeDataTable(1167, 1, 1168, 1, 0, 0, "Han-xin", "Han-Xin");

        ConversionData[0] = new barcodeDataTable(2, 1, 37, 1, 45, 0, "UPCE to UPCA", "012300000642");
        ConversionData[1] = new barcodeDataTable(12, 1, 38, 1, 45, 0, "UPCE1 to UPCA", "123456000070");
        ConversionData[2] = new barcodeDataTable(4, 1, 39, 1, 45, 0, "EAN8 zero extend", "0000040153476");
        ConversionData[3] = new barcodeDataTable(6, 1, 82, 1, 45, 2, "I25 to EAN13", "A9780890815687");
        ConversionData[4] = new barcodeDataTable(7, 1, 54, 1, 55, 1, "CLSI NOTIS", "0 1234 56789 0123");
        ConversionData[5] = new barcodeDataTable(338, 1, 397, 1, 45, 2, "GS1 Databar 14 to UPCA", "A9498765301530");

        CheckDigitData[0] = new barcodeDataTable(1, 1, 34, 0, 40, 0, "UPCA", "1234567890");
        CheckDigitData[1] = new barcodeDataTable(2, 1, 35, 0, 41, 0, "UPCE", "123643");
        CheckDigitData[2] = new barcodeDataTable(12, 1, 36, 0, 42, 0, "UPCE1", "234567");
        CheckDigitData[3] = new barcodeDataTable(0, 1, 48, 1, 43, 1, "Code 39", "1234A");
        CheckDigitData[4] = new barcodeDataTable(6, 1, 49, 2, 44, 1, "I25 OPCC", "01234566");
        CheckDigitData[5] = new barcodeDataTable(11, 1, 50, 0, 46, 1, "MSI 1CD", "20205977");
        CheckDigitData[6] = new barcodeDataTable(11, 1, 50, 1, 46, 1, "MSI 2CD", "202059770");

        LengthsData[0] = new barcodeDataTable(8, 1, 209, 13, 210, 0, "Code 128", "abcdefghijklm");
        LengthsData[1] = new barcodeDataTable(8, 1, 209, 2, 210, 15, "Code 128", "abcdefghijklm");
        LengthsData[2] = new barcodeDataTable(0, 1, 18, 4, 19, 0, "Code 39", "CODE");
        LengthsData[3] = new barcodeDataTable(9, 1, 26, 7, 27, 0, "Code 93", "CODE 93");
        LengthsData[4] = new barcodeDataTable(6, 1, 22, 11, 23, 14, "I25", "01234567890128");
        LengthsData[5] = new barcodeDataTable(5, 1, 20, 4, 21, 8, "D25", "14567");
        LengthsData[6] = new barcodeDataTable(7, 1, 24, 14, 25, 0, "Codabar", "A987654321098B");
        LengthsData[7] = new barcodeDataTable(11, 1, 30, 12, 31, 0, "MSI", "");
        LengthsData[8] = new barcodeDataTable(618, 1, 619, 54, 620, 55, "M25", "");

        SupplementalData[0] = new barcodeDataTable(16, 0, 0, 0, 0, 0, "1- EAN13 s5", "4498765301535");
        SupplementalData[1] = new barcodeDataTable(16, 1, 0, 0, 0, 0, "2- EAN13 s5", "449876530153512345");
        SupplementalData[2] = new barcodeDataTable(16, 1, 0, 0, 0, 0, "3- EAN13", "");
        SupplementalData[3] = new barcodeDataTable(16, 2, 0, 0, 0, 0, "4- EAN13 s5", "449876530153512345");
        SupplementalData[4] = new barcodeDataTable(16, 2, 0, 0, 0, 0, "5- EAN13", "4498765301535");
        SupplementalData[5] = new barcodeDataTable(16, 3, 0, 0, 0, 0, "6- EAN13 s5", "414123456789412345");
        SupplementalData[6] = new barcodeDataTable(16, 5, 83, 1, 576, 1, "7- Bookland s5", "97934535223280001");
        SupplementalData[7] = new barcodeDataTable(16, 6, 0, 0, 0, 0, "8- EAN13 s5", "414123456789412345");
        SupplementalData[8] = new barcodeDataTable(16, 7, 0, 0, 0, 0, "9- EAN13 s5", "977123456789812345");
        SupplementalData[9] = new barcodeDataTable(16, 9, 579, 449, 0, 0, "10- EAN13 s5", "449876530153512345");
        SupplementalData[10] = new barcodeDataTable(16, 10, 580, 414, 0, 0, "11- EAN13 s5", "414123456789412345");

        EnableDisableData[0] = new barcodeDataTable(1, 1, 0, 0, 0, 0, "UPCA", "012345678905");
        EnableDisableData[1] = new barcodeDataTable(2, 1, 0, 0, 0, 0, "UPCE", "01236432");
        EnableDisableData[2] = new barcodeDataTable(12, 1, 0, 0, 0, 0, "UPCE1", "12345670");
        EnableDisableData[3] = new barcodeDataTable(4, 1, 0, 0, 0, 0, "EAN8", "40153476");
        EnableDisableData[4] = new barcodeDataTable(3, 1, 0, 0, 0, 0, "EAN13", "4498765301535");
        EnableDisableData[5] = new barcodeDataTable(617, 1, 0, 0, 0, 0, "ISSN", "8765301X");
        EnableDisableData[6] = new barcodeDataTable(8, 1, 0, 0, 0, 0, "CODE 128", "abcdefghijklm");
        EnableDisableData[7] = new barcodeDataTable(14, 1, 0, 0, 0, 0, "EAN 128", "EAN128");
        EnableDisableData[8] = new barcodeDataTable(84, 1, 0, 0, 0, 0, "ISBT 128", "=%BLDGP");
        EnableDisableData[9] = new barcodeDataTable(0, 1, 0, 0, 0, 0, "CODE 39", "CODE");
        EnableDisableData[10] = new barcodeDataTable(17, 1, 0, 0, 0, 0, "CODE 39 ACII", "CODE@7");
        EnableDisableData[11] = new barcodeDataTable(9, 1, 0, 0, 0, 0, "CODE 93", "CODE 93");
        EnableDisableData[12] = new barcodeDataTable(6, 1, 0, 0, 0, 0, "I25", "01234567890128");
        EnableDisableData[13] = new barcodeDataTable(5, 1, 0, 0, 0, 0, "D25", "14567");
        EnableDisableData[14] = new barcodeDataTable(618, 1, 0, 0, 0, 0, "M25", "01234567890128");
        EnableDisableData[15] = new barcodeDataTable(7, 1, 0, 0, 0, 0, "CODABAR", "A987654321098B");
        EnableDisableData[16] = new barcodeDataTable(11, 1, 0, 0, 0, 0, "MSI", "2020597");
        EnableDisableData[17] = new barcodeDataTable(338, 1, 0, 0, 0, 0, "GS1 DATABAR 14", "0120012345678909");
        EnableDisableData[18] = new barcodeDataTable(339, 1, 0, 0, 0, 0, "GS1 DATABAR LTD", "0115012345678907");
        EnableDisableData[19] = new barcodeDataTable(15, 1, 0, 0, 0, 0, "PDF417", "ABCDEFGHIJK");
        EnableDisableData[20] = new barcodeDataTable(227, 1, 0, 0, 0, 0, "MICROPDF", "ABC123ABC");
        EnableDisableData[21] = new barcodeDataTable(292, 1, 0, 0, 0, 0, "DATAMATRIX", "01234567890512345");
        EnableDisableData[22] = new barcodeDataTable(294, 1, 0, 0, 0, 0, "MAXICODE", "01234567890512345");
        EnableDisableData[23] = new barcodeDataTable(293, 1, 0, 0, 0, 0, "QR CODE", "01234567890512345");
        EnableDisableData[24] = new barcodeDataTable(573, 1, 0, 0, 0, 0, "MICRO QR", "ABC123ABC");
        EnableDisableData[25] = new barcodeDataTable(574, 1, 0, 0, 0, 0, "AZTEC", "1234567890ABCabc");
        EnableDisableData[26] = new barcodeDataTable(1167, 1, 0, 0, 0, 0, "HANXIN", "Han-Xin");

        CodeID[0] = new barcodeDataTable(45, 1, 0, 0, 0, 0, "AIMIDFUN", "]E00012345678905");
        CodeID[1] = new barcodeDataTable(45, 2, 0, 0, 0, 0, "SYMBIDFUN", "A012345678905");

        RQZ[0] = new barcodeDataTable(1289, 0, 1288, 0, 0, 0, "", "");
        RQZ[1] = new barcodeDataTable(1289, 0, 1288, 0, 0, 0, "", "");
        RQZ[2] = new barcodeDataTable(1289, 0, 1288, 0, 0, 0, "", "");
        RQZ[3] = new barcodeDataTable(1289, 0, 1288, 0, 0, 0, "", "");
        RQZ[4] = new barcodeDataTable(1289, 0, 1288, 1, 0, 0, "", "");
        RQZ[5] = new barcodeDataTable(1289, 0, 1288, 1, 0, 0, "", "");
        RQZ[6] = new barcodeDataTable(1289, 0, 1288, 1, 0, 0, "", "");
        RQZ[7] = new barcodeDataTable(1289, 0, 1288, 1, 0, 0, "", "");
        RQZ[8] = new barcodeDataTable(1289, 0, 1288, 2, 0, 0, "", "");
        RQZ[9] = new barcodeDataTable(1289, 0, 1288, 2, 0, 0, "", "");
        RQZ[10] = new barcodeDataTable(1289, 0, 1288, 2, 0, 0, "", "");
        RQZ[11] = new barcodeDataTable(1289, 0, 1288, 2, 0, 0, "", "");
        RQZ[12] = new barcodeDataTable(1289, 0, 1288, 3, 0, 0, "", "");
        RQZ[13] = new barcodeDataTable(1289, 0, 1288, 3, 0, 0, "", "");
        RQZ[14] = new barcodeDataTable(1289, 0, 1288, 3, 0, 0, "", "");
        RQZ[15] = new barcodeDataTable(1289, 0, 1288, 3, 0, 0, "", "");
        RQZ[16] = new barcodeDataTable(1289, 1, 1288, 0, 0, 0, "", "");
        RQZ[17] = new barcodeDataTable(1289, 1, 1288, 0, 0, 0, "", "");
        RQZ[18] = new barcodeDataTable(1289, 1, 1288, 0, 0, 0, "", "");
        RQZ[19] = new barcodeDataTable(1289, 1, 1288, 0, 0, 0, "", "");
        RQZ[20] = new barcodeDataTable(1289, 1, 1288, 1, 0, 0, "", "");
        RQZ[21] = new barcodeDataTable(1289, 1, 1288, 1, 0, 0, "", "");
        RQZ[22] = new barcodeDataTable(1289, 1, 1288, 1, 0, 0, "", "");
        RQZ[23] = new barcodeDataTable(1289, 1, 1288, 1, 0, 0, "", "");
        RQZ[24] = new barcodeDataTable(1289, 1, 1288, 2, 0, 0, "", "");
        RQZ[25] = new barcodeDataTable(1289, 1, 1288, 2, 0, 0, "", "");
        RQZ[26] = new barcodeDataTable(1289, 1, 1288, 2, 0, 0, "", "");
        RQZ[27] = new barcodeDataTable(1289, 1, 1288, 2, 0, 0, "", "");
        RQZ[28] = new barcodeDataTable(1289, 1, 1288, 3, 0, 0, "", "");
        RQZ[29] = new barcodeDataTable(1289, 1, 1288, 3, 0, 0, "", "");
        RQZ[30] = new barcodeDataTable(1289, 1, 1288, 3, 0, 0, "", "");
        RQZ[31] = new barcodeDataTable(1289, 1, 1288, 3, 0, 0, "", "");
        RQZ[32] = new barcodeDataTable(1209, 1, 1288, 3, 0, 0, "", "");
        RQZ[33] = new barcodeDataTable(1208, 1, 1288, 3, 0, 0, "", "");
        RQZ[34] = new barcodeDataTable(1289, 1, 1288, 3, 0, 0, "", "");
        RQZ[35] = new barcodeDataTable(1289, 1, 1288, 3, 0, 0, "", "");
        RQZ[36] = new barcodeDataTable(1210, 1, 1288, 3, 22, 12, "", "");
        RQZ[37] = new barcodeDataTable(1289, 1, 1288, 3, 16, 1, "", "");
        RQZ[38] = new barcodeDataTable(1289, 1, 1288, 3, 1208, 1, "", "");
        RQZ[39] = new barcodeDataTable(1208, 1, 1288, 3, 577, 1, "", "");

        IgnoreFNC4[0] = new barcodeDataTable(0, 0, 0, 0, 0, 0, "", "");
        IgnoreFNC4[1] = new barcodeDataTable(0, 0, 0, 0, 0, 0, "", "");
        IgnoreFNC4[2] = new barcodeDataTable(0, 0, 0, 0, 0, 0, "", "");
        IgnoreFNC4[3] = new barcodeDataTable(0, 0, 0, 0, 0, 0, "", "");
        IgnoreFNC4[4] = new barcodeDataTable(0, 0, 0, 0, 0, 0, "", "");
        IgnoreFNC4[5] = new barcodeDataTable(1254, 1, 0, 0, 0, 0, "", "");
        IgnoreFNC4[6] = new barcodeDataTable(1254, 1, 0, 0, 0, 0, "", "");
        IgnoreFNC4[7] = new barcodeDataTable(1254, 1, 0, 0, 0, 0, "", "");
        IgnoreFNC4[8] = new barcodeDataTable(1254, 1, 0, 0, 0, 0, "", "");
        IgnoreFNC4[9] = new barcodeDataTable(1254, 1, 0, 0, 0, 0, "", "");
    }

    static void InitializeOCR() {

        OCRQuietZone[0] = new barcodeDataTable(680, 1, 695, 23, 0, 0, "OCRA-2C3C", "");
        OCRQuietZone[1] = new barcodeDataTable(680, 1, 695, 23, 0, 0, "OCRA-6C3C", "");
        OCRQuietZone[2] = new barcodeDataTable(680, 1, 695, 63, 0, 0, "OCRA-6C8C", "");
        OCRQuietZone[3] = new barcodeDataTable(680, 1, 695, 63, 0, 0, "OCRA-8C8C", "");
        OCRQuietZone[4] = new barcodeDataTable(680, 1, 695, 95, 0, 0, "OCRA-8C12C", "");
        OCRQuietZone[5] = new barcodeDataTable(680, 1, 695, 95, 0, 0, "OCRA-12C12C", "");
        OCRQuietZone[6] = new barcodeDataTable(680, 1, 695, 23, 0, 0, "OCRA-2C3C", "");
        OCRQuietZone[7] = new barcodeDataTable(680, 1, 695, 23, 0, 0, "OCRA-6C3C", "");
        OCRQuietZone[8] = new barcodeDataTable(680, 1, 695, 63, 0, 0, "OCRA-6C8C", "");
        OCRQuietZone[9] = new barcodeDataTable(680, 1, 695, 63, 0, 0, "OCRA-8C8C", "");
        OCRQuietZone[10] = new barcodeDataTable(680, 1, 695, 95, 0, 0, "OCRA-8C12C", "");
        OCRQuietZone[11] = new barcodeDataTable(680, 1, 695, 95, 0, 0, "OCRA-12C12C", "");
        OCRQuietZone[12] = new barcodeDataTable(680, 1, 695, 23, 0, 0, "OCRA-2C3C", "");
        OCRQuietZone[13] = new barcodeDataTable(680, 1, 695, 23, 0, 0, "OCRA-6C3C", "");
        OCRQuietZone[14] = new barcodeDataTable(680, 1, 695, 63, 0, 0, "OCRA-6C8C", "");
        OCRQuietZone[15] = new barcodeDataTable(680, 1, 695, 63, 0, 0, "OCRA-8C8C", "");
        OCRQuietZone[16] = new barcodeDataTable(680, 1, 695, 95, 0, 0, "OCRA-8C12C", "");
        OCRQuietZone[17] = new barcodeDataTable(680, 1, 695, 95, 0, 0, "OCRA-12C12C", "");
        OCRQuietZone[18] = new barcodeDataTable(680, 1, 695, 23, 0, 0, "OCRB-2C3C", "");
        OCRQuietZone[19] = new barcodeDataTable(681, 1, 695, 23, 0, 0, "OCRB-6C3C", "");
        OCRQuietZone[20] = new barcodeDataTable(681, 1, 695, 63, 0, 0, "OCRB-6C8C", "");
        OCRQuietZone[21] = new barcodeDataTable(681, 1, 695, 63, 0, 0, "OCRB-8C8C", "");
        OCRQuietZone[22] = new barcodeDataTable(681, 1, 695, 95, 0, 0, "OCRB-8C12C", "");
        OCRQuietZone[23] = new barcodeDataTable(681, 1, 695, 95, 0, 0, "OCRB-12C12C", "");
        OCRQuietZone[24] = new barcodeDataTable(681, 1, 695, 23, 0, 0, "OCRB-2C3C", "");
        OCRQuietZone[25] = new barcodeDataTable(681, 1, 695, 23, 0, 0, "OCRB-6C3C", "");
        OCRQuietZone[26] = new barcodeDataTable(681, 1, 695, 63, 0, 0, "OCRB-6C8C", "");
        OCRQuietZone[27] = new barcodeDataTable(681, 1, 695, 63, 0, 0, "OCRB-8C8C", "");
        OCRQuietZone[28] = new barcodeDataTable(681, 1, 695, 95, 0, 0, "OCRB-8C12C", "");
        OCRQuietZone[29] = new barcodeDataTable(681, 1, 695, 95, 0, 0, "OCRB-12C12C", "");
        OCRQuietZone[30] = new barcodeDataTable(681, 1, 695, 23, 0, 0, "OCRB-2C3C", "");
        OCRQuietZone[31] = new barcodeDataTable(681, 1, 695, 23, 0, 0, "OCRB-6C3C", "");
        OCRQuietZone[32] = new barcodeDataTable(681, 1, 695, 63, 0, 0, "OCRB-6C8C", "");
        OCRQuietZone[33] = new barcodeDataTable(681, 1, 695, 63, 0, 0, "OCRB-8C8C", "");
        OCRQuietZone[34] = new barcodeDataTable(681, 1, 695, 95, 0, 0, "OCRB-8C12C", "");
        OCRQuietZone[35] = new barcodeDataTable(681, 1, 695, 95, 0, 0, "OCRB-12C12C", "");
        OCRQuietZone[36] = new barcodeDataTable(681, 1, 695, 23, 0, 0, "MICR-2C3C", "");
        OCRQuietZone[37] = new barcodeDataTable(682, 1, 695, 23, 0, 0, "MICR-6C3C", "");
        OCRQuietZone[38] = new barcodeDataTable(682, 1, 695, 63, 0, 0, "MICR-6C8C", "");
        OCRQuietZone[39] = new barcodeDataTable(682, 1, 695, 63, 0, 0, "MICR-8C8C", "");
        OCRQuietZone[40] = new barcodeDataTable(682, 1, 695, 95, 0, 0, "MICR-8C12C", "");
        OCRQuietZone[41] = new barcodeDataTable(682, 1, 695, 95, 0, 0, "MICR-12C12C", "");

        OCRInverse[0] = new barcodeDataTable(680, 1, 856, 0, 547, 0, "OCR-RegInvReg", "99999999");
        OCRInverse[1] = new barcodeDataTable(680, 1, 856, 1, 547, 0, "OCR-RegInvInv", "99999999");
        OCRInverse[2] = new barcodeDataTable(680, 1, 856, 2, 547, 0, "OCR-RegInvAuto", "99999999");
        OCRInverse[3] = new barcodeDataTable(680, 1, 856, 0, 547, 0, "OCR-InvInvReg", "99999999");
        OCRInverse[4] = new barcodeDataTable(680, 1, 856, 1, 547, 0, "OCR-InvInvInv", "99999999");
        OCRInverse[5] = new barcodeDataTable(680, 1, 856, 2, 547, 0, "OCR-InvInvAuto", "99999999");

        OCRFonts[0] = new barcodeDataTable(680, 0, 0, 0, 0, 0, "OCRA-OCRDisable", "");
        OCRFonts[1] = new barcodeDataTable(680, 0, 0, 0, 0, 0, "OCRA-Disable", "");
        OCRFonts[2] = new barcodeDataTable(680, 1, 0, 0, 0, 0, "OCRA-Enable", "");
        OCRFonts[3] = new barcodeDataTable(680, 1, 684, 1, 0, 0, "OCRA-EnableRes1", "");
        OCRFonts[4] = new barcodeDataTable(680, 1, 684, 2, 0, 0, "OCRA-EnableRes2", "");
        OCRFonts[5] = new barcodeDataTable(680, 1, 684, 3, 0, 0, "OCRA-EnableBank", "");
        OCRFonts[6] = new barcodeDataTable(680, 1, 0, 0, 0, 0, "OCRA-Similar", "");
        OCRFonts[7] = new barcodeDataTable(681, 0, 0, 0, 0, 0, "OCRB-Disable", "");
        OCRFonts[8] = new barcodeDataTable(681, 1, 0, 0, 0, 0, "OCRB-Enable", "");
        OCRFonts[9] = new barcodeDataTable(681, 1, 685, 1, 0, 0, "OCRB-EnaBank", "");
        OCRFonts[10] = new barcodeDataTable(681, 1, 685, 2, 0, 0, "OCRB-EnaLimit", "");
        OCRFonts[11] = new barcodeDataTable(681, 1, 685, 6, 0, 0, "OCRB-EnaISBN10", "");
        OCRFonts[12] = new barcodeDataTable(681, 1, 685, 7, 0, 0, "OCRB-EnaISBN13", "");
        OCRFonts[13] = new barcodeDataTable(681, 1, 685, 3, 691, 3, "OCRB-EnaTravel1", "");
        OCRFonts[14] = new barcodeDataTable(681, 1, 685, 8, 691, 2, "OCRB-EnaTravel2", "");
        OCRFonts[15] = new barcodeDataTable(681, 1, 685, 20, 691, 3, "OCRB-EnaTraAuto", "");
        OCRFonts[16] = new barcodeDataTable(681, 1, 685, 4, 691, 2, "OCRB-EnaPP", "");
        OCRFonts[17] = new barcodeDataTable(681, 1, 685, 9, 691, 2, "OCRB-EnaVisaA", "");
        OCRFonts[18] = new barcodeDataTable(681, 1, 685, 10, 691, 2, "OCRB-EnaVisaB", "");
        OCRFonts[19] = new barcodeDataTable(681, 1, 685, 11, 691, 2, "OCRB-EnaICAO", "");
        OCRFonts[20] = new barcodeDataTable(681, 1, 0, 0, 0, 0, "OCRB-Similar", "");
        OCRFonts[21] = new barcodeDataTable(682, 0, 0, 0, 0, 0, "MICR-Disable", "");
        OCRFonts[22] = new barcodeDataTable(682, 1, 0, 0, 0, 0, "MICR-Enable", "");
        OCRFonts[23] = new barcodeDataTable(683, 0, 0, 0, 0, 0, "USCurrencyDis", "");
        OCRFonts[24] = new barcodeDataTable(683, 1, 0, 0, 0, 0, "USCurrencyEna", "");

        OCRCheckDigit[0] = new barcodeDataTable(700, 1234543212, 688, 2, 694, 3, "OCR-ProAddLtoR", "");
        OCRCheckDigit[1] = new barcodeDataTable(700, 107423879, 688, 2, 694, 1, "OCR-ProAddRtoL", "");
        OCRCheckDigit[2] = new barcodeDataTable(700, 7777777, 688, 19, 694, 4, "OCR-DigiAddLtoR", "");
        OCRCheckDigit[3] = new barcodeDataTable(700, 217942908, 688, 3, 694, 2, "OCR-DigitAddRtoL", "");
        OCRCheckDigit[4] = new barcodeDataTable(700, 12222, 688, 12, 694, 5, "OCR-ProAddRtoLRemind", "");
        OCRCheckDigit[5] = new barcodeDataTable(700, 1789569, 688, 50, 694, 6, "OCR-DigitAddLtoRRemind", "");
        OCRCheckDigit[6] = new barcodeDataTable(700, 1, 688, 43, 694, 9, "OCR-Health", "");

        OCRTemplate[0] = new barcodeDataTable(680, 1, 686, 0, 0, 0, "OCR-AllChar", "ABC123");
        OCRTemplate[1] = new barcodeDataTable(681, 1, 686, 0, 0, 0, "OCR-SomeChar", "ABC123");
        OCRTemplate[2] = new barcodeDataTable(680, 1, 686, 0, 0, 0, "OCR-OutChar", "ABC123");
        OCRTemplate[3] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-RARDOAnOAInvalid", "AA991122");
        OCRTemplate[4] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-RARDOAnOAValid", "AA991122");
        OCRTemplate[5] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-AorDOSmallSpecInvalid", "AAA335..");
        OCRTemplate[6] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-AorDOSmallSpecValid", "AAA335..");
        OCRTemplate[7] = new barcodeDataTable(681, 1, 547, 0, 0, 0, "OCR-DOrFAOrFODOSInvalid", "88 FF 77");
        OCRTemplate[8] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-DOrFAOrFODOSValid", "88 FF 77");
        OCRTemplate[9] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-LStringInvalid", "AB+XY");
        OCRTemplate[10] = new barcodeDataTable(681, 1, 547, 0, 0, 0, "OCR-LStringValid", "AB+XY");
        OCRTemplate[11] = new barcodeDataTable(681, 1, 547, 0, 0, 0, "OCR-Newline", "99999EAAAAA");
        OCRTemplate[12] = new barcodeDataTable(681, 1, 547, 0, 0, 0, "OCR-StrExtract", "C>A>");
        OCRTemplate[13] = new barcodeDataTable(682, 1, 547, 0, 0, 0, "OCR-IgnoretoEnd", "99999D");
        OCRTemplate[14] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-SkipUntill", "P1\"DR\"AAAAAAA");
        OCRTemplate[15] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-SkipUntillNot", "P0\"USS\"AAAAAAAAAA");
        OCRTemplate[16] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-RepeatInvalid", "AA1R");
        OCRTemplate[17] = new barcodeDataTable(680, 1, 547, 0, 0, 0, "OCR-RepeatValid", "AA1R");
        OCRTemplate[18] = new barcodeDataTable(681, 1, 547, 0, 0, 0, "OCR-Scroll", "S99999");

        OCRBoundary[0] = new barcodeDataTable(681, 1, 691, 1, 0, 0, "OCR-ProAddLtoR", "");
        OCRBoundary[1] = new barcodeDataTable(681, 1, 691, 2, 0, 0, "OCR-ProAddRtoL", "");
        OCRBoundary[2] = new barcodeDataTable(681, 1, 691, 3, 0, 0, "OCR-DigiAddLtoR", "");
        OCRBoundary[3] = new barcodeDataTable(681, 1, 691, 2, 0, 0, "OCR-DigitAddRtoL", "");
        OCRBoundary[4] = new barcodeDataTable(681, 1, 689, 10, 690, 100, "OCR-ProAddRtoLRemind", "");
        OCRBoundary[5] = new barcodeDataTable(681, 1, 689, 3, 690, 100, "OCR-DigitAddLtoRRemind", "");
        OCRBoundary[6] = new barcodeDataTable(681, 1, 689, 3, 690, 10, "OCR-Health", "");
        OCRBoundary[7] = new barcodeDataTable(681, 1, 689, 3, 690, 11, "OCR-Health", "");

    }


}
