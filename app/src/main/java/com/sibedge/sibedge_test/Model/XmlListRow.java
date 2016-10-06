package com.sibedge.sibedge_test.Model;

import lombok.Data;

/**
 * Created by Sermilion on 06/10/2016.
 */
@Data
public class XmlListRow {
    private int id;
    private String date;
    private String text;

    public XmlListRow(int Id, String Date, String Text){
        this.id = Id;
        this.date = Date;
        this.text = Text;
    }
}
