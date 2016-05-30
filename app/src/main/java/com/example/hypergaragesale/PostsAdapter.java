package com.example.hypergaragesale;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Henry on 5/13/2016.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private ArrayList<BrowsePosts> mDataset;
    private File imageFile;
    private String picPath;
    private Context ctx;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTitle;
        public TextView mPrice;
        public ImageView imageView;
        public ArrayList<BrowsePosts> mData;
        public Context ctx;

        public ViewHolder(View view, Context ctx, ArrayList<BrowsePosts> mData) {
            super(view);
            this.mData = mData;
            this.ctx = ctx;
            mTitle = (TextView) itemView.findViewById(R.id.list_title);
            mPrice = (TextView) itemView.findViewById(R.id.list_price);
            imageView = (ImageView) view.findViewById(R.id.itemPicture);
            // Implement view click Listener when make each row of RecycleView clickable
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            BrowsePosts browsePosts = this.mData.get(pos);
            Intent intent = new Intent(this.ctx,DetailedPost.class);
            intent.putExtra("img_link",browsePosts.getmPicLink());
            intent.putExtra("itemTitle",browsePosts.getmTitle());
            intent.putExtra("itemPrice",browsePosts.getmPrice());
            intent.putExtra("itemDescription",browsePosts.getmDescription());
            intent.putExtra("itemAddress",browsePosts.getmAddress());
            this.ctx.startActivity(intent) ;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PostsAdapter(ArrayList<BrowsePosts> myDataset, Context ctx)
    {
        mDataset = myDataset;
        this.ctx = ctx;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v,ctx,mDataset );
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get elements from your dataset at this position
        // - replace the contents of the views with that elements
        holder.mTitle.setText(mDataset.get(position).mTitle);
        holder.mPrice.setText(mDataset.get(position).mPrice);
        //get bitmap from pic link
        picPath = mDataset.get(position).mPicLink;
        imageFile = new File(picPath);
        //Log.i("aaa","qqq"+imageFile.getAbsolutePath());
        //Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        //holder.imageView.setImageBitmap(image);

        //add decode image code here (test)
        int reqWidth = 0;
        int reqHeight = 0;
       // reqWidth = holder.imageView.getLayoutParams().width;
        //reqHeight = holder.imageView.getLayoutParams().height;
        //holder.imageView.setImageBitmap(decodeSampledBitmapFromFile(imageFile.getAbsolutePath(),
          //      reqWidth,reqHeight));
        loadBitmap(1,holder.imageView,imageFile.getAbsolutePath(),holder);

    }

    /*
    public void loadBitmap(int resId, ImageView imageView,String fileName) {
        if (ProccessBitmapTask.cancelPotentialWork(resId, imageView)) {
            final ProccessBitmapTask task = new ProccessBitmapTask(imageView,fileName);
            final ProccessBitmapTask.AsyncDrawable asyncDrawable =
                    new ProccessBitmapTask.AsyncDrawable(, mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }*/


    public void loadBitmap(int resId, ImageView imageView, String fileName,ViewHolder holder) {
        ProccessBitmapTask task = new ProccessBitmapTask(imageView,fileName,holder);
        task.execute(resId);
    }



    /* Need to ask Taral what is resources and how is it used?
    public void loadBitmap(int resId, ImageView imageView,String fileName) {
        if (ProccessBitmapTask.cancelPotentialWork(resId, imageView)) {
            final ProccessBitmapTask task = new ProccessBitmapTask(imageView,fileName);
            final ProccessBitmapTask.AsyncDrawable asyncDrawable =
                    new ProccessBitmapTask.AsyncDrawable(, mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }
    */


    /*Delete the decode and insample function later to process pics in background
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String filename,
                                                     int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);


        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }*/


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}