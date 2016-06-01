package com.example.hypergaragesale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.File;

public class DetailedPost extends FragmentActivity implements OnMapReadyCallback{

    private ImageView imageView;
    private TextView itemTitle, itemPrice, itemDescription;
    private File imageFile;
    private String picPath, itemAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);
        //set views
        imageView = (ImageView) findViewById(R.id.detailImage);
        itemTitle = (TextView) findViewById(R.id.detailTitle);
        itemPrice = (TextView) findViewById(R.id.detailPrice);
        itemDescription = (TextView) findViewById(R.id.detailDescription);

        //get data from intent to display in views
        picPath = getIntent().getStringExtra("img_link");
        imageFile = new File(picPath);
        Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        imageView.setImageBitmap(image);
        itemTitle.setText(getIntent().getStringExtra("itemTitle"));
        itemPrice.setText(getIntent().getStringExtra("itemPrice"));
        itemDescription.setText(getIntent().getStringExtra("itemDescription"));
        itemAddress = getIntent().getStringExtra("itemAddress");
        Log.v("initial input address: ",itemAddress);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       loadItemLocation(googleMap);
    }

    public void loadItemLocation(GoogleMap googleMap){
        ProcessAddress processAddressTask = new ProcessAddress(googleMap,this);
        processAddressTask.execute(itemAddress);
    }
}
