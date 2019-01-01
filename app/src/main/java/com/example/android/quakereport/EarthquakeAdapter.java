package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    String primaryLocation;
    String locationOffset;
    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);
        String originalLocation = currentEarthquake.getLocation();

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);

        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        magnitudeView.setText(formattedMagnitude);

//        TextView locationView = (TextView) listItemView.findViewById(R.id.location);
//        locationView.setText(currentEarthquake.getLocation());

        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);

        return listItemView;
    }
}
