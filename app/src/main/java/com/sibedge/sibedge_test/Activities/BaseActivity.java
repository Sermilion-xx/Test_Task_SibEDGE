package com.sibedge.sibedge_test.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sibedge.sibedge_test.R;

/**
 * Created by Sermilion on 05/10/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String INTENT_PARAM_PREFIX = "com.sibedge.sibedge_test";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected void addFragment(@IdRes Integer containerViewId,
                               @NonNull Fragment fragment ,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager().beginTransaction()
                .add(containerViewId, fragment, fragmentTag).disallowAddToBackStack()
                .commit();
    }

    protected void replaceFragment(@IdRes Integer containerViewId,
                                  @NonNull Fragment fragment,
                                  @NonNull String fragmentTag,
                                  @Nullable String backStackStateName) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName).commit();
    }

    protected String getResourceString(int id){
        return getResources().getString(id);
    }

    protected <T extends View> T find(int id, Class<T> type){
        return type.cast(findViewById(id));
    }
}
