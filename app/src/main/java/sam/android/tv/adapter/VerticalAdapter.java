package sam.android.tv.adapter;

import android.content.Context;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sam.android.tv.R;
import sam.android.tv.library.CustomRecyclerView;
import sam.android.tv.model.RowObject;
import sam.android.tv.utility.Utils;

public class VerticalAdapter extends InputTrackingVerticalRecyclerViewAdapter<VerticalAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<RowObject> mRowObjects;

    public static class ViewHolder extends VerticalGridView.ViewHolder {
        public LinearLayout mContainer;
        public TextView mTitle;
        public CustomRecyclerView mHorizontalRecyclerView;
        public HorizontalAdapter mHorizontalAdapter;

        public ViewHolder(View v, Context mContext) {
            super(v);
            mContainer = (LinearLayout)v.findViewById(R.id.row_container);
            mTitle = (TextView)v.findViewById(R.id.tv_row_title);
            mHorizontalRecyclerView = (CustomRecyclerView)v.findViewById(R.id.hg_row);

            mHorizontalAdapter = new HorizontalAdapter(mContext, null);
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mHorizontalRecyclerView.setLayoutManager(mLayoutManager);
            mHorizontalRecyclerView.setAdapter(mHorizontalAdapter);
            mLayoutManager.scrollToPosition(mHorizontalAdapter.getItemCount() / 2);
            mHorizontalAdapter.setSelectedItem(mHorizontalAdapter.getItemCount() / 2);
//            mHorizontalRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                boolean isScrollLeft;
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                    int pos;
//                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                        if (isScrollLeft) {
//                            pos = mLayoutManager.findFirstVisibleItemPosition();
//                        } else {
//                            pos = mLayoutManager.findFirstVisibleItemPosition() + 1;
//                        }
//
//                        mLayoutManager.scrollToPositionWithOffset(pos, 0);
//                    }
//                }
//
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    isScrollLeft = dx < 0;
//                }
//            });

        }
    }

    public VerticalAdapter(Context mContext, ArrayList<RowObject> mRowObjects) {
        super(mContext);
        this.mContext = mContext;
        this.mRowObjects = mRowObjects;
    }

    @Override
    public VerticalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("onBindViewHolder", "Started");

        RowObject mRowObject = mRowObjects.get(position % 3);

        holder.mContainer.getLayoutParams().height = Utils.getDisplaySize(mContext).y * 17 / 48;

        holder.mTitle.setText(mRowObject.getTitle());
        holder.mHorizontalAdapter.addAll(mRowObject.getMovies());
        holder.mHorizontalAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
