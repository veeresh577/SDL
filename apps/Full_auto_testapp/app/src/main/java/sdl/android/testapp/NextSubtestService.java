package sdl.android.testapp;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

/**
 * Created by GFRP64 on 9/15/2017.
 */

public class NextSubtestService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @paramname Used to name the worker thread, important only for debugging.
     */
    private static final String TAG = "sdl.android.testapp";

    public NextSubtestService() {
        super("NextSubtestService");
    }


    protected void onHandleIntent(Intent intent) {
        //HashMap<>
        Bundle b1;
        String subtest_id;
        int testId;

        Log.i("NextSubtest service", "hit");
        if (intent != null) {
            b1 = intent.getExtras();
            subtest_id = b1.getString("subtest");
            testId = Integer.decode(subtest_id);
            Message m1 = new Message();
            m1.what = testId;

            SW_Decode_Android_TestApp_Activity.testHandler.handleMessage(m1);
        }
    }
}







