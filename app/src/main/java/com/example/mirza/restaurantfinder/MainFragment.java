package com.example.mirza.restaurantfinder;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

public class MainFragment extends Fragment {

    private static final String APP_ID = "0669806daa3758a8cca2698adfa47fa3";

    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    LocationManager locationManager;

    public boolean isNetworkAvailable() {
        return ((ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Double latitude, longitude;
        latitude = 0.0;
        longitude = 0.0;

        TextView textLatitude= (TextView) view.findViewById(R.id.tvLatitude);
        TextView textLongtitude= (TextView) view.findViewById(R.id.tvLongitude);

        if (isNetworkAvailable()) {
            locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(getActivity(), "Using GPS", Toast.LENGTH_LONG).show();

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 10, mLocationListener);
            } else {
                Toast.makeText(getActivity(), "Using network location", Toast.LENGTH_LONG).show();

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120000, 10, mLocationListener);
            }

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                textLatitude.setText("Latitude is: "+latitude.toString());
                textLongtitude.setText("Longitude is: "+ longitude.toString());
            } else {
                Toast.makeText(getActivity(), "Cannot get location", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();

        }


        double lat = latitude, lon = longitude;
        String units = "metric";

        String url = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s",
                lat, lon, units,APP_ID);

        url = "http://api.openweathermap.org/data/2.5/weather?lat=41,311081&lon=69,240562&units=metric&appid=0669806daa3758a8cca2698adfa47fa3";
        TextView textView = (TextView) view.findViewById(R.id.tvWeather);


        new GetWeather(textView).execute(url);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Restaurant Finder");
    }
}
