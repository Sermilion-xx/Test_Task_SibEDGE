package com.sibedge.sibedge_test.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.Excluder;
import com.google.gson.reflect.TypeToken;
import com.sibedge.sibedge_test.Model.ListRow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by Sermilion on 05/10/2016.
 */

public class Utility {

    public static int GALLERY_INTENT_CALLED = 0;
    public static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static int GALLERY_KITKAT_INTENT_CALLED = 1;
    public static int MEDIA_TYPE_IMAGE = 2;
    public static String locale = "en";

    public enum ItemClick {LONG, SHORT, ADD_BUTTON}

    public static void saveItemsToPref(Context mContext, ArrayList<ListRow> mItems) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        Gson gson = new Gson();
        String json = gson.toJson(mItems);
        editor.putString("mItems", json);
        editor.apply();
    }

    public static void saveLangToPref(Context mContext, String lang) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        editor.putString("lang", lang);
        editor.apply();
    }

    public static String getLangToPref(Context mContext) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        return pref.getString("lang", "");
    }

    public static ArrayList<ListRow> getItemsFromPref(Context mContext) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String str = pref.getString("mItems", "");
        Type type = new TypeToken<ArrayList<ListRow>>() {
        }.getType();
        ArrayList<ListRow> restoreData = new Gson().fromJson(str, type);
        return restoreData != null ? restoreData : new ArrayList<ListRow>();
    }

    public static Uri getOutputMediaFileUri(int type, String fileName) {
        return Uri.fromFile(getOutputMediaFile(type, Environment.DIRECTORY_PICTURES, fileName));
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type, String directory, String fileName) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                directory), "SibEDGE");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("SibEFGE", "failed to create directory");
            }
        }
        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    fileName);
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


    public static void httpGetFile(@NonNull String url, @NonNull File destFile) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            long contentLength = body.contentLength();
            BufferedSource source = body.source();
            BufferedSink sink = Okio.buffer(Okio.sink(destFile));
            Buffer sinkBuffer = sink.buffer();
            long totalBytesRead = 0;
            long bufferSize = 8 * 1024L;
            long bytesRead;
            while (source.read(sinkBuffer, bufferSize) != -1L) {
                bytesRead = source.read(sinkBuffer, bufferSize);
                sink.emit();
                totalBytesRead += bytesRead;
                long progress = (totalBytesRead * 100 / contentLength);
//            publishProgress(progress);
            }
            sink.flush();
            sink.close();
            source.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }



}
