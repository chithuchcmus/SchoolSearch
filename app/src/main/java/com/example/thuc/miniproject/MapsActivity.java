package com.example.thuc.miniproject;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    MyPlace myPlace;
    List<MyPlace> myPlaces;
    MyDataBase db;
    private GoogleMap mMap;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = MyDataBase.getInstance(this);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        floatingActionButton = (FloatingActionButton)findViewById(R.id.favourite_button);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        myPlaces = db.getLocationByName(((MyPlace)extras.get("data")).getName());
        myPlace =myPlaces.get(0);
        updateFavBtn(myPlace.getImage());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myPlace.getImage()>0)
                {
                    myPlace.setImage(0);
                    updateFavBtn(myPlace.getImage());
                    db.updateFavourite(myPlace,0);
                }

                else
                {
                    myPlace.setImage(1);
                    updateFavBtn(myPlace.getImage());
                    db.updateFavourite(myPlace,1);
                }
            }
        });
    }
    private void updateFavBtn(Integer favourite) {
        if (favourite == 1) {
            floatingActionButton.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_favorite_black_56dp, getTheme()
            ));
        } else {
            floatingActionButton.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_unfavorite_border_black_24dp, getTheme()
            ));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<MyPlace> myPlaceList = db.getAllLocation();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(myPlace.getLatitude(), myPlace.getLongitude());

        Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(myPlace.getAddres()));
        marker.setTag(myPlace.getWebsite());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
    }

}
