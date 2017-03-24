package sam.android.tv.adapter;

import android.content.Context;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import sam.android.tv.R;
import sam.android.tv.model.Movie;
import sam.android.tv.utility.Utils;

public class HorizontalAdapter extends InputTrackingHorizontalRecyclerViewAdapter<HorizontalAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Movie> mMovies;

    public static class ViewHolder extends HorizontalGridView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView)v.findViewById(R.id.iv_movie);

        }
    }

    public HorizontalAdapter(Context mContext, ArrayList<Movie> mMovies) {
        super(mContext);
        this.mContext = mContext;
        this.mMovies = mMovies;
    }

    @Override
    public HorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie mMovie = mMovies.get(position % 10);

        holder.imageView.setImageResource(mMovie.getImageResId());
        Log.d("SHI", "OK");

        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.height = (int) (Utils.getDisplaySize(mContext).y * 17 / 48);
        params.width = (int) (params.height / 1.6);
        holder.imageView.setLayoutParams(params);
//        holder.imageView.getLayoutParams().width = (int) (holder.imageView.getLayoutParams().height / 1.4);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public void addAll(ArrayList<Movie> mMovies) {
        this.mMovies = mMovies;
    }
}
