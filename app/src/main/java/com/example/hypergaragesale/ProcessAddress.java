package com.example.hypergaragesale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by henrylai on 5/24/16.
 */
public class ProcessAddress extends AsyncTask<String,Void,List<Address>> {

    private GoogleMap googleMap;
    private Context ctx;
    private String streetAddress;


    public ProcessAddress(GoogleMap googleMap, Context ctx, String streetAddress){
        this.googleMap = googleMap;
        this.ctx = ctx;
        this.streetAddress = streetAddress;
    }
    @Override
    protected List<Address> doInBackground(String ... param) {
        //streetAddress = addresses[0]
        String test = "1111 Lockheed Martin Way, Sunnyvale, CA";
        if (test.equalsIgnoreCase(streetAddress)){
                Log.v("The strings are equal!",streetAddress);
        };

        List<Address> addressList = null;
        Geocoder fwdGeocoder = new Geocoder(ctx,Locale.US);
        try{
            addressList = fwdGeocoder.getFromLocationName("1111 Lockheed Martin Way, Sunnyvale, CA",10);
            Log.v("input address: ",streetAddress);
            Log.v("result address: ", addressList.toString());
        }
        catch (IOException e){

        }

        return addressList;
    }

    protected void onPostExecute(List<Address> address) {
        Address location;
        double latitude;
        double longitdue;
        location = address.get(0);
        latitude = location.getLatitude();
        longitdue = location.getLongitude();
        Log.v("Location: ",location.toString());
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitdue)).title("Item Location"));
    }
}
