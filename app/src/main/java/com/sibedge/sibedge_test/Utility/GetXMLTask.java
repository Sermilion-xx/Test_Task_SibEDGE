package com.sibedge.sibedge_test.Utility;

import android.os.AsyncTask;

import java.io.File;

/**
 * Created by Sermilion on 06/10/2016.
 */

public class GetXMLTask extends AsyncTask<Object, Void, Void> {
    public AsyncResponse delegate = null;
    private File xmlFile;

    public void setDelegate(AsyncResponse delegate) {
        this.delegate = delegate;
    }


    @Override
    protected Void doInBackground(Object... params) {
        xmlFile = (File) params[1];
        Utility.httpGetFile((String)params[0], xmlFile);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            delegate.processFinish(Utility.getStringFromFile(xmlFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
