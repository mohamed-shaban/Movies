package com.example.mohamed.movies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class DetailActivity extends ActionBarActivity {
    MovieDbHelper mhelper = new MovieDbHelper(this);
    Movie movie;
    public static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, new DetailFragment())
                    .commit();
        }
    }

    public void onStart() {
        super.onStart();
        isFavorite();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public void isFavorite() {
        ImageButton fav = (ImageButton) findViewById(R.id.fav);
        SQLiteDatabase db = mhelper.getReadableDatabase();
        Intent intent = this.getIntent();
            movie = (Movie) intent.getParcelableExtra("Movie");
        Cursor cur = db.query(MovieContract.MovieEntry.TABLE_NAME,
                new String[] {MovieContract.MovieEntry.title},
                "mid=?",
                new String[] { String.valueOf(movie.getId()) },
                null,
                null,
                null);
        if (cur.moveToFirst())
        {
            fav.setImageResource(R.drawable.favorite2);
            fav.setTag(1);
        }
        else
        {
            fav.setImageResource(R.drawable.favorite1);
            fav.setTag(0);
        }
    }
    public void Favorite(View view) {
        ImageButton fav = (ImageButton) view.findViewById(R.id.fav);
        if (fav.getTag() == 0)
            addFav(view);
        else
            removeFav(view);
    }
    public void addFav(View view) {
        ImageButton fav = (ImageButton) view.findViewById(R.id.fav);
        fav.setTag(1);
        SQLiteDatabase db = mhelper.getReadableDatabase();
        fav.setImageResource(R.drawable.favorite2);
        ContentValues c = new ContentValues();
        c.put(MovieContract.MovieEntry.title, movie.getTitle());
        c.put(MovieContract.MovieEntry.vote_average, movie.getVoteAverage());
        c.put(MovieContract.MovieEntry.mid, movie.getId());
        c.put(MovieContract.MovieEntry.overview, movie.getOverview());
        c.put(MovieContract.MovieEntry.poster_path, movie.getImage());
        c.put(MovieContract.MovieEntry.release_date, movie.getReleaseDate());
        db.insert(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                c);
    }
   public void removeFav(View view)
   {
       ImageButton fav = (ImageButton) view.findViewById(R.id.fav);
       fav.setTag(0);
       SQLiteDatabase db = mhelper.getReadableDatabase();
       fav.setImageResource(R.drawable.favorite1);
       db.delete(MovieContract.MovieEntry.TABLE_NAME,
               MovieContract.MovieEntry.mid + " = ?",
               new String[] { String.valueOf(movie.getId()) });
    }
}