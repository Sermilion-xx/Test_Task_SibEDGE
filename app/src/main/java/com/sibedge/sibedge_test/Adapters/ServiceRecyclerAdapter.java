package com.sibedge.sibedge_test.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibedge.sibedge_test.Model.ListRow;
import com.sibedge.sibedge_test.R;

import java.util.ArrayList;

/**
 * Created by Sermilion on 06/10/2016.
 */

public class ServiceRecyclerAdapter extends RecyclerView.Adapter<ServiceRecyclerAdapter.ViewHolder> {

    private ArrayList<String> mItems;

    public ServiceRecyclerAdapter(ArrayList<String> items) {
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
        String row = mItems.get(position);
        holder.bindTitle(row);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.service_textView);
        }

        public void bindTitle(String row) {
            mTextView.setText(row);
        }
    }

}
