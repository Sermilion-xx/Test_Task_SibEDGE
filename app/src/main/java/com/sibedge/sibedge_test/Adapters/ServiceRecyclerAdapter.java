package com.sibedge.sibedge_test.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibedge.sibedge_test.Model.XmlListRow;
import com.sibedge.sibedge_test.R;

import java.util.ArrayList;

/**
 * Created by Sermilion on 06/10/2016.
 */

public class ServiceRecyclerAdapter extends RecyclerView.Adapter<ServiceRecyclerAdapter.ViewHolder> {

    private ArrayList<XmlListRow> mItems;

    public ServiceRecyclerAdapter(ArrayList<XmlListRow> items) {
        this.mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_service, parent, false);
        return new ServiceRecyclerAdapter.ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        XmlListRow row = mItems.get(position);
        holder.bindTitle(row);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private TextView mId;
        private TextView mDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.service_textView);
            mId       = (TextView) itemView.findViewById(R.id.service_id);
            mDate     = (TextView) itemView.findViewById(R.id.service_date);
        }

        public void bindTitle(XmlListRow row) {
            mTextView.setText(row.getText());
            mId.setText(String.valueOf(row.getId()));
            mDate.setText(row.getDate());
        }
    }

}
