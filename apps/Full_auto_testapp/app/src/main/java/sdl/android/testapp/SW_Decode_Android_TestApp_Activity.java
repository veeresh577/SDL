/*

 SDL Test App Activity

 Created by Dgupta1 on 10/10/2019.

*/
package sdl.android.testapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zebra.adc.decoder.BarCodeReader;
import com.zebra.adc.decoder.BarCodeReader.PropertyNum;

import org.apache.http.util.ByteArrayBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class SW_Decode_Android_TestApp_Activity extends Activity
        implements BarCodeReader.DecodeCallback, BarCodeReader.PictureCallback, BarCodeReader.PreviewCallback, SurfaceHolder.Callback, BarCodeReader.VideoCallback {

    private static BarCodeReader bcr = null;
    EditText output;
    TextView scan_data;
    Button decode, clear, last_image;
    static int PrimTest = 0;
    static int testcase = 0;
    private long startDecodeTime = 0;
    private boolean start = false;
    static boolean DecodeEvent = false;
    static boolean MotionEvent = false;
    static boolean ImageEvent = false;

    ImageView image;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder = null;
    Bundle bundledStateData;
    static private boolean videoCapDisplayStarted = true;
    private ToneGenerator tg = null;
    private boolean isEnabledBeep = false;
    private boolean atMain = false;
    boolean pass = false;
    boolean finalResult = true;
    String engineType = "";
    String Step9Result = "";

    boolean isSE2100 = false;

    String TAG = SW_Decode_Android_TestApp_Activity.class.getSimpleName();

    static Handler testHandler;
    private int currentSecurityLevel = 0;
    private String modelNumber = "INVALID";

    //states
    static final int STATE_IDLE = 0;
    static final int STATE_DECODE = 1;
    static final int STATE_HANDSFREE = 2;
    static final int STATE_PREVIEW = 3;
    static final int STATE_SNAPSHOT = 4;
    static final int STATE_VIDEO = 5;

    private int state = STATE_IDLE;

    static {
        System.loadLibrary("IAL");
        System.loadLibrary("SDL");

        if (android.os.Build.VERSION.SDK_INT >= 26)
            System.loadLibrary("barcodereader80"); // Android 8.0
        else if (android.os.Build.VERSION.SDK_INT >= 24)
            System.loadLibrary("barcodereader70"); // Android 7.0
        else if (android.os.Build.VERSION.SDK_INT >= 19)
            System.loadLibrary("barcodereader44"); // Android 4.4
        else if (android.os.Build.VERSION.SDK_INT >= 18)
            System.loadLibrary("barcodereader43"); // Android 4.3
        else
            System.loadLibrary("barcodereader"); // Android 2.3 - Android 4.2
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        output = findViewById(R.id.output); // Text Field for output data
        decode = findViewById(R.id.decode); // UI Buttons for Scan
        // UI Buttons for Image
        last_image = findViewById(R.id.last_image);
        last_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowLastDecodedImage();
            }
        });
        clear = findViewById(R.id.clear); // Clear Button for clean the Text field
        scan_data = findViewById(R.id.scannedData);
        // Beep after scan
        tg = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);

        DecodeTimer myDecodeTimer = new DecodeTimer();
        bundledStateData = new Bundle();
        isEnabledBeep = false;

        decode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EnableTests(63);
            }
        });// When we press manually SCAN button on the Testapp
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                output.setText("");
                ImageView image = findViewById(R.id.image_view_sdl);
                image.invalidate();
                image.setImageResource(0);
            }
        });

        bundledStateData.putString("test_output", "");
        myDecodeTimer.start();
        createHandler();
    }


    /**
     * Method to handle test cases as per Test case number
     */
    private void createHandler() {
        testHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == 5000)
                    restoreDefaults();
                int result = bcr.setParameter(905, 1);
                if (result == BarCodeReader.BCR_ERROR) {
                    output.append("ERROR: Could not enable \"Get Last Decoded Image\". Aborting...\n");
                    return;
                }
                if (msg.what >= 1 && msg.what <= 8) {
                    PostalEnableTests(msg.what);
                }
                if (msg.what >= 9 && msg.what <= 16) {
                    PostalDisableTests(msg.what);
                }
                if (msg.what >= 17 && msg.what <= 24) {
                    CompositeTests(msg.what);
                }
                if (msg.what >= 25 && msg.what <= 29) {
                    InverseTests(msg.what);
                }
                if (msg.what >= 30 && msg.what <= 35) {
                    ConversionTests(msg.what);
                }
                if (msg.what >= 36 && msg.what <= 42) {
                    CheckDigitTests(msg.what);
                }
                if (msg.what >= 43 && msg.what <= 51) {
                    LengthTests(msg.what);
                }
                if (msg.what >= 52 && msg.what <= 62) {
                    SupplementalTests(msg.what);
                }
                if (msg.what >= 63 && msg.what <= 89) {
                    EnableTests(msg.what);
                }
                if (msg.what >= 90 && msg.what <= 116) {
                    DisableTests(msg.what);
                }
                if (msg.what >= 117 && msg.what <= 143) {
                    EnableAllTests(msg.what);
                }
                if (msg.what >= 144 && msg.what <= 170) {
                    DisableAllTests(msg.what);
                }
                if (msg.what >= 171 && msg.what <= 214) {
                    SecurityLevelTests(msg.what);
                }
                if (msg.what >= 215 && msg.what <= 254) {
                    ReduceQuietZoneTests(msg.what);
                }
                if (msg.what >= 255 && msg.what <= 264) {
                    IgnoreFNC4Tests(msg.what);
                }
                if (msg.what >= 447 && msg.what <= 448) {
                    CodeIDTests(msg.what);
                }
                if (msg.what >= 500 && msg.what <= 541) {
                    OCRQuietZoneTests(msg.what);
                }
                if (msg.what >= 542 && msg.what <= 547) {
                    OCRInverseTests(msg.what);
                }
                if (msg.what >= 548 && msg.what <= 572) {
                    OCRFontsTests(msg.what);
                }
                if (msg.what >= 573 && msg.what <= 579) {
                    OCRCheckDigitTests(msg.what);
                }
                if (msg.what >= 580 && msg.what <= 598) {
                    OCRSubsetTemplatesTests(msg.what);
                }
                if (msg.what >= 599 && msg.what <= 606) {
                    OCRBoundaryTests(msg.what);
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        String storeText;
        // Save the test results from the editText box in a bundle
        storeText = output.getText().toString();
        bundledStateData.putString("test_output", storeText);
        if (bcr != null) {
            bcr.release();
            bcr = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        state = STATE_IDLE;

        String restoreText;
        String AndroidDeviceID = android.os.Build.MODEL;
        int openValue = 0;

        // Restore the test results from the bundledStateData bundle
        restoreText = bundledStateData.getString("test_output");
        output.append(restoreText);
        if (AndroidDeviceID.contains("MC40") || AndroidDeviceID.contains("MC32"))
            openValue = 42;

        if (android.os.Build.VERSION.SDK_INT >= 18)
            bcr = BarCodeReader.open(openValue, getApplicationContext()); // Android 4.3 and above
        else
            bcr = BarCodeReader.open(openValue);

        try {
            if (bcr == null) {
                output.append("open failed");
                return;
            }
            bcr.setDecodeCallback(this);
        } catch (Exception e) {
            output.append("Exception: " + e.toString());
        }

        if (bcr != null) {
            modelNumber = bcr.getStrProperty(PropertyNum.MODEL_NUMBER);
            isSE2100 = modelNumber.contains("BOCV");
        }
        //isSE2100 = true; //Temporary
    }

    private void mainScreen() {

        if (atMain)
            return;
        String restoreText;
        atMain = true;
        //Restore output reference
        output = findViewById(R.id.output);
        restoreText = bundledStateData.getString("test_output");
        output.append(restoreText);
        if (PrimTest == 6 && testcase == 9)
            output.append(Step9Result);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        String storeText;
        // Save the test results from the editText box in a bundle
        storeText = output.getText().toString();
        bundledStateData.putString("test_output", storeText);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String restoreText;
        // Restore the test results from the bundledStateData bundle
        restoreText = bundledStateData.getString("test_output");
        output.append(restoreText);
    }

    /**
     * Method to on Click Exit button
     */
    public void exitClick(View v) {
        if (bcr != null) {
            bcr.release();
            bcr = null;
        }
        this.finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bcr != null) {
            bcr.release();
            bcr = null;
        }

    }

    OnClickListener mImageClickListener = new OnClickListener() {
        public void onClick(View v) {
            return;
        }
    };

    // ------------------------------------------------------
    // callback for take-picture button on snap-preview screen
    OnClickListener mTakePicListener = new OnClickListener() {
        public void onClick(View v) {
//            doSnap1();
        }
    };


    public void onPreviewFrame(byte[] data, BarCodeReader reader) {
        // TODO Auto-generated method stub

    }

