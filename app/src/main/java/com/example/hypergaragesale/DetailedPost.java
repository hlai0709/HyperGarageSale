package com.example.hypergaragesale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class DetailedPost extends AppCompatActivity {

    private ImageView imageView;
    private TextView itemTitle, itemPrice;
    private File imageFile;
    private String picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);
        //set views
        imageView = (ImageView) findViewById(R.id.detailImage);
        itemTitle = (TextView) findViewById(R.id.detailTitle);
        itemPrice = (TextView) findViewById(R.id.detailPrice);

        //get data from intent to display in views
        picPath = getIntent().getStringExtra("img_link");
        imageFile = new File(picPath);
        Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        imageView.setImageBitmap(image);
        itemTitle.setText(getIntent().getStringExtra("itemTitle"));
        itemPrice.setText(getIntent().getStringExtra("itemPrice"));
    }
}
