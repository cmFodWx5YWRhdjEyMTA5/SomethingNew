package peaceinfotech.malegaonbazar.User.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import peaceinfotech.malegaonbazar.OnBackPressedListener;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.User.OffersListAdapter;
import peaceinfotech.malegaonbazar.User.OffersListModel;
import peaceinfotech.malegaonbazar.User.UI.DirectionActivity;
import peaceinfotech.malegaonbazar.User.GetData.GetNearbyPlacesData;
import peaceinfotech.malegaonbazar.User.UI.SearchLocation;
import peaceinfotech.malegaonbazar.User.UI.UserActivity;

public class FragmentOffers extends Fragment implements OnMapReadyCallback,LocationListener{

    GoogleMap mMap;

    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    ImageView searchLoc;
    private MapView mMapView;
    GoogleApiClient client;
    public static final int REQUEST_LOCATION_CODE = 99;
    Double orglatitude, orglongitude,sorglat,sorglog;
    int PROXIMITY = 1000;
    FusedLocationProviderClient mfusedLocationProviderClient;
    Boolean onMarkerclick = false;
    Boolean onSearchClick=false;
    Boolean onOffersClick=false;
    String click;
    Boolean mLocationPermissionGranted = false;
    Spinner spinner;
    ArrayList<String> categories;
    TextView tvlocation;
    Geocoder geocoder;
    List<Address> addresses;
    LinearLayout layup,laysearch,layoffers;
    Animation infoup,infodown;
    TextView tvtitle,tvvic,tvback,tvdirec,tvviewoffers,tvboffer;
    LatLng endlatlng,orglatlng;
    LatLng searchLatlng;
    boolean searchout=false;
    String destName;
    List<OffersListModel> offersList = new ArrayList<>();
    RecyclerView recyclerView;
    OffersListAdapter offersListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_offers, container, false);

        categories=new ArrayList<String>();

        mMapView = view.findViewById(R.id.mapView);
        searchLoc=view.findViewById(R.id.searchlocation);
        tvlocation=view.findViewById(R.id.tvlocation);
        layup=view.findViewById(R.id.layup);
        tvtitle=view.findViewById(R.id.tvtitle);
        tvvic=view.findViewById(R.id.tvvic);
        tvback=view.findViewById(R.id.tvback);
        tvdirec=view.findViewById(R.id.tvdirec);
        laysearch=view.findViewById(R.id.laysearch);
        tvviewoffers=view.findViewById(R.id.tvviewoffers);
        layoffers=view.findViewById(R.id.offersup);
        tvboffer=view.findViewById(R.id.tvboffer);
        recyclerView=view.findViewById(R.id.recycler_offers);
