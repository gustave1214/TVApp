package sam.android.tv.model;

import java.util.ArrayList;

public class RowObject {
    private String title;
    private ArrayList<Movie> movies;

    public RowObject() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
