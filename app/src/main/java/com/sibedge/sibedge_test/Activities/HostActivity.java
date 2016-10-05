package com.sibedge.sibedge_test.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sibedge.sibedge_test.Adapters.ViewPagerAdapter;
import com.sibedge.sibedge_test.Fragments.FragmentFactory;
import com.sibedge.sibedge_test.Fragments.SibEDGE_ListFragment;
import com.sibedge.sibedge_test.Fragments.SibEDGE_MapFragment;
import com.sibedge.sibedge_test.Fragments.SibEDGE_ScalingFragment;
import com.sibedge.sibedge_test.Fragments.SibEDGE_ServiceFragment;
import com.sibedge.sibedge_test.Model.ListRow;
import com.sibedge.sibedge_test.Model.Utility;
import com.sibedge.sibedge_test.R;

import java.util.ArrayList;

public class HostActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private Button mAddButton;
    private EditText userInput;
    private SibEDGE_ListFragment listFragment;
    private AlertDialog newItemalertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        mViewPager = find(R.id.host_activity_viewPages);
        mTabLayout = find(R.id.host_activity_tabLayout);
        mToolbar = find(R.id.host_activity_toolbar);
        mAddButton = find(R.id.list_add_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFragment.setItemClickType(Utility.ItemClick.ADD_BUTTON);
                showNewItemDialog();
            }
        });
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        if (mToolbar != null) {
            mToolbar.setTitle("SibEDGE");
        }
        ArrayList<ListRow> mItems = Utility.getItemsFromPref(this);
        listFragment = FragmentFactory.getListFragment();
        listFragment.setHostActivity(this);
        listFragment.setmItems(mItems);

        SibEDGE_ScalingFragment scalingFragment = FragmentFactory.getScalingFragment();
        SibEDGE_ServiceFragment serviceFragment = FragmentFactory.getServiceFragment();
        SibEDGE_MapFragment mapFragment = FragmentFactory.getMapFragment();

        mAdapter.addFragment(listFragment, getResourceString(R.string.list));
        mAdapter.addFragment(scalingFragment, getResourceString(R.string.scaling));
        mAdapter.addFragment(serviceFragment, getResourceString(R.string.service));
        mAdapter.addFragment(mapFragment, getResourceString(R.string.map));

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);
    }

    public void showNewItemDialog(final int...position) {
        LayoutInflater li = LayoutInflater.from(HostActivity.this);
        View promptsView = li.inflate(R.layout.item_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                HostActivity.this);
        alertDialogBuilder.setView(promptsView);
        userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        if(position.length>0){
            userInput.setText(listFragment.getmItems().get(position[0]).getTitle());
            userInput.setSelection(userInput.length());
            userInput.requestFocus();
        }
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String title = userInput.getText().toString();
                                if(listFragment.getItemClickType() == Utility.ItemClick.SHORT){
                                    listFragment.editRowItem(title, position[0]);
                                }else if(listFragment.getItemClickType() == Utility.ItemClick.LONG){

                                }else if(listFragment.getItemClickType() == Utility.ItemClick.ADD_BUTTON){
                                    listFragment.addRowItem(title);
                                }

                            }
                        })
                .setNegativeButton("Revert",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        newItemalertDialog = alertDialogBuilder.create();

        newItemalertDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    new AlertDialog.Builder(HostActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Add Item")
                            .setMessage("Are you sure you want to finish?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    newItemalertDialog.dismiss();
                                }
                            }).setNegativeButton("No", null).show();
                }
                return false;
            }
        });
        newItemalertDialog.show();
    }

}