//        btsearch=view.findViewById(R.id.btsearch);
        initGoogleMap(savedInstanceState);

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        categories.add(0,"Select a Category");
        categories.add(1,"Restaurant");
        categories.add(2,"School");
        categories.add(3,"Hospital");

        spinner = (Spinner) view.findViewById(R.id.spin);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        laysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClick=true;
                startActivityForResult(new Intent(getContext(),SearchLocation.class),1);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                if(spinner.getSelectedItem() == "Select a Category")
                {

                }
                else{
                    if(spinner.getSelectedItem() == "Restaurant") {
                        mMap.clear();
                        if(onSearchClick&&searchout){
                            searchNewLocation(sorglat,sorglog);
                            click = "restaurant";
                            String url = getUrl(orglatitude, orglongitude, click);
                            Object dataTransfer[] = new Object[3];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = click;
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                            Toast.makeText(getActivity(), click, Toast.LENGTH_LONG).show();
                        }
                        else{
                            getLocation();
                            click = "restaurant";
                            String url = getUrl(orglatitude, orglongitude, click);
                            Object dataTransfer[] = new Object[3];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = click;
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                            Toast.makeText(getActivity(), click, Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(spinner.getSelectedItem() =="Hospital"){
                        mMap.clear();
                        if(onSearchClick&&searchout){
                            searchNewLocation(sorglat,sorglog);
                            click = "hospital";
                            String url = getUrl(orglatitude, orglongitude, click);
                            Object dataTransfer[] = new Object[3];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = click;
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                            Toast.makeText(getActivity(), click, Toast.LENGTH_LONG).show();
                        }
                        else{
                            getLocation();
                            click = "hospital";
                            String url = getUrl(orglatitude, orglongitude, click);
                            Object dataTransfer[] = new Object[3];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = click;
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                            Toast.makeText(getActivity(), click, Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(spinner.getSelectedItem() == "School"){
                        mMap.clear();
                        if(onSearchClick&&searchout){
                            searchNewLocation(sorglat,sorglog);
                            click = "school";
                            String url = getUrl(orglatitude, orglongitude, click);
                            Object dataTransfer[] = new Object[3];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = click;
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                            Toast.makeText(getActivity(), click, Toast.LENGTH_LONG).show();
                        }
                        else{
                            getLocation();
                            click = "school";
                            String url = getUrl(orglatitude, orglongitude, click);
                            Object dataTransfer[] = new Object[3];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = click;
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                            Toast.makeText(getActivity(), click, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tvviewoffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                infodown=AnimationUtils.loadAnimation(getActivity(),R.anim.down_info);
                layup.setVisibility(View.GONE);
                layup.setAnimation(infodown);

                infoup = AnimationUtils.loadAnimation(getActivity(), R.anim.up_info);
                layoffers.setVisibility(View.VISIBLE);
                layoffers.setAnimation(infoup);

                mMapView.setClickable(false);

                for(int i=0;i<5;i++){
                    offersList.add(new OffersListModel("Offers Title","Get 20% off on first purchase","ss"));
                }

                offersListAdapter = new OffersListAdapter(offersList,getActivity());
                recyclerView.setAdapter(offersListAdapter);
                offersListAdapter.notifyDataSetChanged();

            }
        });

        tvback.setOnClickListener(new View.OnClickListener() {
            @RequiresApi
            @Override
            public void onClick(View v) {
                infodown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_info);
                layup.setVisibility(View.GONE);
                layup.setAnimation(infodown);

                //chnaging the color
                spinner.setEnabled(true);
                laysearch.setEnabled(true);
                tvlocation.setTextColor(Color.parseColor("#000000"));

            }
        });

        tvdirec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orglatlng=new LatLng(orglatitude,orglongitude);
                Intent putIn=new Intent(getActivity(),DirectionActivity.class);
                putIn.putExtra("origin",orglatlng);
                putIn.putExtra("end",endlatlng);
                putIn.putExtra("name",destName);
                startActivity(putIn);
            }
        });

        tvboffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                infodown=AnimationUtils.loadAnimation(getActivity(),R.anim.down_info);
                layoffers.setVisibility(View.GONE);
                layoffers.setAnimation(infodown);

                infoup = AnimationUtils.loadAnimation(getActivity(), R.anim.up_info);
                layup.setVisibility(View.VISIBLE);
                layup.setAnimation(infoup);

            }
        });


        return view;
    }

    private void initGoogleMap(Bundle savedInstanceState) {

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
    }


    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder placeurl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json");

            placeurl.append("?location=" + latitude + "," + longitude);
            placeurl.append("&radius=" + PROXIMITY);
            placeurl.append("&type=" + nearbyPlace);
            placeurl.append("&sensor=true");
            placeurl.append("&key=" + "AIzaSyAEdRkdwVissmatsKvama28utF65K-4ZA8");
            return placeurl.toString();
    }

    private void getSearchedLocation(LatLng latLng) {
        MarkerOptions mo = new MarkerOptions();
        mo.position(latLng);
        mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(mo);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mfusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location clocation = (Location) task.getResult();
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(clocation.getLatitude(), clocation.getLongitude())));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(clocation.getLatitude(), clocation.getLongitude()));
                    markerOptions.title("Current Location");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    mMap.addMarker(markerOptions).showInfoWindow();

                    orglatitude = clocation.getLatitude();
                    orglongitude = clocation.getLongitude();

                    getGeocoder(orglatitude,orglongitude);

                } else {
                    Toast.makeText(getActivity(), "Current location not found check your gps", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }



    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) getActivity())
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) getActivity())
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap=googleMap;


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 800, 10, 0);

        if(onSearchClick&&searchout) {
            mMap.clear();
            searchNewLocation(sorglat,sorglog);
        }
        else{
            mMap.clear();
            getLocation();
        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                String title=marker.getTitle();
                String vicinity=marker.getSnippet();
                endlatlng=marker.getPosition();

                if(title.equals("Current Location")||title.equals("Searched Location")) {

                }
                else {
                    infoup = AnimationUtils.loadAnimation(getActivity(), R.anim.up_info);
                    layup.setVisibility(View.VISIBLE);
                    layup.setAnimation(infoup);
                    tvtitle.setText(title);
                    tvvic.setText(vicinity);
                    mMapView.setClickable(false);
                    destName = title;
                    onMarkerclick = true;

                    //change of color
                    spinner.setEnabled(false);
                    laysearch.setEnabled(false);
                    tvlocation.setTextColor(Color.parseColor("#CBCBCB"));
                }
                return true;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                searchout=true;
                Double lat=data.getDoubleExtra("lat",orglatitude);
                Double log=data.getDoubleExtra("log",orglongitude);
                searchLatlng=new LatLng(lat,log);
                mMap.clear();
                layup.setVisibility(View.GONE);
                spinner.setSelection(0);
                searchNewLocation(lat,log);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                mMap.clear();
                if(searchout){
                    spinner.setSelection(0);
                    searchNewLocation(sorglat,sorglog);
                }
                else{
                    spinner.setSelection(0);
                    getLocation();
                    searchout=false;
                }
            }
        }
    }

    public void getGeocoder(Double latitude, Double longitude){

        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses=geocoder.getFromLocation(latitude,longitude,1);

            String adress=addresses.get(0).getAddressLine(0);
            String area=addresses.get(0).getLocality();
            String city=addresses.get(0).getAdminArea();

            String fulladdress=adress+", "+area+", "+city+".";

            tvlocation.setText(fulladdress);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchNewLocation(Double lat,Double log){

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, log)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        if(orglatitude==lat&&orglongitude==log){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(lat, log));
            markerOptions.title("Current Location");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions).showInfoWindow();

            sorglat=lat;
            sorglog=log;
            getGeocoder(orglatitude,orglongitude);
        }
        else{

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(lat, log));
            markerOptions.title("Searched Location");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions).showInfoWindow();

            sorglat=lat;
            sorglog=log;
            orglatitude = lat;
            orglongitude = log;

            getGeocoder(orglatitude,orglongitude);

        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);

            // do this for each or your Spinner
            // You might consider using Bundle.putStringArray() instead
    }



    @Override
    public void onResume() {
        super.onResume();
        if(onSearchClick&&searchout){
            searchNewLocation(sorglat,sorglog);
        }
        else{
            getLocation();
        }
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Offers");

    }

    @Override
    public void onLocationChanged(Location location) {

        getLocation();
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

    public void onBackPressed() {
        if (layoffers.getVisibility()==View.VISIBLE||layup.getVisibility()==View.VISIBLE) {
            if(layoffers.getVisibility()==View.VISIBLE){
                layoffers.setVisibility(View.GONE);
            }
            else if(layup.getVisibility()==View.VISIBLE){
                layup.setVisibility(View.GONE);
            }
        }
        else {
            getActivity().finish();
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}
