package com.sibedge.sibedge_test.Fragments;

import android.support.v4.app.Fragment;

import org.jetbrains.annotations.Contract;

/**
 * Created by Sermilion on 05/10/2016.
 */

public class FragmentFactory {
    @Contract(" -> !null")
    public static SibEDGE_ListFragment getListFragment() {
        return new SibEDGE_ListFragment();
    }

    @Contract(" -> !null")
    public static SibEDGE_ScalingFragment getScalingFragment() {
        return new SibEDGE_ScalingFragment();
    }

    @Contract(" -> !null")
    public static SibEDGE_ServiceFragment getServiceFragment() {
        return new SibEDGE_ServiceFragment();
    }

    @Contract(" -> !null")
    public static SibEDGE_MapFragment getMapFragment() {
        return new SibEDGE_MapFragment();
    }
}
