package com.sibedge.sibedge_test.Activities;

/**
 * Created by Sermilion on 07/10/2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.sibedge.sibedge_test.R;
import com.sibedge.sibedge_test.Utility.Utility;

public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onResume() {
        super.onResume();
        Utility.locale = Utility.getLangToPref(this);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Utility.changeLang(Splash.this, true);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
