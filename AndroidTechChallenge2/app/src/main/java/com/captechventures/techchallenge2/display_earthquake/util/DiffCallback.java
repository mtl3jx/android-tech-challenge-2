package com.captechventures.techchallenge2.display_earthquake.util;

import android.support.v7.util.DiffUtil;

import java.util.List;

import com.captechventures.techchallenge2.model.Earthquake;

/**
 * Created by mluansing on 9/29/17.
 */

public class DiffCallback extends DiffUtil.Callback {

    public List<Earthquake> oldEarthquakes, newEarthquakes;

    public DiffCallback(List<Earthquake> newEarthquakes, List<Earthquake> oldEarthquakes) {
        this.newEarthquakes = newEarthquakes;
        this.oldEarthquakes = oldEarthquakes;
    }

    @Override
    public int getOldListSize() {
        return oldEarthquakes.size();
    }

    @Override
    public int getNewListSize() {
        return newEarthquakes.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldEarthquakes.get(oldItemPosition).getId() == newEarthquakes.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldEarthquakes.get(oldItemPosition).equals(newEarthquakes.get(newItemPosition));
    }

}