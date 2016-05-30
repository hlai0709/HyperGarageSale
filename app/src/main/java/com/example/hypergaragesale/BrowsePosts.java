package com.example.hypergaragesale;

/**
 * Created by Taral on 3/12/2016.
 */
public class BrowsePosts {
    public String mTitle;
    public String mPrice;
    public String mPicLink;
    public String mDescription;
    public String mAddress;


    public BrowsePosts (String title, String price, String picLink, String description, String address) {
        this.mTitle = title;
        this.mPrice = price;
        this.mPicLink = picLink;
        this.mDescription = description;
        this.mAddress = address;

    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmPicLink() {
        return mPicLink;
    }

    public String getmTitle() {

        return mTitle;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmDescription() {
        return mDescription;
    }

}
