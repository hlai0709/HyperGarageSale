package com.example.hypergaragesale;

/**
 * Created by Taral on 3/12/2016.
 */
public class BrowsePosts {
    public String mTitle;
    public String mPrice;
    public String mPicLink;

    public BrowsePosts (String title, String price, String picLink) {
        this.mTitle = title;
        this.mPrice = price;
        this.mPicLink = picLink;
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
}
