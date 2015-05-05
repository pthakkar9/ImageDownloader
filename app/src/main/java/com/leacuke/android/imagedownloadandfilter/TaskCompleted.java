package com.leacuke.android.imagedownloadandfilter;

import android.net.Uri;

/**
 * Created by pthakkar9 on 4/19/2015.
 * <p/>
 * Helps passing values between AsyncTask(s) and Activities
 */
public interface TaskCompleted {
    void onDownloadComplete(Uri uri);
    void onFilterComplete(Uri uri);
}
