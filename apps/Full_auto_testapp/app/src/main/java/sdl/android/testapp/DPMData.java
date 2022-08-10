package sdl.android.testapp;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DPMData {

    private static final String DPM1 = "ABCDFE"; //Metal - Large hunk of metal
    private static final String DPM2 = "ABCDFE"; //Metal - Large hunk of metal
    private static final String DPM3 = "SC0001"; //Metal - Medical Clamp
    private static final String DPM4 = "MOTOROLA DPM"; //Metal - Motorola DPM samples
    private static final String DPM5 = "P4039360,1T0512906181"; //Metal - Steel mechanical part
    private static final String DPM6 = "(20T)EZ668622535H"; //Metal - Black mechanical part
    private static final String DPM7 = "ST046604330F8K0930"; //Printed - Phone battery
    private static final String DPM8 = "SAD1M0125-17508A008600818120000"; //Printed - Contact Case
    private static final String DPM9 = "J000T00202092"; //Printed - PCB

    public static final String getDPMSampleOLD(int sample) {
        switch (sample) {
            case 1:
                return DPM1;
            case 2:
                return DPM2;
            case 3:
                return DPM3;
            case 4:
                return DPM4;
            case 5:
                return DPM5;
            case 6:
                return DPM6;
            case 7:
                return DPM7;
            case 8:
                return DPM8;
            case 9:
                return DPM9;
            default:
                return "INVALID";
        }
    }

    public static final String getDPMSample(int sample) {
        String acquiredSample = "INVALID";

        if (!(Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED))) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String str = Environment.getExternalStorageDirectory().getAbsolutePath();
                str += "/DPMSamples.txt";
                try {
                    int count = 1;
                    File file = new File(str);
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {

                        if (count == sample && count <= 9) {
                            acquiredSample = line;
                        }
                        count++;
                    }
                    fileReader.close();

                } catch (IOException e) {
                    acquiredSample = "FILE NOT FOUND";
                    e.printStackTrace();
                }

            } else {
                acquiredSample = "EXTERNAL STORAGE WRITING ERROR";
                //output.append("Cannot write Log because the External Storage is not available for writing");
            }
        } else {
            acquiredSample = "EXTERNAL STORAGE ERROR";
            //output.append("Cannot Write to Log file because there is no External Storage detected");
        }

        return acquiredSample;

    }
}
