import android.app.Activity;
import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.greenriver.pcrepair.app.R;

import java.io.FileOutputStream;

/*
 * Created by jimtryon on 5/22/14.
 */
public class Signature extends Activity {
    private GestureOverlayView gv;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            gv = (GestureOverlayView) findViewById(R.id.signature);
            gv.setDrawingCacheEnabled(true);
        }

    private boolean isClicked = true;

    public void saveSig(View v) {

        try {
            Bitmap bm = Bitmap.createBitmap(gv.getDrawingCache());
            gv.setDrawingCacheEnabled(false);
            FileOutputStream fos = openFileOutput("signature.png", Context.MODE_PRIVATE);
            //compress to specified format (PNG), quality - which is ignored for PNG, and out stream
            //bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.v("Gestures", e.getMessage());
            e.printStackTrace();
        }
        isClicked = true;
    }

        public void clearSig(View v) {

            if (!isClicked) {

                gv.cancelClearAnimation();
                gv.clear(true);
                gv.invalidate();
            }

        }
}
