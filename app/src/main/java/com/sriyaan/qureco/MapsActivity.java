package com.sriyaan.qureco;

import android.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FloatingActionButton fab;
    String lat = "", longt = "";
    EditText etSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub
                mMap.clear();
                Log.d("arg0", arg0.latitude + "-" + arg0.longitude);
                lat     = String.valueOf(arg0.latitude);
                longt   = String.valueOf(arg0.longitude);
                LatLng sydney = new LatLng(arg0.latitude, arg0.longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title("You"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
            }
        });
        etSearch = (EditText) findViewById(R.id.search);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    // Getting user input location
                    String location = etSearch.getText().toString();

                    if (location != null && !location.equals("")) {
                        try {
                            LatLng newpoint = getLocationFromAddress(MapsActivity.this, location);
                            mMap.addMarker(new MarkerOptions().position(newpoint).title(location));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newpoint, 15));
                            hideKeyboard();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lat.equals("0"))
                {

                }else
                if(longt.equals("0"))
                {

                }
                else
                {
                    //Toast.makeText(MapActivity.this,"lat"+prefs.getString("lat","")+"longt"+prefs.getString("longt",""),Toast.LENGTH_SHORT).show();
                    MainActivity.str_lon = longt;
                    MainActivity.str_lat = lat;

                    onBackPressed();
                }


            }
        });
    }
    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            lat     = String.valueOf(location.getLatitude());
            longt   = String.valueOf(location.getLongitude());

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    public void hideKeyboard() throws Exception
    {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
