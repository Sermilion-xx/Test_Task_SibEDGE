package com.sibedge.sibedge_test.Model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Sermilion on 06/10/2016.
 */
@Data
public class XmlListRow implements Parcelable{
    private int id;
    private String date;
    private String text;

    public XmlListRow(int Id, String Date, String Text){
        this.id = Id;
        this.date = Date;
        this.text = Text;
    }

    protected XmlListRow(Parcel in) {
        id = in.readInt();
        date = in.readString();
        text = in.readString();
    }

    public static final Creator<XmlListRow> CREATOR = new Creator<XmlListRow>() {
        @Override
        public XmlListRow createFromParcel(Parcel in) {
            return new XmlListRow(in);
        }

        @Override
        public XmlListRow[] newArray(int size) {
            return new XmlListRow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(text);
    }
}
