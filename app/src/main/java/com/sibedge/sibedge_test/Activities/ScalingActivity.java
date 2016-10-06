package com.sibedge.sibedge_test.Activities;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.FloatMath;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sibedge.sibedge_test.R;
import com.sibedge.sibedge_test.Utility.TouchImageView;
import com.sibedge.sibedge_test.Utility.Utility;

import java.io.IOException;

/**
 * Created by Sermilion on 06/10/2016.
 */

public class ScalingActivity extends BaseActivity{

    private TouchImageView scalingImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaling);
        Toolbar mToolbar = find(R.id.scaling_activity_toolbar);
        mToolbar.setTitle("Scaling Activity");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scalingImage = (TouchImageView) findViewById(R.id.scaling_image);
        Button zoomInButton = find(R.id.button_zoom_in);
        Button zoomOutButton = find(R.id.button_zoom_out);

        Uri fileUri = getIntent().getParcelableExtra("fileUri");
        try {
            Bitmap bitmap = Utility.getThumbnail(fileUri, this);
            scalingImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        zoomInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MotionEvent event = MotionEvent.obtain(1000, 1000, MotionEvent.ACTION_POINTER_DOWN, 200,200,0);
//                event.setLocation(0,0);
//                oldDist = spacing(event);
//                Log.d(TAG, "oldDist=" + oldDist);
//                if (oldDist > 10f) {
//                    savedMatrix.set(matrix);
//                    midPoint(mid, event);
//                    mode = ZOOM;
//                    Log.d(TAG, "mode=ZOOM");
//                }
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
