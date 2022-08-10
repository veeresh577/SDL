package sdl.android.testapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

import com.zebra.adc.decoder.BarCodeReader;

import java.io.IOException;


/**
 * Created by HRFT46 on 11/2/2016.
 */

public class PreviewWindowActivity extends Activity implements BarCodeReader.DecodeCallback, BarCodeReader.PreviewCallback, BarCodeReader.VideoCallback, SurfaceHolder.Callback {

    private static BarCodeReader bcr = null;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.previewimage);

            getWindow().setFormat(PixelFormat.UNKNOWN);
            surfaceView = findViewById(R.id.cameraPreviewSurface_2);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

            surfaceView.setOnClickListener(mImageClickListener);
        } catch (Exception e) {
            //Do Nothing for now
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            bcr.setDecodeCallback(this);
        } catch (Exception e) {

        }

        bcr.startViewFinder(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            bcr.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bcr.startDecode();
    }

    @Override
    public void onEvent(int event, int info, byte[] data, BarCodeReader reader) {
        // TODO Auto-generated method stub

    }

    public void onDecodeComplete(int symbology, int length, byte[] data,
                                 BarCodeReader reader) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //TODO Auto-generated method stub
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        //TODO Auto-generated method stub
    }

    OnClickListener mImageClickListener = new OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };

    public void onPreviewFrame(byte[] data, BarCodeReader reader) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onVideoFrame(int format, int width, int height, byte[] data,
                             BarCodeReader reader) {
        Bitmap bmSnap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //   iv.setImageBitmap(bmSnap);
    }

}
