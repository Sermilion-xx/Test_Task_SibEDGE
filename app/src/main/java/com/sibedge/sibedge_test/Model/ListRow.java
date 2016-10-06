package com.sibedge.sibedge_test.Model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Sermilion on 05/10/2016.
 */
@Data
public class ListRow implements Parcelable {
    private String title;
    private boolean flagged;

    public ListRow(String aTitle){
        this.title = aTitle;
    }

    protected ListRow(Parcel in) {
        title = in.readString();
        flagged = in.readByte() != 0;
    }

    public static final Creator<ListRow> CREATOR = new Creator<ListRow>() {
        @Override
        public ListRow createFromParcel(Parcel in) {
            return new ListRow(in);
        }

        @Override
        public ListRow[] newArray(int size) {
            return new ListRow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeByte((byte) (flagged ? 1 : 0));
    }
}
