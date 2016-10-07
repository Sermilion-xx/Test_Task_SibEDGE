package com.sibedge.sibedge_test.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sibedge.sibedge_test.Activities.HostActivity;
import com.sibedge.sibedge_test.Adapters.ListRecyclerAdapter;
import com.sibedge.sibedge_test.Utility.ClickListener;
import com.sibedge.sibedge_test.Model.ListRow;
import com.sibedge.sibedge_test.Utility.Utility;
import com.sibedge.sibedge_test.R;

import java.util.ArrayList;

/**
 * Created by Sermilion on 05/10/2016.
 */

public class SibEDGE_ListFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ListRecyclerAdapter mAdapter;
    private ArrayList<ListRow> mItems;
    private Utility.ItemClick itemClickType;
    private HostActivity hostActivity;
    private AlertDialog optionsDialog = null;

    public Utility.ItemClick getItemClickType() {
        return itemClickType;
    }

    public void setItemClickType(Utility.ItemClick itemClickType) {
        this.itemClickType = itemClickType;
    }

    public void setHostActivity(HostActivity hostActivity) {
        this.hostActivity = hostActivity;
    }

    public void setmItems(ArrayList<ListRow> mItems) {
        this.mItems = mItems;
    }

    public ArrayList<ListRow> getmItems() {
        return mItems;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setRetainInstance(true);
        outState.putParcelableArrayList("mItems", mItems);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            mItems = savedInstanceState.getParcelableArrayList("mItems");
        }
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new ListRecyclerAdapter(mItems, getContext(), this);
        mAdapter.setOnItemClickListener(new ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                itemClickType = Utility.ItemClick.SHORT;
                hostActivity.showNewItemDialog(position);
            }

            @Override
            public void onItemLongClick(final int pos, final View v) {
                Log.d("Click", "onItemLongClick pos = " + pos);
                itemClickType = Utility.ItemClick.LONG;
                String names[] = {"Edit", "Delete"};

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater(null);
                View convertView = inflater.inflate(R.layout.option_dialog, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Choose action");
                final ListView lv = (ListView) convertView.findViewById(R.id.optionDialog);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {
                                    itemClickType = Utility.ItemClick.SHORT;
                                    hostActivity.showNewItemDialog(pos);
                                    optionsDialog.dismiss();
                                } else {
                                    deleteRow(pos);
                                    optionsDialog.dismiss();
                                }
                            }
                        }
                );
                optionsDialog = alertDialog.create();
                optionsDialog.show();
                Log.d("Click", "onItemClick position: " + pos);
            }
        });
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    public void addRowItem(String title) {
        mItems.add(new ListRow(title));
        mAdapter.notifyDataSetChanged();
        saveItemsToPref();
    }

    public void editRowItem(String title, int position) {
        mItems.get(position).setTitle(title);
        mAdapter.notifyDataSetChanged();
        saveItemsToPref();
    }

    public void deleteRow(int position) {
        mItems.remove(position);
        mAdapter.notifyDataSetChanged();
        saveItemsToPref();
    }

    public void saveItemsToPref(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                Utility.saveItemsToPref(getContext(), mItems);
                return null;
            }
        }.execute();
    }

}
