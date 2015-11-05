package com.satish.cloudrun;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.satish.cloudrun.R;

public class CreateTrackActivity extends AppCompatActivity implements LocationListener {

    private TextView latitudeField;
    private TextView longitudeField;
    private TextView accuracyField;
    private TextView altitudeField;
    private TextView speedField;
    private TextView timeField;

    private LocationManager locationManager;
    private Location location = null;
    private boolean isGPSEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_track);
        //addMapFragment();

        latitudeField = (TextView) findViewById(R.id.TextView02);
        longitudeField = (TextView) findViewById(R.id.TextView04);
        accuracyField = (TextView) findViewById(R.id.TextView06);
        altitudeField = (TextView) findViewById(R.id.TextView08);
        speedField = (TextView) findViewById(R.id.TextView10);
        timeField = (TextView) findViewById(R.id.TextView00);
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnabled) {
            Log.d("GPS", "not enabled");
            showSettingsAlert();
        } else {
            try {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } catch (SecurityException ex) {
                Log.d("LocationManager", ex.getMessage());
            }

            // Initialize the location fields
            if (location != null) {
                System.out.println("Provider GPS by default" + " has been selected.");
                onLocationChanged(location);
            } else {
                latitudeField.setText("Location not available");
                longitudeField.setText("Location not available");
            }
        }
    }

    /** Called when the user clicks the Start Mapping button */
    public void startMapsActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    /*
    private void addMapFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        googleMapFragment = new GoogleMapFragment();
        fragmentTransaction.add(R.id.My_Container_1_ID, googleMapFragment);
        fragmentTransaction.commit();
    }*/
    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException ex) {
            Log.d("LocationManager", ex.getMessage());
        }
    }

    /* Remove the locationlistner updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException ex) {
            Log.d("LocationManager", ex.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        float acu = location.getAccuracy();
        double alt = location.getAltitude();
        float speed = location.getSpeed();
        long time = location.getTime();

        latitudeField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));
        accuracyField.setText(String.valueOf(acu));
        altitudeField.setText(String.valueOf(alt));
        speedField.setText(String.valueOf(speed));
        timeField.setText(String.valueOf(time));

        GoogleMapFragment googleMapFragment = (GoogleMapFragment)
                getFragmentManager().findFragmentById(R.id.gmap);
        googleMapFragment.addMarker(lat, lng);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    private void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
