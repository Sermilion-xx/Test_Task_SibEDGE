package com.sibedge.sibedge_test.Utility;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Sermilion on 06/10/2016.
 */

public class GetXMLTask extends AsyncTask<Object, Void, Void> {
    public AsyncResponse delegate = null;
    private File xmlFile;
    private XmlPullParser xpp;

    public void setXpp(XmlPullParser xpp) {
        this.xpp = xpp;
    }

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
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            xpp.setInput(new StringReader(Utility.getStringFromFile(xmlFile)));
            delegate.processFinish(xpp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
