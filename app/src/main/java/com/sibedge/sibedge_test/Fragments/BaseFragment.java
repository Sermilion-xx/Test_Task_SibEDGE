package com.sibedge.sibedge_test.Fragments;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Sermilion on 05/10/2016.
 */

public class BaseFragment extends Fragment{

    protected String getResourceString(int id){
        return getResources().getString(id);
    }

    protected <T extends View> T find(int id){
        return (T) getActivity().findViewById(id);
    }

}
