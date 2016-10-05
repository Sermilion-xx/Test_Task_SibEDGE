package com.sibedge.sibedge_test.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibedge.sibedge_test.Fragments.SibEDGE_ListFragment;
import com.sibedge.sibedge_test.Model.ClickListener;
import com.sibedge.sibedge_test.Model.ListRow;
import com.sibedge.sibedge_test.Model.Utility;
import com.sibedge.sibedge_test.R;

import java.util.ArrayList;


/**
 * Created by Sermilion on 05/10/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private static int TYPE_0 = 0;
    private static int TYPE_1 = 1;
    private ArrayList<ListRow> mItems;
    private Context mContext;
    private SibEDGE_ListFragment mListFragment;

    public RecyclerAdapter(ArrayList<ListRow> photos, Context context, SibEDGE_ListFragment listFragment) {
        mItems = photos;
        mContext = context;
        mListFragment = listFragment;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).isFlagged() ? TYPE_0 : TYPE_1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_list, parent, false);
        return new ViewHolder(inflatedView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListRow row = mItems.get(position);
        holder.bindTitle(row, mContext);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView mImageView;
        private TextView mTextView;
        private ImageButton mImageButton;
        private RecyclerAdapter mRecyclerAdapter;

        public ViewHolder(View view, RecyclerAdapter recyclerAdapter) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.list_imageView);
            mTextView = (TextView) view.findViewById(R.id.list_textView);
            mImageButton = (ImageButton) view.findViewById(R.id.list_button);
            mRecyclerAdapter = recyclerAdapter;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ListRow row = mRecyclerAdapter.mItems.get(position);
                    if (row.isFlagged()) {
                        mRecyclerAdapter.mItems.get(position).setFlagged(false);
                    } else {
                        mRecyclerAdapter.mItems.get(position).setFlagged(true);
                    }
                    mRecyclerAdapter.notifyItemChanged(position);
                }
            });
        }

        public void bindTitle(ListRow row, Context context) {
            mTextView.setText(row.getTitle());
            int icon_drawable_id;
            int flag_drawable_id;
            if (getItemViewType() == 0) {
                icon_drawable_id = R.drawable.ic_imac;
                flag_drawable_id = R.drawable.ic_flag_inactive;
            } else {
                icon_drawable_id = R.drawable.ic_tv;
                flag_drawable_id = R.drawable.ic_flag_active;
            }
            mImageView.setImageDrawable(ContextCompat.getDrawable(context, icon_drawable_id));
            mImageButton.setImageDrawable(ContextCompat.getDrawable(context, flag_drawable_id));
        }

        @Override
        public boolean onLongClick(View v) {
            mListFragment.setItemClickType(Utility.ItemClick.LONG);
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }

        @Override
        public void onClick(View v) {
            mListFragment.setItemClickType(Utility.ItemClick.SHORT);
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerAdapter.clickListener = clickListener;
    }
}
