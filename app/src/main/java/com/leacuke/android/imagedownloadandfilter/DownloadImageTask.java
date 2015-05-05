package com.leacuke.android.imagedownloadandfilter;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

/**
 * Created by pthakkar9 on 4/19/2015.
 * <p/>
 * Handles downloading image with AsyncTask
 */
public class DownloadImageTask extends AsyncTask<Uri, Void, Uri> {

    private String TAG = getClass().getSimpleName();

    private TaskCompleted mTaskCompletedListener;

    private Context mContext;

    // Constructor that get Context.
    // Later used in downloadImage method in Utils.
    public DownloadImageTask(Context context, TaskCompleted taskCompleted) {
        mContext = context;
        mTaskCompletedListener = taskCompleted;
    }

    @Override
    protected Uri doInBackground(Uri... uris) {
        Uri imageFilePathUri;

        Log.d(TAG, "doInBackground called with URI - " + uris[0].toString());
        imageFilePathUri = Utils.downloadImage(mContext, uris[0]);
        return imageFilePathUri;
    }


    @Override
    protected void onPostExecute(Uri uri) {
        Log.d(TAG, "onPostExecute called");

        if (uri == null) {
            // Null is returned from downloadImage when URL is not pointed to an image
            Utils.showToast(mContext, "This URL is not pointing to an image. " +
                    "Please enter a valid URL to download image.");
            Log.d(TAG, "getUri is returning null URI. Entered URL doesn't point to an image.");

        } else {
            // This causes onDownloadComplete method to be executed in RetainedFragment
            mTaskCompletedListener.onDownloadComplete(uri);
        }
    }


}
