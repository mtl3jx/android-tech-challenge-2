package com.captechventures.techchallenge2.display_earthquake;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.captechventures.techchallenge2.model.Earthquake;

/**
 * Created by mluansing on 9/20/17.
 */

public class FetchJsonData extends AsyncTask<Object, Object, Void> {

    private List<Earthquake> earthquakeList;
    private EarthquakeLoadListener earthquakeLoadListener;

    // tag for logging purposes
    private static final String TAG = FetchJsonData.class.getSimpleName();

    public FetchJsonData(Context context) {
        if (context instanceof EarthquakeLoadListener)
            this.earthquakeLoadListener = (EarthquakeLoadListener) context;
        else {
            Log.e(TAG, "Initialization error");
        }
    }

    @Override
    protected Void doInBackground(Object... voids) {
        // background thread
        try {
            // connect to URL
            URL jsonDataUrl = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson");
            HttpURLConnection httpURLConnection = (HttpURLConnection) jsonDataUrl.openConnection();

            // set up tools to read from stream
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // read by line and put in buffer
            String line = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                // read line
//                Log.d(TAG, line);

                // add to string buffer
                stringBuffer.append(line);
            }

            // iterate through list at "features" and put each JSON object into an Earthquake
            JSONObject featureCollection = new JSONObject(stringBuffer.toString());
            JSONObject metadata = (JSONObject) featureCollection.getJSONObject("metadata");
            int count = metadata.getInt("count");
            JSONArray features = (JSONArray) featureCollection.getJSONArray("features");

            // validate metadata count
            if (count != features.length()) {
                Log.e(TAG, "Metadata error in reading array size");
            } else {
                earthquakeList = new ArrayList<>();
            }

            // reset progress bar
            earthquakeLoadListener.updateProgress(0.0);

            // iterate through array for each earthquake (given count # of earthquakes)
            for (int i = 0; i < count; i++) {
                JSONObject earthquake = (JSONObject) features.get(i);
//                Log.d(TAG, earthquake);

                // temporary JSON objects to help with parsing
                JSONObject prop = (JSONObject) earthquake.getJSONObject("properties");
                JSONObject geom = (JSONObject) earthquake.getJSONObject("geometry");
                JSONArray coord = (JSONArray) geom.getJSONArray("coordinates");

                // parse values from JSON
                String title = prop.getString("title");
                double latitude = coord.getDouble(1);
                double longitude = coord.getDouble(0);
                Date time = new Date(prop.getLong("time"));

                Double magnitude;
                if (prop.get("mag") != JSONObject.NULL)
                    magnitude = prop.getDouble("mag");
                else
                    magnitude = -1.0; // set magnitude to -1 if null

                String url = prop.getString("url");
                String id = earthquake.getString("id");

                // create new Earthquake object
                Earthquake quake = new Earthquake(title, latitude, longitude, time, magnitude, url, id);

                // add Earthquake to list
                earthquakeList.add(quake);

                // update progress bar with percentage
                earthquakeLoadListener.updateProgress(((i+1) / (double) count) * 100);
            }

            // close input stream and http connection
            inputStream.close();
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // pass back list to activity (by calling some method that makes the UI changes)
        earthquakeLoadListener.afterDataLoad(earthquakeList);
    }

    // one interface for async --> activity
    public interface EarthquakeLoadListener {
        void afterDataLoad(List<Earthquake> earthquakeList);
        void updateProgress(double progress);
    }

}
