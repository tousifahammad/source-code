package com.app.geolocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import locationprovider.davidserrano.com.LocationProvider;

public class MainActivity extends AppCompatActivity {
    TextView tv_lat_long, tv_address;
    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnabled) {
            Toast.makeText(this, "Please enable GPS and try again", Toast.LENGTH_LONG).show();
            finish();

        } else {
            // set Toolbar
            initUI();

            requestPermission();
        }
    }


    private void initUI() {
        tv_lat_long = findViewById(R.id.tv_lat_long);
        tv_address = findViewById(R.id.tv_address);
    }

    void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            getCurrentLocation();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void getCurrentLocation() {
        //create a callback
        LocationProvider.LocationCallback callback = new LocationProvider.LocationCallback() {

            @Override
            public void onNewLocationAvailable(float lat, float lon) {
                //location update

                getAddressByLatLong(lat, lon);

                Log.d("1111", "latitude: " + lat + "   longitude: " + lon);

                tv_lat_long.setText("Latitude: " + lat + "\nLongitude: " + lon);

            }

            @Override
            public void locationServicesNotEnabled() {
                //failed finding a location
            }

            @Override
            public void updateLocationInBackground(float lat, float lon) {
                //if a listener returns after the main locationAvailable callback, it will go here
            }

            @Override
            public void networkListenerInitialised() {
                //when the library switched from GPS only to GPS & network
            }

            @Override
            public void locationRequestStopped() {

            }
        };

        //initialise an instance with the two required parameters
        LocationProvider locationProvider = new LocationProvider.Builder()
                .setContext(this)
                .setListener(callback)
                .create();

        //start getting location
        locationProvider.requestLocation();
    }

    private void getAddressByLatLong(float lat, float lon) {
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            Log.d("1111", "addresses: " + addresses);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                // sending back first address line and locality
                String result = address.getAddressLine(0) + ", " + address.getLocality();

                tv_address.setText(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
