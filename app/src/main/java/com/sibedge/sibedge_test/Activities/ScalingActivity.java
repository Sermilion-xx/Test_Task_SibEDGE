package com.sibedge.sibedge_test.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.sibedge.sibedge_test.Utility.TouchImageView;
import com.sibedge.sibedge_test.R;
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
        Toolbar mToolbar = find(R.id.scaling_activity_toolbar, Toolbar.class);
        mToolbar.setTitle(getResourceString(R.string.scaling_activity));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scalingImage = (TouchImageView) findViewById(R.id.scaling_image);

        scalingImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    scalingImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    scalingImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                Drawable drawable = scalingImage.getDrawable();
                Rect rectDrawable = drawable.getBounds();
                float leftOffset = (scalingImage.getMeasuredWidth() - rectDrawable.width()) / 2f;
                float topOffset = (scalingImage.getMeasuredHeight() - rectDrawable.height()) / 2f;

                Matrix matrix = scalingImage.getImageMatrix();
                matrix.postTranslate(leftOffset, topOffset);
                scalingImage.setImageMatrix(matrix);
                scalingImage.invalidate();
            }
        });
        Button zoomInButton = find(R.id.button_zoom_in, Button.class);
        Button zoomOutButton = find(R.id.button_zoom_out, Button.class);

        Uri fileUri = getIntent().getParcelableExtra("fileUri");
        try {
            Bitmap bitmap = Utility.getThumbnail(fileUri, this);
            scalingImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        zoomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scalingImage.zoomIn();
            }
        });

        zoomOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scalingImage.zoomOut();
            }
        });
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