//    ImageView lastImage;

    /**
     * Method to ShowLastDecodedImage
     */
    private void ShowLastDecodedImage() {
        int result = bcr.setParameter(905, 1);
        Log.d(TAG, "GetLastDecodedImageTests : " + result);

        if (result == BarCodeReader.BCR_ERROR) {
            output.append("ERROR: Could not enable \"Get Last Decoded Image\". Aborting...\n");
            return;
        }
        byte[] data = bcr.getLastDecImage();
        System.out.println("bytes_a 1 : " + data);
        System.out.println("bytes_a : " + Arrays.toString(data));
        saveLastScanImage(data);
        // display snapshot
        Bitmap bmSnap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (bmSnap == null) {
            output.append("LastImageDecodeComplete: no bitmap");
        }
        ImageView image = findViewById(R.id.image_view_sdl);
        image.setAdjustViewBounds(true);

        image.setImageBitmap(bmSnap);
    }

    /**
     * Method to snapshot image screen
     *
     * @param bmSnap
     */
    private void snapScreen(Bitmap bmSnap) {
        atMain = false;
        setContentView(R.layout.image);
        image = findViewById(R.id.snap_image);
        image.setOnClickListener(mImageClickListener);

        if (bmSnap != null)
            image.setImageBitmap(bmSnap);
    }

    /**
     * Method to saveLastScanImage to device storage
     *
     * @param data
     */
    private void saveLastScanImage(byte[] data) {
        String mBaseFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SDL_Image";
        String name = "Last_decode_Image.jpg";
        String mFilePath_ = mBaseFolderPath + "/" + name;
        File imgFile = new File(mFilePath_);
        if (!new File(mBaseFolderPath).exists()) {
            new File(mBaseFolderPath).mkdirs();
        }

        if (imgFile.exists()) imgFile.delete();
        System.out.println("File name is : " + imgFile);
        try {
            ByteArrayBuffer bab = new ByteArrayBuffer(data.length);
            imgFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imgFile);
            bab.append(data, 0, data.length);
            fos.write(bab.buffer());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method setSession Timeout to 5 seconds
     */
    private void setSessionTimeout() {
        int result = 0;
        result = bcr.setParameter(136, 50);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Decode Session Timeout\nTest Aborted");
            return;
        }
    }

    /**
     * Method to enable No Read
     */
    private void setNoReadEnable() {
        int result = 0;
        result = bcr.setParameter(94, 1);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set No Read Parameter\nTest Aborted");
            return;
        }
    }

    /**
     * Method to Oreintation 180 for OCR Test cases.
     */
    private void setOreintation_two() {
        int result = 0;
        result = bcr.setParameter(687, 2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Oreintaion to 180degree\n");
        }
    }

    /*
     * Method for Postal Enable testing
     *
     */
    public void PostalEnableTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 1;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.PostalData[testcase].paramNum, barcodeData.PostalData[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
            Log.d("SDL_SCAN", "BCR_ERROR");
        }

        doDecode();
    }

    /*
     * Method for Postal Disable testing
     */
    public void PostalDisableTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 9;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.PostalData[testcase].paramNum, barcodeData.PostalData[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
            Log.d("SDL_SCAN", "BCR_ERROR");
        }

        doDecode();
    }

    /*
     * Method for Composite testing
     */
    public void CompositeTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 17;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.CompositeData[testcase].paramNum, barcodeData.CompositeData[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
        }

        /* Set Test Parameters 2 */
        result = bcr.setParameter(barcodeData.CompositeData[testcase].paramNum2, barcodeData.CompositeData[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2\n");
        }

        doDecode();
    }

    /*
     * Method for Inverse testing
     */
    public void InverseTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 25;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.InverseData[testcase].paramNum, barcodeData.InverseData[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
        }

        /* Set Test Parameters 2*/
        result = bcr.setParameter(barcodeData.InverseData[testcase].paramNum2, barcodeData.InverseData[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2\n");

        }

        doDecode();
    }

    /*
     * Method for Conversion testing
     */
    public void ConversionTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 30;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.ConversionData[testcase].paramNum, barcodeData.ConversionData[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
        }

        /* Set Test Parameters 2*/
        result = bcr.setParameter(barcodeData.ConversionData[testcase].paramNum2, barcodeData.ConversionData[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2");
        }

        /* Set Test Parameters 3*/
        result = bcr.setParameter(barcodeData.ConversionData[testcase].paramNum3, barcodeData.ConversionData[testcase].paramVal3);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 3");
        }

        doDecode();
    }

    /*
     * Method for Check Digit testing
     */
    public void CheckDigitTests(int nexTest) {

        int result = 0;
        testcase = nexTest - 36;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.CheckDigitData[testcase].paramNum, barcodeData.CheckDigitData[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
        }

        /* Set Test Parameters 2*/
        result = bcr.setParameter(barcodeData.CheckDigitData[testcase].paramNum2, barcodeData.CheckDigitData[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2");
        }

        /* Set Test Parameters 3*/
        result = bcr.setParameter(barcodeData.CheckDigitData[testcase].paramNum3, barcodeData.CheckDigitData[testcase].paramVal3);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 3");
        }

        /* Set Test Parameters for I25 sample with Check Digit enabled and OPCC Verification */
        if (testcase == 4) {
            /* Set Parameters Lengths for Interleaved 2 of 5 */
            result = bcr.setParameter(22, 4);
            if (result != BarCodeReader.BCR_SUCCESS) {
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Interleaved 2 of 5 Length");
                Log.d("SDL_SCAN", "ERROR");
            }

            /* Set Parameters Lengths 2 for Interleaved 2 of 5 */
            result = bcr.setParameter(23, 14);
            if (result != BarCodeReader.BCR_SUCCESS) {
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Interleaved 2 of 5 Length 2");
                Log.d("SDL_SCAN", "ERROR");
            }
        }

        doDecode();
    }

    /*
     * Method for Length testing
     */
    public void LengthTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 43;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.LengthsData[testcase].paramNum, barcodeData.LengthsData[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
        }

        /* Set Test Parameters 2*/
        result = bcr.setParameter(barcodeData.LengthsData[testcase].paramNum2, barcodeData.LengthsData[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2\n");
        }

        /* Set Test Parameters 3*/
        result = bcr.setParameter(barcodeData.LengthsData[testcase].paramNum3, barcodeData.LengthsData[testcase].paramVal3);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 3\n");
        }


        doDecode();
    }

    /*
     * Method for Supplemental testing
     */
    public void SupplementalTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 52;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.SupplementalData[testcase].paramNum, barcodeData.SupplementalData[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
        }

        /* Set Test Parameters 2*/
        if (testcase == 6 || testcase == 9 || testcase == 10) {
            result = bcr.setParameter(barcodeData.SupplementalData[testcase].paramNum2, barcodeData.SupplementalData[testcase].paramVal2);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2");
            }
        }

        /* Set Test Parameters 3*/
        if (testcase == 6) {
            result = bcr.setParameter(barcodeData.SupplementalData[testcase].paramNum3, barcodeData.SupplementalData[testcase].paramVal3);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 3");
            }
        }

        doDecode();
    }

    /*
     * Method for Enable testing
     */
    public void EnableTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 63;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.EnableDisableData[testcase].paramNum, barcodeData.EnableDisableData[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
        }

        /* Set MSI length */
        if (testcase == 16) {
            /* Set MSI to length 1 */
            result = bcr.setParameter(30, 12);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not MSI Length");
            }
            /* Set MSI to length 2 */
            result = bcr.setParameter(31, 0);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not MSI Length 2");
            }
        }

        /* Set Discrete 2 of 5 to any length */
        if (testcase == 13) {
            /* Set Discrete 2 of 5 to length 1 */
            result = bcr.setParameter(20, 5);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Discrete Length");
            }

            /* Set Discrete 2 of 5 to length 2 */
            result = bcr.setParameter(21, 0);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Discrete Length 2");
            }

        }

        doDecode();
    }

    /*
     * Method for Disable testing
     */
    public void DisableTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 90;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.EnableDisableData[testcase].paramNum, barcodeData.EnableDisableData[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
        }

        doDecode();
    }

    /*
     * Method for Enable All testing
     */
    public void EnableAllTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 117;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Test Parameters */
        bcr.enableAllCodeTypes();

        /* Set Parameter 2 for Code 128b */
        if (testcase == 6) {
            /* Set Parameter for Code 128b */
            result = bcr.setParameter(594, 0);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2 for Code 128b");
            }

            /* Set Parameter 3 for Code 128b*/
            result = bcr.setParameter(681, 0);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 3 for Code 128b");
            }

        }

        /* Set Discrete 2 of 5 to any length */
        if (testcase == 13) {
            /* Set Discrete 2 of 5 to length 1 */
            result = bcr.setParameter(20, 5);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Discrete Length");
            }

            /* Set Discrete 2 of 5 to length 2 */
            result = bcr.setParameter(21, 0);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Discrete Length 2");
            }

        }

        doDecode();
    }

    /*
     * Method for Disable All testing
     */
    public void DisableAllTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 144;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        bcr.disableAllCodeTypes();

        doDecode();
    }

    /*
     * Method for Reduced Quiet Zone testing
     */
    public void ReduceQuietZoneTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 215;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.RQZ[testcase].paramNum, barcodeData.RQZ[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
        }

        result = bcr.setParameter(barcodeData.RQZ[testcase].paramNum2, barcodeData.RQZ[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
        }

        if (testcase == 36 || testcase == 37 || testcase == 38 || testcase == 39) {
            result = bcr.setParameter(barcodeData.RQZ[testcase].paramNum3, barcodeData.RQZ[testcase].paramVal3);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
            }
        }

        if (testcase == 38) {
            result = bcr.setParameter(85, 1);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
                return;
            }

            result = bcr.setParameter(730, 0);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
                return;
            }
        }
        doDecode();
    }

    /*
     * Method for Ignore FNC4 testing
     */
    public void IgnoreFNC4Tests(int nextTest) {

        int result = 0;
        testcase = nextTest - 255;
        barcodeData.Initialize();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        if (testcase == 5 || testcase == 6 || testcase == 7 || testcase == 8 || testcase == 9) {
            result = bcr.setParameter(barcodeData.IgnoreFNC4[testcase].paramNum, barcodeData.IgnoreFNC4[testcase].paramVal);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
            }
        }

        doDecode();
    }

    /***
     * Cycles through each security level testing each barcode in the security levels barcode document.
     * @param nextTest
     */
    public void SecurityLevelTests(int nextTest) {

        int result = 0;
        DecodeEvent = false;
        testcase = nextTest - 171;
        restoreDefaults();

        /* Set Security Level value */
        if ((nextTest >= 171) && (nextTest <= 181))
            currentSecurityLevel = 0;
        if ((nextTest >= 182) && (nextTest <= 192))
            currentSecurityLevel = 1;
        if ((nextTest >= 193) && (nextTest <= 203))
            currentSecurityLevel = 2;
        if ((nextTest >= 204) && (nextTest <= 214))
            currentSecurityLevel = 3;

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        /* Set Test Parameters */
        result = bcr.setParameter(77, currentSecurityLevel);
        if (result == BarCodeReader.BCR_ERROR) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
        }

        doDecode();
    }

    /*
     * Method for CodeID testing
     */
    public void CodeIDTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 447;
        restoreDefaults();
        barcodeData.Initialize();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();

        result = bcr.setParameter(barcodeData.CodeID[testcase].paramNum, barcodeData.CodeID[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
        }
        doDecode();

    }

    /*
     * Method for OCR Quiet Zone testing
     */
    public void OCRQuietZoneTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 500;
        restoreDefaults();
        barcodeData.InitializeOCR();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Oreintation 180 degree */
        setOreintation_two();

        /* Set Test Parameters 1*/
        result = bcr.setParameter(barcodeData.OCRQuietZone[testcase].paramNum, barcodeData.OCRQuietZone[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
        }
        /* Set Test Parameters 2*/
        result = bcr.setParameter(barcodeData.OCRQuietZone[testcase].paramNum2, barcodeData.OCRQuietZone[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2\n");
        }
        doDecode();

    }

    /*
     * Method for OCR Inverse testing
     */
    public void OCRInverseTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 542;
        restoreDefaults();
        barcodeData.InitializeOCR();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Oreintation 180 degree */
        setOreintation_two();

        /* Set Test Parameters 1*/
        result = bcr.setParameter(barcodeData.OCRInverse[testcase].paramNum, barcodeData.OCRInverse[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
//        } else {
//            output.append("\nTest " + (testcase + 1) + " Set #" + barcodeData.OCRInverse[testcase].paramNum + "to" + barcodeData.OCRInverse[testcase].paramVal + " Successfully\n");
        }

        /* Set Test Parameters 2*/
        result = bcr.setParameter(barcodeData.OCRInverse[testcase].paramNum2, barcodeData.OCRInverse[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2\n");
//        } else {
//            output.append("\nTest " + (testcase + 1) + " Set #" + barcodeData.OCRInverse[testcase].paramNum2 + "to" + barcodeData.OCRInverse[testcase].paramVal2 + " Successfully\n");
        }

        /* Set Templates Parameters */
        result = bcr.setParameter(barcodeData.OCRInverse[testcase].paramNum3, barcodeData.OCRInverse[testcase].data);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Templates Parameter \n");
//        } else {
//            output.append("\nTest " + (testcase + 1) + " Set #" + barcodeData.OCRInverse[testcase].paramNum3 + "to" + barcodeData.OCRInverse[testcase].paramVal3 + " Successfully\n");
        }

        doDecode();

    }

    /*
     * Method for OCR Fonts testing
     */
    public void OCRFontsTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 548;
        restoreDefaults();
        barcodeData.InitializeOCR();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Oreintation 180 degree */
        setOreintation_two();

        /* Set Test Parameters 1*/
        result = bcr.setParameter(barcodeData.OCRFonts[testcase].paramNum, barcodeData.OCRFonts[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
        }


        /* Set Test Parameters 2*/
        if (testcase == 3 || testcase == 4 || testcase == 5 || testcase == 9 || testcase == 10 || testcase == 11 || testcase == 12 || testcase == 13 || testcase == 14 || testcase == 15 || testcase == 16 || testcase == 17 || testcase == 18 || testcase == 19) {
            result = bcr.setParameter(barcodeData.OCRFonts[testcase].paramNum2, barcodeData.OCRFonts[testcase].paramVal2);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2\n");
            }
        }

        /* Set Templates Parameters 3*/
        if (testcase == 13 || testcase == 14 || testcase == 15 || testcase == 16 || testcase == 17 || testcase == 18 || testcase == 19) {
            result = bcr.setParameter(barcodeData.OCRFonts[testcase].paramNum3, barcodeData.OCRFonts[testcase].paramVal3);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 3\n");
            }
        }
        doDecode();

    }

    /*
     * Method for OCR Check Digit testing
     */
    public void OCRCheckDigitTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 573;
        restoreDefaults();
        barcodeData.InitializeOCR();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Oreintation 180 degree */
        setOreintation_two();

        if (testcase == 0 || testcase == 1 || testcase == 2 || testcase == 4 || testcase == 6) {

            result = bcr.setParameter(680, 1);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set No Read\nTest Aborted");
                return;
            }
        }

        if (testcase == 4 || testcase == 5) {
            result = bcr.setParameter(681, 1);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set No Read\nTest Aborted");
                return;
            }
        }

        if (testcase == 3) {
            result = bcr.setParameter(682, 1);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set No Read\nTest Aborted");
                return;
            }
        }
