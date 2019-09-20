
	implementation 'com.github.david-serrano:locationprovider:1.2'
	implementation 'com.karumi:dexter:5.0.0'



    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission. ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />



    //================================= LOCATION =======================
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
                //Toast.makeText(PatientActivity.this, "lat : " + lat + "\nLong : " + lon, Toast.LENGTH_SHORT).show();

                getAddressByLatLong(lat, lon);

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
        Geocoder geocoder;
        List<Address> addresses;

        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                // sending back first address line and locality
                current_location = address.getAddressLine(0) + ", " + address.getLocality();

                Log.d("1111", "current_location: " + current_location);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }