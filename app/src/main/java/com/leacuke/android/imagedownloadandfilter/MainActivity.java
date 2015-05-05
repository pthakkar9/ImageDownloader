package com.leacuke.android.imagedownloadandfilter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    private String TAG = getClass().getSimpleName();

    Context mContext;

    EditText editTextUrl;
    Button buttonDownload;

    // URI that points to the image to be downloaded
    private Uri imageUri;

    private RetainedFragment retainedFragment;

    // Tag for adding fragment
    private String TAG_RETAINED_FRAGMENT = "TAG_RETAINED_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        FragmentManager fragmentManager = getFragmentManager();
        retainedFragment = (RetainedFragment) fragmentManager.
                findFragmentByTag(TAG_RETAINED_FRAGMENT);

        if (retainedFragment == null) {
            retainedFragment = new RetainedFragment();
            fragmentManager.beginTransaction().add(retainedFragment, TAG_RETAINED_FRAGMENT).commit();
        }

        // Initialize UI components or as prof. Schmidt says "cache" them
        editTextUrl = (EditText) findViewById(R.id.editText_url);
        buttonDownload = (Button) findViewById(R.id.button_download);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Checks which option is clicked on action bar
        switch (id) {
            case R.id.action_about:
                Utils.showToast(MainActivity.this, "About pressed");
            case R.id.action_settings:
                Utils.showToast(MainActivity.this, "Settings pressed");
        }

        return super.onOptionsItemSelected(item);
    }

    // Called when Download Button (R.id.button_download) is pressed
    public void onDownloadButtonPressed(View view) {

        Log.d(TAG, "onDownloadButtonPressed called");

        Uri mUri = getUri();

        if (mUri != null) {
            retainedFragment.startDownloadTask(mUri);
        }
    }

    // This method handles getting URI from UI
    private Uri getUri() {

        String inputURIString;
        inputURIString = editTextUrl.getText().toString();

        // If URL is not entered
        if (inputURIString.isEmpty()) {
            // Get default URL from string resources
            imageUri = Uri.parse((String) getText(R.string.string_textViewDefaultUrl));
            Log.d(TAG, "getUri is returning default URI");
            return imageUri;
        }

        // If entered string is not URL
        if (!Patterns.WEB_URL.matcher(inputURIString).matches()) {
            Utils.showToast(mContext, "Invalid URL. Please enter a valid URL.");
            Log.d(TAG, "getUri is returning null URI. Entered string is not URL.");
            return null;
        }

        // Entered URL is pointing to an image
        imageUri = Uri.parse(inputURIString);
        Log.d(TAG, "getUri is returning image URI " + imageUri);
        return imageUri;
    }
}
