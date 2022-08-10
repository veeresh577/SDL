package sdl.android.testapp;

public class mdData {
    private static final int[] decodesExpected = {2, 2, 1, 1, 0, 3, 3, 3, 3, 1, 10, 3, 3, 3, 1, 0, 5, 5, 0, 3, 3, 3};

    private static final String[] test1Expected = {"123456789012", "012345678905"}; //Multi #1
    private static final String[] test2Expected = {"123456789012", "012345678905", "466459286608", "374247843233"}; //Multi #s 1-3
    private static final String[] test3Expected = {"123456789012", "112233445562", "888888888886"};//Multi #4
    private static final String[] test4Expected = {"123456789012", "ABCabc", "01234565"};//Multi #5
    private static final String[] test5Expected = {"123456789012", "000000000000", "888888888886", "188833247479", "122446688007", "112233445562", "593052222815", "831530475965", "750861159132", "804764299195", "804764299997"}; //Multi #6-7
    private static final String[] test6Expected = {"123456789012", "112233445562", "888888888886", "123123123125", "0123456789050123456789012345678901234567890123456789012345678901234"};//Multi #9
    private static final String[] test7Expected = {"123456789012", "112233445562", "888888888886", "123123123125", "PASSED"};//Multi #10
    private static final String[] test8Expected = {"PASSED", "FAILED", "0123456789", "abcdefghi"};//Multi #11-13
    private static final String[] test9Expected = {"PASSED", "0123456789ABCDEFGHIJKLMNOPQRST", "Please Let This Test Case Pass"};//Multi #14
    private static final String[] testA1Expected = {"FirstSample", "SecondSample"}; //DPM Multi #1
    private static final String[] testA2Expected = {"FirstSample", "SecondSample", "ThirdSample"}; //DPM Multi #2

    public static boolean TestForMatch(String data, int testNum) {
        String[] expectedData;

        if (testNum == 1 || testNum == 2 || testNum == 3 || testNum == 4 || testNum == 10)
            expectedData = test1Expected;
        else if (testNum == 5 || testNum == 6 || testNum == 7)
            expectedData = test2Expected;
        else if (testNum == 8)
            expectedData = test3Expected;
        else if (testNum == 9 || testNum == 12 || testNum == 13)
            expectedData = test4Expected;
        else if (testNum == 11 || testNum == 14)
            expectedData = test5Expected;
        else if (testNum == 17)
            expectedData = test6Expected;
        else if (testNum == 18)
            expectedData = test7Expected;
        else if (testNum == 19 || testNum == 20 || testNum == 21)
            expectedData = test8Expected;
        else if (testNum == 22)
            expectedData = test9Expected;
        else
            return false;

        for (int i = 0; i < expectedData.length; i++) {
            if (expectedData[i].equals(data)) return true;
        }

        return false;
    }

    public static boolean DPMTestForMatch(String data, int testNum) {
        String[] expectedData;

        if (testNum == 1 || testNum == 2)
            expectedData = testA1Expected;
        else if (testNum == 4)
            expectedData = testA2Expected;
        else
            return false;

        for (int i = 0; i < expectedData.length; i++) {
            if (expectedData[i].equals(data)) return true;
        }

        return false;
    }


    public static boolean TestNumberofDecodes(int testNum, int returnedNumberOfDecodes) {
        if (testNum == 12) return decodesExpected[testNum - 1] >= returnedNumberOfDecodes;
        return (decodesExpected[testNum - 1] == returnedNumberOfDecodes);
    }

    public static boolean DPMTestNumberofDecodes(int testNum, int returnedNumberOfDecodes) {
        if (testNum == 1) return (2 == returnedNumberOfDecodes);
        else if (testNum == 2) return (1 == returnedNumberOfDecodes);
        else if (testNum == 3) return (0 == returnedNumberOfDecodes);
        else if (testNum == 4) return (3 == returnedNumberOfDecodes);
        else return false;
    }
}
