package com.leacuke.android.imagedownloadandfilter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by pthakkar9 on 4/19/2015.
 * <p/>
 * Handles filtering image with AsyncTask
 */
public class FilterImageTask extends AsyncTask<Uri, Void, Uri> {

    private String TAG = getClass().getSimpleName();

    private TaskCompleted mTaskCompleted;

    private Context mContext;

    public FilterImageTask(Context context, TaskCompleted taskCompleted) {
        mContext = context;
        mTaskCompleted = taskCompleted;
    }

    @Override
    protected Uri doInBackground(Uri... uris) {
        Uri imageFilePathUri;

        Log.d(TAG, "doInBackground called with URI - " + uris[0].toString());
        imageFilePathUri = Utils.grayScaleFilter(mContext, uris[0]);
        return imageFilePathUri;
    }

    @Override
    protected void onPostExecute(Uri uri) {
        Log.d(TAG, "onPostExecute called");

        // This causes onFilterComplete method to be executed in RetainedFragment
        mTaskCompleted.onFilterComplete(uri);

    }
}
