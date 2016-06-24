package com.example.hypergaragesale;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by henrylai on 6/17/16.
 */
public class SearchResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_result);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {


            //declare database
            PostsDbHelper mDbHelper = new PostsDbHelper(this);
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            //parse query string
            String query = intent.getStringExtra(SearchManager.QUERY);
            String stringQuery= "SELECT * FROM " + Posts.PostEntry.TABLE_NAME + " WHERE " + Posts.PostEntry.COLUMN_NAME_TITLE
                    + " LIKE ?";

            Cursor cursor = db.rawQuery(stringQuery,new String[] {query});
            showResults(cursor);


           // setContentView(R.layout.activity_result);

            //use the query to search
        }
    }

    public void showResults(Cursor cursor){
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        setContentView(R.layout.activity_browse_posts);
        // looping through all rows and adding to list
        ArrayList<BrowsePosts> browsePosts = new ArrayList<BrowsePosts>();
        if (cursor.moveToFirst()) {
            do {
                browsePosts.add(new BrowsePosts(
                        cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_NAME_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_NAME_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_NAME_PHOTO_DIR)),
                        cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_NAME_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_NAME_ADDRESS))));
            } while (cursor.moveToNext());
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.posts_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PostsAdapter(browsePosts,this);
        mRecyclerView.setAdapter(mAdapter);
        cursor.close();
    }



}
