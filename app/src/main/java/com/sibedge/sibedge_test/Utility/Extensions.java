package com.sibedge.sibedge_test.Utility;

import android.view.View;

/**
 * Created by Sermilion on 05/10/2016.
 */

public class Extensions {

    public static <T extends View> T find(View view, int id) {
        return (T) view.findViewById(id);

    }
}
