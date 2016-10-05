package com.sibedge.sibedge_test.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Sermilion on 05/10/2016.
 */

public class Utility {
    public enum ItemClick {LONG, SHORT, ADD_BUTTON}

    public static void saveItemsToPref(Context mContext, ArrayList<ListRow> mItems) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        Gson gson = new Gson();
        String json = gson.toJson(mItems);
        editor.putString("mItems", json);
        editor.apply();
    }

    public static ArrayList<ListRow> getItemsFromPref(Context mContext){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        String str = pref.getString("mItems","");
        Type type = new TypeToken<ArrayList<ListRow>>() { }.getType();
        ArrayList<ListRow> restoreData = new Gson().fromJson(str, type);
        return restoreData!=null?restoreData: new ArrayList<ListRow>();
    }
}
