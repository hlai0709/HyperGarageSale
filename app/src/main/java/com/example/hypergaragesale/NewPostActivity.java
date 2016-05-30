package com.example.hypergaragesale;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


public class NewPostActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private ContentValues values;

    private EditText titleText;
    private EditText descText;
    private EditText priceText;
    private EditText addressText;
    private Button picButton;
    private int lastPostId = 0;
    private PostsDbHelper mDbHelper = new PostsDbHelper(this);
    private String picPath;
    private File imagefile;
    Uri uriFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        picButton = (Button) findViewById(R.id.picButton);
        picButton.setEnabled(false);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        titleText = (EditText)findViewById(R.id.textView_title);
        descText = (EditText)findViewById(R.id.textView_desc);
        priceText = (EditText)findViewById(R.id.textView_price);
        addressText = (EditText) findViewById(R.id.textView_address);

        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();
    }

    private void showSnackBar(View v) {
        if (v == null) {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.new_post_snackbar,
                    Snackbar.LENGTH_SHORT).show();
        }
        else {
            Snackbar.make(v, R.string.new_post_snackbar,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

//    public void newPostAdded(View v) {
//        addPost();
//    }

    private long addPost() {
        // Create a new map of values, where column names are the keys
        values = new ContentValues();
        values.put(Posts.PostEntry.COLUMN_NAME_TITLE, titleText.getText().toString());
        values.put(Posts.PostEntry.COLUMN_NAME_DESCRIPTION, descText.getText().toString());
        values.put(Posts.PostEntry.COLUMN_NAME_PRICE, priceText.getText().toString());
        values.put(Posts.PostEntry.COLUMN_NAME_ADDRESS, addressText.getText().toString());
        values.put(Posts.PostEntry.COLUMN_NAME_PHOTO_DIR,"NULL");

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                Posts.PostEntry.TABLE_NAME,
                null,
                values);
        picButton.setEnabled(true);

        //getApplicationContext().getResources()

        return newRowId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_post) {
            showSnackBar(null);
            lastPostId = (int)addPost();
        }
        return super.onOptionsItemSelected(item);
    }

    public void TakePic(View view){
        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imagefile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        lastPostId + ".jpg");
        picPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + "/" + lastPostId + ".jpg";
        uriFile = Uri.fromFile(imagefile);
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT,uriFile);
        camIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(camIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100){
            switch (resultCode){
                case Activity.RESULT_OK:
                    if(imagefile.exists()){
                        Toast.makeText(getApplicationContext(),"The image was saved at " + picPath
                                ,Toast.LENGTH_LONG).show();
                        mDbHelper.insertPicLink(picPath,lastPostId);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"The image was not saved!",Toast.LENGTH_LONG).show();
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    break;

                default:
                    break;
            }
        }

    }
/*
    public void DeletePost(View v) {
        mDbHelper.deleteLastEntryData();
        Toast.makeText(this,"Last database entry has been deleted!",Toast.LENGTH_LONG).show();
    }*/
}
