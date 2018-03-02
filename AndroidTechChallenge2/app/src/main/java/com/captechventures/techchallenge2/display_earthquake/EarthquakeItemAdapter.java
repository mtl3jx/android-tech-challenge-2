package com.captechventures.techchallenge2.display_earthquake;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.captechventures.techchallenge2.R;
import com.captechventures.techchallenge2.display_earthquake.util.DiffCallback;
import com.captechventures.techchallenge2.model.Earthquake;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mluansing on 9/22/17.
 */

public class EarthquakeItemAdapter extends RecyclerView.Adapter<EarthquakeItemAdapter.EarthquakeViewHolder> {

    private final Context context;
    private List<Earthquake> earthquakeList;
    private EarthquakeItemListener earthquakeItemListener;

    // tag for logging purposes
    private static final String TAG = EarthquakeItemAdapter.class.getSimpleName();

    public EarthquakeItemAdapter(@NonNull Context context, @LayoutRes int resource, List<Earthquake> earthquakeList, EarthquakeItemListener earthquakeItemListener) {
        this.context = context;
        this.earthquakeList = earthquakeList;
        this.earthquakeItemListener = earthquakeItemListener;
    }

    // overridden methods
    @Override
    public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_earthquake,parent, false);
        return new EarthquakeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final EarthquakeViewHolder holder, int position) {
        final Earthquake quake = earthquakeList.get(position);

        TableRow tableRow = (TableRow) holder.tableRow;

        // shade background red if high magnitude
        double magnitude = quake.getMagnitude();
        if (magnitude >= 7) {
            tableRow.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        } else if (magnitude >= 5) {
            tableRow.setBackgroundColor(ContextCompat.getColor(context, R.color.light_red));
        } else {
            tableRow.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        // consistent format for lat / lon - 4 decimal places
        // this is done with decimal format on render to not lose data precision
        DecimalFormat df = new DecimalFormat("####.0000");

        // make UI changes to row based on quake data
        holder.title.setText(quake.getTitle());
        holder.lat.setText(df.format(quake.getLatitude()));
        holder.lon.setText(df.format(quake.getLongitude()));
        holder.time.setText(quake.getDateTime());

        // save checkbox data in earthquake list
        final CheckBox checkBox = holder.checkBox;
        checkBox.setChecked(quake.isChecked());

        // make checkboxes clickable
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // print which checkbox was clicked
                final int pos = holder.getAdapterPosition();

                if (b) {
                    Log.v(TAG, pos + " checkbox checked!");

                    // enable unselect and delete buttons
                    earthquakeItemListener.enableButtons();

                } else {
                    Log.v(TAG, pos + " checkbox unchecked!");

                    // disable buttons if nothing checked
                    if (noneSelected()) earthquakeItemListener.disableButtons();
                }
                earthquakeList.get(pos).setChecked(b);
            }
        });

        // make rest of row clickable
        holder.clickableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int pos = holder.getAdapterPosition();
                Log.v(TAG, "Item " + pos + " clicked");

                // verify network connection
                if (earthquakeItemListener.isConnectedToInternet()) {
                    // pull up url with details of quake
                    earthquakeItemListener.openURL(earthquakeList.get(pos).getUrl());
                } else {
                    Log.d(TAG, "Not connected to internet");
                    Toast.makeText(context, "Cannot Load Earthquake Details\n" +
                            "No Network Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return earthquakeList.size();
    }

    public List<Earthquake> getEarthquakeList() {
        return earthquakeList;
    }

    // helper methods (public then private)
    public void updateList(List<Earthquake> earthquakes) {

        // diffUtil --> makes notify data set changes position specific and faster
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(this.earthquakeList, earthquakes));

        Log.d(TAG, "Using DiffUtil to calculate position changes");

        // update earthquakeList
        this.earthquakeList = earthquakes;

        // make changes to view
        diffResult.dispatchUpdatesTo(this);
    }

    public void deleteRows(RecyclerView recyclerView) {
        // delete rows from earthquakeList if they are checked
        final int count = earthquakeList.size();

        // create newEarthquakeList to hold all earthquakes to save
        List<Earthquake> newEarthquakeList = new ArrayList<>();

        // loop through earthquakes -- if not selected then put into new list
        for (Earthquake quake : earthquakeList) {
            // save all earthquakes not checked for deletion
            if(!quake.isChecked()) {
                newEarthquakeList.add(quake);
            }
        }

        // then set to new list of earthquakes
        updateList(newEarthquakeList);

        // uncheck all checkboxes
        unselectAll();

        // compute # of rows deleted
        int result = count - earthquakeList.size();
        Log.d(TAG, result + " rows deleted");
    }

    public void unselectAll() {
        // unselect all checked boxes
        for (Earthquake quake : earthquakeList) {
            quake.setChecked(false);
        }

        // update view
        notifyDataSetChanged();
    }

    public boolean noneSelected() {
        // check if any checkboxes are selected
        for (Earthquake quake : earthquakeList)
            if (quake.isChecked()) return false;
        return true;
    }

    // classes / interfaces

    // holds the view for each Earthquake's TableRow
    public class EarthquakeViewHolder extends RecyclerView.ViewHolder {
        public TableRow tableRow;
        public LinearLayout clickableRow;
        public TextView title, lat, lon, time;
        public CheckBox checkBox;

        // view holder constructor
        public EarthquakeViewHolder(View view) {
            super(view);
            tableRow = (TableRow) view.findViewById(R.id.table_row);
            clickableRow = (LinearLayout) view.findViewById(R.id.clickable_row);
            title = (TextView) tableRow.findViewById(R.id.row_title);
            lat = (TextView) tableRow.findViewById(R.id.row_latitude);
            lon = (TextView) tableRow.findViewById(R.id.row_longitude);
            time = (TextView) tableRow.findViewById(R.id.row_time);
            checkBox = (CheckBox) tableRow.findViewById(R.id.checkBox);
        }
    }

    // one interface for adapter --> activity
    public interface EarthquakeItemListener {
        void deselectAll();
        void deleteRows();
        void enableButtons();
        void disableButtons();
        void openURL(String quake_url);
        boolean isConnectedToInternet();
    }
}