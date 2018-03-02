package com.captechventures.techchallenge2.display_earthquake.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import com.captechventures.techchallenge2.R;

public class EarthquakeDetailsActivity extends AppCompatActivity {

    private WebView webView;

    // tag for logging purposes
    private static final String TAG = EarthquakeDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = (String) bundle.getSerializable("url");

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    // data (checked boxes, deleted rows) should be saved when returning to parent activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
