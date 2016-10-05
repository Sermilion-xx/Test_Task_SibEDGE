package com.sibedge.sibedge_test.Model;

import lombok.Data;

/**
 * Created by Sermilion on 05/10/2016.
 */
@Data
public class ListRow {
    private String title;
    private boolean flagged;

    public ListRow(String aTitle){
        this.title = aTitle;
    }
}
