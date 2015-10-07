package com.example.mohamed.movies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
public class MainActivity extends ActionBarActivity implements MovieFragment.Callback {
    //Now we will be heading towards hooking the adapter to GridView and make it functional
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    MovieDbHelper mhelper = new MovieDbHelper(this);
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private boolean mTwoPane;
    Movie movie;
    @Override
        public void onItemSelected(Movie movie,int flag) {
        this.movie=movie;
        if (mTwoPane) {

                    Bundle args = new Bundle();
                    args.putSerializable("Movie", movie);

                    DetailFragment fragment = new DetailFragment();
                    fragment.setArguments(args);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_container, fragment, DETAILFRAGMENT_TAG)
                            .commit();
                } else if(flag==0) {
                    Intent intent = new Intent(this, DetailActivity.class)
                            .putExtra("Movie",movie);
                    startActivity(intent);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.detail_container) != null) {
            if (savedInstanceState == null) {
                mTwoPane=true;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        }
        else
            mTwoPane=false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    @Override
    protected void onResume() {
        super.onResume();
         /* MovieFragment ff = (MovieFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_movie);
            if ( null != ff ) {
               // ff.mGridAdapter.clear();
               ff.updateMovies();
            }
        /*DetailFragment df = (DetailFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
                   /* if ( null != df ) {
                            df.up();
                        }*/
    }
}
