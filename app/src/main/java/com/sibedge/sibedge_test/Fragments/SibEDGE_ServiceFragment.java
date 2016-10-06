package com.sibedge.sibedge_test.Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sibedge.sibedge_test.Adapters.ServiceRecyclerAdapter;
import com.sibedge.sibedge_test.R;
import com.sibedge.sibedge_test.Utility.AsyncResponse;
import com.sibedge.sibedge_test.Utility.GetXMLTask;
import com.sibedge.sibedge_test.Utility.Utility;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Sermilion on 05/10/2016.
 */

public class SibEDGE_ServiceFragment extends Fragment implements AsyncResponse {

    final String LOG_TAG = "XML_Log";
    private ProgressBar mprogressBar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ServiceRecyclerAdapter mAdapter;
    private ArrayList<String> mItems;


    @Override
    public void processFinish(Object output) {
        processXml((XmlPullParser) output);
    }

    private void processXml(XmlPullParser xpp) {
        try {
            String tmp;
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                                + ", depth = " + xpp.getDepth() + ", attrCount = "
                                + xpp.getAttributeCount());
                        tmp = "";
                        for (int i = 0; i < xpp.getAttributeCount(); i++) {
                            tmp = tmp + xpp.getAttributeName(i) + " = "
                                    + xpp.getAttributeValue(i) + ", ";
                        }
                        if (!TextUtils.isEmpty(tmp))
                            Log.d(LOG_TAG, "Attributes: " + tmp);
                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + xpp.getText());
                        break;

                    default:
                        break;
                }
                // следующий элемент
                xpp.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            final XmlPullParser xpp = factory.newPullParser();
            GetXMLTask asyncTask = new GetXMLTask();
            asyncTask.setDelegate(this);
            asyncTask.setXpp(xpp);
            final File xmlFile = Utility.getOutputMediaFile(Utility.MEDIA_TYPE_IMAGE, Environment.DIRECTORY_DOCUMENTS, "sibedge.xml");
            asyncTask.execute("http://storage.space-o.ru/testXmlFeed.xml", xmlFile);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.service_recyclerView);
        mprogressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mprogressBar.setVisibility(View.VISIBLE);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        ArrayList<String> mItems = new ArrayList<>();
        mItems.add("1");
        mItems.add("2");
        mAdapter = new ServiceRecyclerAdapter(mItems);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

}
