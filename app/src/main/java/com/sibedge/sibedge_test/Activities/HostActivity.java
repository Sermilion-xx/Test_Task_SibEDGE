package com.sibedge.sibedge_test.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sibedge.sibedge_test.Adapters.ViewPagerAdapter;
import com.sibedge.sibedge_test.Fragments.FragmentFactory;
import com.sibedge.sibedge_test.Fragments.SibEDGE_ListFragment;
import com.sibedge.sibedge_test.Fragments.SibEDGE_MapFragment;
import com.sibedge.sibedge_test.Fragments.SibEDGE_ScalingFragment;
import com.sibedge.sibedge_test.Fragments.SibEDGE_ServiceFragment;
import com.sibedge.sibedge_test.Model.ListRow;
import com.sibedge.sibedge_test.R;
import com.sibedge.sibedge_test.Utility.Utility;

import java.util.ArrayList;

public class HostActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private EditText userInput;
    private SibEDGE_ListFragment listFragment;
    private AlertDialog newItemalertDialog;
    private int currentPage;
    private int menuResourse = R.menu.menu;

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        ViewPager mViewPager = find(R.id.host_activity_viewPages, ViewPager.class);
        TabLayout mTabLayout = find(R.id.host_activity_tabLayout, TabLayout.class);
        Toolbar mToolbar = find(R.id.host_activity_toolbar, Toolbar.class);

        mViewPager.addOnPageChangeListener(this);

        mToolbar.setTitle("SibEDGE");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        ArrayList<ListRow> mItems = Utility.getItemsFromPref(this);
        listFragment = FragmentFactory.getListFragment();
        listFragment.setHostActivity(this);
        listFragment.setmItems(mItems);

        SibEDGE_ScalingFragment scalingFragment = FragmentFactory.getScalingFragment();
        SibEDGE_ServiceFragment serviceFragment = FragmentFactory.getServiceFragment();
        SibEDGE_MapFragment mapFragment = FragmentFactory.getMapFragment();
        mapFragment.setmHostActivity(this);

        mAdapter.addFragment(listFragment, getResourceString(R.string.list));
        mAdapter.addFragment(scalingFragment, getResourceString(R.string.scaling));
        mAdapter.addFragment(serviceFragment, getResourceString(R.string.service));
        mAdapter.addFragment(mapFragment, getResourceString(R.string.map));

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);
    }

    public void showNewItemDialog(final int... position) {
        LayoutInflater li = LayoutInflater.from(HostActivity.this);
        View promptsView = li.inflate(R.layout.item_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                HostActivity.this);
        alertDialogBuilder.setView(promptsView);
        userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        if (position.length > 0) {
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
                                if (listFragment.getItemClickType() == Utility.ItemClick.SHORT) {
                                    listFragment.editRowItem(title, position[0]);
                                } else if (listFragment.getItemClickType() == Utility.ItemClick.LONG) {

                                } else if (listFragment.getItemClickType() == Utility.ItemClick.ADD_BUTTON) {
                                    listFragment.addRowItem(title);
                                }

                            }
                        })
                .setNegativeButton("Revert", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        newItemalertDialog = alertDialogBuilder.create();

        newItemalertDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;
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
                            }).setOnKeyListener(new Dialog.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface arg0, int keyCode,
                                             KeyEvent event) {
                            if (event.getAction() != KeyEvent.ACTION_DOWN)
                                return true;
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                arg0.dismiss();
                                newItemalertDialog.dismiss();
                            }
                            return false;
                        }
                    }).setNegativeButton("No", null).show();

                }
                return false;
            }
        });
        newItemalertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuResourse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            if (currentPage == 0) {
                listFragment.setItemClickType(Utility.ItemClick.ADD_BUTTON);
                showNewItemDialog();
            }
            return true;
        } else if (id == R.id.action_language) {
            Utility.changeLang(this, false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPage = position;
        if (position == 0) {
            invalidateOptionsMenu();
            menuResourse = R.menu.menu;
        } else if (position == 1) {
            invalidateOptionsMenu();
            menuResourse = R.menu.menu_alt;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
