package peaceinfotech.malegaonbazar.User.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.User.Fragment.FragmentOffers;
import peaceinfotech.malegaonbazar.User.GetData.GetDirectionsData;

public class DirectionActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final int LOCATION_REQUEST = 500;
    GoogleMap mMap;
    android.support.v7.widget.Toolbar toolbar;
    FusedLocationProviderClient mfusedLocationProviderClient;
    MapView mMapView;
    GoogleApiClient client;
    Boolean mLocationPermissionGranted = false;
    LocationRequest locationRequest;
    Double orglatitude,orglongitude,destlatitude,destlongitude;
    LatLng orglatlng,destlatlng;
    String destName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_direction);

        mMapView=findViewById(R.id.mapViewDirec);
        toolbar=findViewById(R.id.toold);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Directions");


        initGoogleMap(savedInstanceState);
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Intent getIn=getIntent();
        orglatlng=getIn.getExtras().getParcelable("origin");
        destlatlng=getIn.getExtras().getParcelable("end");
        destName=getIn.getStringExtra("name");




        orglatitude=orglatlng.latitude;
        orglongitude=orglatlng.longitude;


//        Log.d("lat",orglatitude.toString()+"/"+orglongitude.toString());
        destlatitude=destlatlng.latitude;
        destlongitude=destlatlng.longitude;



    }

    public void getDirectionData(){

        String url = getDirectionUrl();
        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
//        dataTransfer[2] = destlatlng;
        GetDirectionsData getDirectionsData = new GetDirectionsData(destlatlng);
        getDirectionsData.execute(dataTransfer);
        Toast.makeText(DirectionActivity.this, "Direction", Toast.LENGTH_LONG).show();

    }

    private String getDirectionUrl() {

        StringBuilder directionUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        directionUrl.append("origin=" + orglatitude + "," + orglongitude);
        directionUrl.append("&destination=" + destlatitude + "," + destlongitude);
        directionUrl.append("&key=" + "AIzaSyAEdRkdwVissmatsKvama28utF65K-4ZA8");

        return directionUrl.toString();

    }

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(FragmentOffers.MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(orglatlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(orglatlng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptions).showInfoWindow();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mLocationPermissionGranted=false;
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                            mLocationPermissionGranted=true;
                        }
                    }
                    mMap.setMyLocationEnabled(true);
                } else {
                    Toast.makeText(this, "permission Denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap=googleMap;
//        googleMap.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        googleMap.setMyLocationEnabled(true);
        getLocation();

        String url = getDirectionUrl();
        Object dataTransfer[] = new Object[3];
        dataTransfer[0] = googleMap;
        dataTransfer[1] = url;
        dataTransfer[2]=destName;
//        dataTransfer[2] = destlatlng;
        GetDirectionsData getDirectionsData = new GetDirectionsData(destlatlng);
        getDirectionsData.execute(dataTransfer);
        Toast.makeText(DirectionActivity.this, "Direction", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(FragmentOffers.MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(FragmentOffers.MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

}
