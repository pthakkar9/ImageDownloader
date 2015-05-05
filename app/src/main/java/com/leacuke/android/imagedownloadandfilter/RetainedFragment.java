package com.leacuke.android.imagedownloadandfilter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by pthakkar9 on 4/21/2015.
 * <p/>
 * This fragment is retained during runtime changes.
 */
public class RetainedFragment extends Fragment implements TaskCompleted {

    private String TAG = getClass().getSimpleName();

    Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This makes this Fragment not destroyed during runtime changes.
        setRetainInstance(true);
    }

    public void startDownloadTask(Uri downloadUri) {
        // Create a new task to download image and execute it
        DownloadImageTask downloadImageTask = new DownloadImageTask(mContext, this);
        downloadImageTask.execute(downloadUri);
    }

    // This  is called when DownloadImageTask finishes onPostExecute
    @Override
    public void onDownloadComplete(Uri uri) {

        Log.d(TAG, "onDownloadComplete called with URI - " + uri);

        // Create a new task (thread) and execute for filtering image
        FilterImageTask filterImageTask = new FilterImageTask(mContext, this);
        filterImageTask.execute(uri);
    }

    // This  is called when FilterImageTask finishes onPostExecute
    @Override
    public void onFilterComplete(Uri uri) {

        // Filtering image is completed in path to the filtered image is in uri
        Log.d(TAG, "onFilterComplete called with URI - " + uri);

        // Start an intent to view filtered image
        Intent showImageIntent = new Intent(Intent.ACTION_VIEW);
        showImageIntent.setDataAndType(Uri.parse("file://" + uri), "image/*");
        startActivity(showImageIntent);
    }


}