//        /* Set Check digit multiplier */
//        result = bcr.setParameter(barcodeData.OCRCheckDigit[testcase].paramNum, barcodeData.OCRCheckDigit[testcase].paramVal);
//        if (result != BarCodeReader.BCR_SUCCESS) {
//            Log.d("SDL_SCAN", "BCR_ERROR");
//            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
//        }else{
//            output.append("\nTest " + (testcase + 1) + " Successfully set Check Digit Multiplier \n");
//        }

        /* Set Test Parameters 2*/
        result = bcr.setParameter(barcodeData.OCRCheckDigit[testcase].paramNum2, barcodeData.OCRCheckDigit[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2\n");
        }

        /* Set Test Parameters 3*/
        result = bcr.setParameter(barcodeData.OCRCheckDigit[testcase].paramNum3, barcodeData.OCRCheckDigit[testcase].paramVal3);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 3\n");
        }
        doDecode();

    }

    /*
     * Method for OCR Subset Templates testing
     */
    public void OCRSubsetTemplatesTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 580;
        barcodeData.InitializeOCR();
        restoreDefaults();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Oreintation 180 degree */
        setOreintation_two();

        result = bcr.setParameter(barcodeData.OCRTemplate[testcase].paramNum2, barcodeData.OCRTemplate[testcase].data);
        if (result != BarCodeReader.BCR_SUCCESS) {
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
            Log.d("SDL_SCAN", "BCR_ERROR");
        }else {
            output.append("\nTest " + (testcase + 1) + " Set #" + barcodeData.OCRTemplate[testcase].paramNum2 + "to" + barcodeData.OCRTemplate[testcase].data + " Successfully\n");
        }

        /* Set Test Parameters */
        result = bcr.setParameter(barcodeData.OCRTemplate[testcase].paramNum, barcodeData.OCRTemplate[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter");
            Log.d("SDL_SCAN", "BCR_ERROR");
        }
        if (testcase == 11) {
            result = bcr.setParameter(691, 2);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set No Read\nTest Aborted");
                return;
            }
        }

        doDecode();
    }

    /*
     * Method for OCR Boundary testing
     */
    public void OCRBoundaryTests(int nextTest) {

        int result = 0;
        testcase = nextTest - 599;
        restoreDefaults();
        barcodeData.InitializeOCR();

        /* Set Decode Session Timeout to 5 seconds */
        setSessionTimeout();
        /* Set Transmit “No Read” Message */
        setNoReadEnable();
        /* Set Oreintation 180 degree */
        setOreintation_two();

        /* Set Test Parameters 1*/
        result = bcr.setParameter(barcodeData.OCRBoundary[testcase].paramNum, barcodeData.OCRBoundary[testcase].paramVal);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter\n");
        }
        /* Set Test Parameters 2*/
        result = bcr.setParameter(barcodeData.OCRBoundary[testcase].paramNum2, barcodeData.OCRBoundary[testcase].paramVal2);
        if (result != BarCodeReader.BCR_SUCCESS) {
            Log.d("SDL_SCAN", "BCR_ERROR");
            output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 2\n");
        }

        /* Set Test Parameters 3*/
        if (testcase == 4 || testcase == 5 || testcase == 6 || testcase == 7) {
            result = bcr.setParameter(barcodeData.OCRBoundary[testcase].paramNum3, barcodeData.OCRBoundary[testcase].paramVal3);
            if (result != BarCodeReader.BCR_SUCCESS) {
                Log.d("SDL_SCAN", "BCR_ERROR");
                output.append("\nTest " + (testcase + 1) + " ERROR: Could not set Parameter 3\n");
            }
        }

        doDecode();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            pass = data.getBooleanExtra("Pass", false);
            if ((pass && testcase == 1) || (!pass && testcase == 2)) {
                output.append("Test #" + testcase + ": PASS\n\n");

            } else {
                output.append("Test #" + testcase + ": FAIL\n\n");
                finalResult = false;
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            pass = data.getBooleanExtra("Pass", false);
            if (pass) {
                output.append("Test #" + testcase + ": PASS\n\n");
            } else {
                output.append("Test #" + testcase + ": FAIL\n\n");
                finalResult = false;
            }
        }
    }


    private void setupSE2100_TestPlatformForPreview() {
        String storeText;

        // Save the test results from the editText box in a bundle
        storeText = output.getText().toString();

        bundledStateData.putString("test_output", storeText);

        try {
            atMain = false;
            setContentView(R.layout.previewimage);
            // Create a tiny preview surface so that startPreview can work.
            // Newer android versions do not start camera preview unless a preview display is given
            // Added here for use with the SE2100 test device
            getWindow().setFormat(PixelFormat.UNKNOWN);
            surfaceView = findViewById(R.id.cameraPreviewSurface_2);
            //surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

            surfaceView.setOnClickListener(mImageClickListener);

            //bcr.startDecode(); //start decode (callback gets results)
        } catch (Exception e) {
            output.append("open excp:" + e);
        }
    }

    /**
     * Method to Start Decoding
     */
    public void doDecode() {
        if (setIdle() != STATE_IDLE)
            return;
        int result = bcr.setParameter(905, 1);
        Log.d(TAG, "GetLastDecodedImageTests : " + result);

        if (result == BarCodeReader.BCR_ERROR) {
            output.append("ERROR: Could not enable \"Get Last Decoded Image\". Aborting...\n");
            return;
        }
        state = STATE_DECODE;
        if (isSE2100)
            setupSE2100_TestPlatformForPreview();
        else
            bcr.startDecode();
    }

    /**
     * Method to doSnap
     */
    public void doSnap() {
        if (setIdle() != STATE_IDLE)
            return;

        state = STATE_SNAPSHOT;
        if (isSE2100)
            setupSE2100_TestPlatformForPreview();
        else
            bcr.takePicture(this);
    }

    /**
     * Method setIdle
     */
    private int setIdle() {
        int prevState = state;
        int ret = prevState;

        state = STATE_IDLE;
        switch (prevState) {
            case STATE_HANDSFREE:
                //resetTrigger();
            case STATE_DECODE:
                bcr.stopDecode();
                break;
            case STATE_VIDEO:
                bcr.stopPreview();
                break;
            case STATE_SNAPSHOT:
                ret = STATE_IDLE;
                break;
            default:
                ret = STATE_IDLE;
        }
        return ret;
    }

    /**
     * Method to Display Scan data
     *
     * @param sCount
     * @param sDat
     */
    private void dspData(String sCount, final String sDat) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Log.d("sDat", sDat);
                output.append(sDat + "\n");
                scan_data.setText(sDat + "\n");
            }
        });

    }

    /**
     * Method onDecodeComplete
     *
     * @param symbology the symbology of decoded bar code if any
     * @param length    if positive, indicates the length of the bar code data,
     *                  otherwise, DECODE_STATUS_TIMEOUT if the request timed out or
     *                  DECODE_STATUS_CANCELED if stopDecode() is called before a successful
     *                  decode or timeout.
     * @param data      the contents of the decoded bar code
     * @param reader    the BarCodeReader service object.
     */
    public void onDecodeComplete(int symbology, int length, byte[] data, BarCodeReader reader) {
        String error_message = null;
        String bcData = "";
        if (length > 0) {
            isEnabledBeep = true;
            if (isEnabledBeep)
                beep();
        }
        if (state == STATE_DECODE)
            state = STATE_IDLE;
        if (length > 0) {
            if (symbology == 0x69)    // signature capture
            {
            } else {
                if (symbology == 0x99)    //type 99?
                {
                    symbology = data[0];
                    int n = data[1];
                    int s = 2;
                    int d = 0;
                    int len = 0;
                    byte[] d99 = new byte[data.length];
                    for (int i = 0; i < n; ++i) {
                        s += 2;
                        len = data[s++];
                        System.arraycopy(data, s, d99, d, len);
                        s += len;
                        d += len;
                    }
                    d99[d] = 0;
                    data = d99;

                }
                try {
                    String sCounts = "[] type: " + symbology + " len: " + length + " ";
                    String sData = new String(data, 0, length);
                    dspData(sCounts, sData);

                    bcData = sData.trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else    // no-decode
        {

            switch (length) {
                case BarCodeReader.DECODE_STATUS_TIMEOUT:
                    error_message = "decode timed out";
                    break;

                case BarCodeReader.DECODE_STATUS_CANCELED:

                    error_message = "decode cancelled";
                    break;

                case BarCodeReader.DECODE_STATUS_MULTI_DEC_COUNT:
                    break;
                case BarCodeReader.DECODE_STATUS_ERROR:
                    error_message = "DECODE FAILED !";
                    break;
                default:
                    error_message = "decode failed";
                    break;
            }
        }
        final String finalError_message = error_message;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (finalError_message != null) {
                    output.append("Error " + finalError_message + "\n");
                    scan_data.setText(finalError_message + "\n");
                }
            }
        });

        if (bcData.equals("")) {
            return;
        }
        if (bcData.length() > 0) {
            Log.d("SDL_SCAN", bcData);
            Log.d("SDL_SCAN", "Scan_Complete");
            finalResult = true;
        }
    }


    @SuppressLint("NewApi")
    private void showSigCapture(byte[] data) {
        Intent i = new Intent(this, GetLastDecodedImageActivity.class);
        byte[] sigCapImageDataNoHdr = Arrays.copyOfRange(data, 6, data.length);
        i.putExtra("lastDecodedImage", sigCapImageDataNoHdr);
        try {
            startActivityForResult(i, 2);
        } catch (ActivityNotFoundException E) {
            Toast tst = new Toast(this);
            tst.setDuration(Toast.LENGTH_SHORT);
            tst.setText("Activity Not Found");
            tst.show();
        }
    }

    public void writeDecodedImage(String formatExtension) {
        byte[] data = bcr.getLastDecImage();
        ByteArrayBuffer bab = new ByteArrayBuffer(data.length);
        if (!(Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED))) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String str = Environment.getExternalStorageDirectory().getAbsolutePath();
                str += "/SWDecode/";
                File f = new File(str);
                if (!f.exists()) f.mkdir();
                str += "GetLastDecImage";
                str += formatExtension;
                File f2 = new File(str);
                try {
                    FileOutputStream fos = new FileOutputStream(f2);
                    bab.append(data, 0, data.length);
                    fos.write(bab.buffer());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    output.append("\nCannot save image because the File Output failed.\n");
                }
            } else {
                output.append("Cannot write Log because the External Storage is not aailable for writing");
            }
        } else {
            output.append("Cannot Write to Log file because there is no External Storage detected");
        }
    }

    /**
     * Class DecoderTimer
     */
    class DecodeTimer extends Thread {

        public void run() {
            while (true) {
                while (start) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    bcr.stopDecode();
                    start = false;
                }
            }
        }
    }

    /**
     * Method onEvent
     */
    public void onEvent(int event, int info, byte[] data, BarCodeReader reader) {

        if (event == BarCodeReader.BCRDR_EVENT_MOTION_DETECTED) {
            MotionEvent = true;
            if (bcr.getNumParameter(138) == BarCodeReader.ParamVal.AUTO_AIM && PrimTest == 3)
                bcr.startDecode();
        }


    }

    @Override
    public void onPictureTaken(int format, int width, int height, byte[] data,
                               BarCodeReader reader) {

        ImageEvent = true;

    }

    /**
     * Method set Beep on decode barcode
     */
    private void beep() {
        if (tg != null)
            tg.startTone(ToneGenerator.TONE_CDMA_NETWORK_CALLWAITING);
    }

    /**
     * Method restore Defaults
     */
    private void restoreDefaults() {
        if (bcr != null){
            bcr.setDefaultParameters();
        }
    }

    /*
     * If starting a handsfree session set to true
     */
    private void decode(boolean HandsFree) {
        if (!HandsFree) {
            bcr.startDecode();
            startDecodeTime = System.currentTimeMillis();
        } else {
            bcr.startHandsFreeDecode(BarCodeReader.ParamVal.HANDSFREE);
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        //TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            bcr.setPreviewDisplay(holder);
        } catch (IOException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (state == STATE_PREVIEW) {
            bcr.startViewFinder(this);
        } else if (state == STATE_DECODE) {
            if (isSE2100) {
                bcr.startDecode();
            }
        } else if (state == STATE_SNAPSHOT) {
            if (isSE2100) {
                bcr.startDecode();
            }
        } else if (state == STATE_HANDSFREE) {
            if (isSE2100) {
                bcr.startHandsFreeDecode(BarCodeReader.ParamVal.HANDSFREE);
            }
        } else if (state == STATE_SNAPSHOT) {
            if (isSE2100) {
                bcr.takePicture(this);
            }
        } else {
            //bcr.startVideoCapture(this);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //TODO Auto-generated method stub
    }

    public void onVideoFrame(int format, int width, int height, byte[] data,
                             BarCodeReader reader) {
        // display snapshot
        Bitmap bmSnap = BitmapFactory.decodeByteArray(data, 0, data.length);

        if (!videoCapDisplayStarted) {
            atMain = false;
            videoCapDisplayStarted = true;
            setContentView(R.layout.image);
            image = findViewById(R.id.snap_image);

            // This handles snapshot with viewfinder
            if (state == STATE_PREVIEW) {
                LayoutInflater controlInflater = LayoutInflater.from(getBaseContext());
                View viewControl = controlInflater.inflate(R.layout.control, null);
                ViewGroup.LayoutParams layoutParamsControl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                this.addContentView(viewControl, layoutParamsControl);
                findViewById(R.id.takepicture).setOnClickListener(mTakePicListener);
            } else {
                image.setOnClickListener(mImageClickListener);
            }
        }

        if (bmSnap != null)
            image.setImageBitmap(bmSnap);
    }
}
