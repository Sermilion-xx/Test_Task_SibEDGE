package com.sibedge.sibedge_test.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Sermilion on 05/10/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected String getResourceString(int id){
        return getResources().getString(id);
    }

    protected <T extends View> T find(int id, Class<T> type){
        return type.cast(findViewById(id));
    }
}
