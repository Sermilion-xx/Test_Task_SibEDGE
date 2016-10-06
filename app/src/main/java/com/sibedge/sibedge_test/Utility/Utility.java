package com.sibedge.sibedge_test.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sibedge.sibedge_test.Model.ListRow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by Sermilion on 05/10/2016.
 */

public class Utility {

    public static int GALLERY_INTENT_CALLED = 0;
    public static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static int GALLERY_KITKAT_INTENT_CALLED = 1;
    public static int MEDIA_TYPE_IMAGE = 2;

    public enum ItemClick {LONG, SHORT, ADD_BUTTON}

    public static void saveItemsToPref(Context mContext, ArrayList<ListRow> mItems) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        Gson gson = new Gson();
        String json = gson.toJson(mItems);
        editor.putString("mItems", json);
        editor.apply();
    }

    public static ArrayList<ListRow> getItemsFromPref(Context mContext) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String str = pref.getString("mItems", "");
        Type type = new TypeToken<ArrayList<ListRow>>() {
        }.getType();
        ArrayList<ListRow> restoreData = new Gson().fromJson(str, type);
        return restoreData != null ? restoreData : new ArrayList<ListRow>();
    }

    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SibEDGE");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("SibEFGE", "failed to create directory");
            }
        }
        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "scale_picture");
        } else {
            return null;
        }
        return mediaFile;
    }

    public static Bitmap getThumbnail(Uri uri, Activity activity) throws IOException {
        int THUMBNAIL_SIZE = 640;
        InputStream input = activity.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if (onlyBoundsOptions.outWidth == -1 || onlyBoundsOptions.outHeight == -1)
            return null;
        int originalSize;
        if (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth)
            originalSize = onlyBoundsOptions.outHeight;
        else
            originalSize = onlyBoundsOptions.outWidth;

        Double ratio;
        if (originalSize > THUMBNAIL_SIZE)
            ratio = (double) originalSize / THUMBNAIL_SIZE;
        else
            ratio = 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        input = activity.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        if (input != null) {
            input.close();
        }
        return bitmap;
    }

    public static int getPowerOfTwoForSampleRatio(Double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0)
            return 1;
        else
            return k;
    }
}
