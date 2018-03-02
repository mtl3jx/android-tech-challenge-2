package com.captechventures.techchallenge2.display_earthquake;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.captechventures.techchallenge2.R;
import com.captechventures.techchallenge2.display_earthquake.detail.EarthquakeDetailsActivity;
import com.captechventures.techchallenge2.model.Earthquake;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DisplayEarthquakesActivity extends AppCompatActivity implements
        FetchJsonData.EarthquakeLoadListener,           // async task uses to call activity methods
        EarthquakeItemAdapter.EarthquakeItemListener     // adapter uses to call activity methods
{
    // async task instance
    private FetchJsonData process;

    // layout vars
    private Button button_load_data, button_delete, button_unselect;
    private RecyclerView recyclerView;
    private TableRow tableHeadings;
    private EarthquakeItemAdapter adapter;

    // progress bar things
    private RelativeLayout progressLayout;
    private ProgressBar progressBar;
    private TextView progressBar_text;

    // tag for logging purposes
    private static final String TAG = DisplayEarthquakesActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // content view only set in onCreate
        setContentView(R.layout.activity_display_earthquakes);

        // do all findViewByIds in onCreate to set up view
        button_load_data = (Button) findViewById(R.id.button_load_data);
        button_delete = (Button) findViewById(R.id.button_delete);
        button_unselect = (Button) findViewById(R.id.button_unselect);

        // set up progress bar variables
        progressLayout = (RelativeLayout) findViewById(R.id.progressLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar_text = (TextView) findViewById(R.id.progressBar_text);

        // set up table stuff
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        tableHeadings = (TableRow) findViewById(R.id.table_headings);
        adapter = new EarthquakeItemAdapter(this, R.layout.item_earthquake, new ArrayList<Earthquake>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // make reload button respond to clicking
        button_load_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Reload button was clicked!");

                // handle network failures
                if (isConnectedToInternet()) {

                    // hide table
                    tableHeadings.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);

                    // reset progress bar
                    updateProgress(0);

                    // show progress bars
                    progressLayout.setVisibility(View.VISIBLE);

                    // start data load
                    if (process != null && !process.isCancelled()) {
                        process.cancel(true);
                    }
                    process = new FetchJsonData(DisplayEarthquakesActivity.this);
                    process.execute();

                } else {

                    Log.d(TAG, "Not connected to internet");
                    Toast.makeText(getApplicationContext(), "Cannot Reload Data from Server\n" +
                            "No Network Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // make delete button respond to clicks
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Delete button was clicked!");

                // delete rows from earthquake list
                deleteRows();
            }
        });

        // make unselect button respond to clicks
        button_unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Unselect all button was clicked!");

                deselectAll();
            }
        });

        // handle network failures
        if (isConnectedToInternet()) {
            // reset progress
            updateProgress(0);

            // show progress bars
            progressLayout.setVisibility(View.VISIBLE);

            // cancel process if running
            if (process != null && !process.isCancelled()) {
                process.cancel(true);
            }

            // start data load
            process = new FetchJsonData(DisplayEarthquakesActivity.this);
            process.execute();

        } else {
            Log.d(TAG, "Not connected to internet");
            Toast.makeText(getApplicationContext(), "Cannot Load Data from Server\n" +
                    "No Network Connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // cancel process when activity destroy
        if (process != null && !process.isCancelled()) {
            process.cancel(true);
        }
    }

    @Override
    public void afterDataLoad(List<Earthquake> earthquakes) {
        // grab list of earthquakes from callback function
        disableButtons();

        // make table visible
        tableHeadings.setVisibility(View.VISIBLE);

        // create recycler view and custom array adapter to specify how to render each earthquake within table
        recyclerView.setVisibility(View.VISIBLE);

        // update list of earthquakes
        adapter.updateList(earthquakes);

        // notify adapter the list has changed
        adapter.notifyDataSetChanged();

        // hide progress bars
        progressLayout.setVisibility(View.GONE);

        Toast.makeText(getApplicationContext(), "Click on a row to view details", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateProgress(final double progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DecimalFormat df = new DecimalFormat("##0.0");
                progressBar.setProgress((int) progress);
                progressBar_text.setText(df.format(progress) + "%");
            }
        });
    }

    @Override
    public void enableButtons() {
        button_delete.setEnabled(true);
        button_unselect.setEnabled(true);
    }

    @Override
    public void disableButtons() {
        button_delete.setEnabled(false);
        button_unselect.setEnabled(false);
    }

    @Override
    public void deselectAll() {
        // disable delete / select all buttons
        disableButtons();

        // tell adapter to uncheck all boxes
        adapter.unselectAll();
    }

    @Override
    public void deleteRows() {
        // disable delete / select all buttons
        disableButtons();

        // tell adapter to remove rows from recyclerview
        adapter.deleteRows(recyclerView);
    }

    @Override
    public void openURL(String url) {
        // open url in internal browser with quake details

        // serialize the url
        Bundle bundle = new Bundle();
        bundle.putString("url", url);

        // pass dateList to second activity via intent
        Intent intent = new Intent(this, EarthquakeDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

}
