package sam.android.tv.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import sam.android.tv.R;
import sam.android.tv.adapter.VerticalAdapter;
import sam.android.tv.library.CustomRecyclerView;
import sam.android.tv.model.Movie;
import sam.android.tv.model.RowObject;

public class MainActivity extends Activity {
    private CustomRecyclerView mVerticalRecylerView;
    private ArrayList<RowObject> mRowObjects = new ArrayList<>();
    private VerticalAdapter mVerticalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "Started");

        mVerticalRecylerView = (CustomRecyclerView) findViewById(R.id.vg_main);

        loadRows();

        init();
    }

    private void init() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mVerticalRecylerView.setLayoutManager(layoutManager);
        layoutManager.scrollToPosition(mVerticalAdapter.getItemCount() / 2);
        mVerticalRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isScrollDown;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int pos;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isScrollDown) {
                        pos = layoutManager.findFirstVisibleItemPosition();
                    } else {
                        pos = layoutManager.findFirstVisibleItemPosition() + 1;
                    }

                    layoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                isScrollDown = dy < 0;
            }
        });
    }

    private void loadRows() {
        RowObject mRowObject1 = new RowObject();
        mRowObject1.setTitle("Row_" + 1);
        ArrayList<Movie> movies1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Movie movie = new Movie();
            movie.setImageResId(getIdFromName("movie_" + i));
            movies1.add(movie);
        }
        mRowObject1.setMovies(movies1);

        RowObject mRowObject2 = new RowObject();
        mRowObject2.setTitle("Row_" + 2);
        ArrayList<Movie> movies2 = new ArrayList<>();
        for (int i = 3; i < 13; i++) {
            Movie movie = new Movie();
            movie.setImageResId(getIdFromName("movie_" + i % 10));
            movies2.add(movie);
        }
        mRowObject2.setMovies(movies2);

        RowObject mRowObject3 = new RowObject();
        mRowObject3.setTitle("Row_" + 3);
        ArrayList<Movie> movies3 = new ArrayList<>();
        for (int i = 6; i < 16; i++) {
            Movie movie = new Movie();
            movie.setImageResId(getIdFromName("movie_" + i % 10));
            movies3.add(movie);
        }
        mRowObject3.setMovies(movies3);

        mRowObjects.add(mRowObject1);
        mRowObjects.add(mRowObject2);
        mRowObjects.add(mRowObject3);

        mVerticalAdapter = new VerticalAdapter(this, mRowObjects);
        mVerticalRecylerView.setAdapter(mVerticalAdapter);
    }

    private int getIdFromName(String resName) {
        return getResources().getIdentifier(resName, "drawable", getPackageName());
    }
}
