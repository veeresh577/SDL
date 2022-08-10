package sdl.android.testapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;


public class GetLastDecodedImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_last_decoded_image);

        Intent i = getIntent();
        ImageView lastImage;
        Bitmap image;

        lastImage = findViewById(R.id.snap_image);

        if (getIntent().hasExtra("lastDecodedImage")) {
            byte[] data = i.getByteArrayExtra("lastDecodedImage");
            image = BitmapFactory.decodeByteArray(data, 0, data.length);
            //image = i.getParcelableExtra("lastDecodedImage");
            SaveImage(image);

            if (image != null) {
                lastImage.setImageBitmap(image);
            } else {
                lastImage.setImageResource(R.drawable.ic_launcher);
            }
            this.finish();
        }
    }

    private static void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/SDL_LastImage");
        myDir.mkdirs();

        String fname = "Image_SDC" + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.get_last_decoded_image, menu);
        return true;
    }
}
