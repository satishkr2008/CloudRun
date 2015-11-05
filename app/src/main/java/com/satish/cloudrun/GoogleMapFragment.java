package com.satish.cloudrun;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapFragment extends Fragment {

    MapView mapView;
    private GoogleMap googleMap;


    public GoogleMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_google_map, container, false);

        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mapView.getMap();
        // Add a marker in Sydney and move the camera
        LatLng origin = new LatLng(0, 0);
        googleMap.addMarker(new MarkerOptions().position(origin).title("Map Origin"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(origin));

        return v;
    }

    public void addMarker (double lat, double longi) {
        LatLng coordinate = new LatLng(lat, longi);
        googleMap.addMarker(new MarkerOptions().position(coordinate));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
