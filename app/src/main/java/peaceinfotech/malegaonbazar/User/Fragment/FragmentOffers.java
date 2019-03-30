package peaceinfotech.malegaonbazar.User.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.User.Adapter.OffersListAdapter;
import peaceinfotech.malegaonbazar.User.Model.OffersListModel;
import peaceinfotech.malegaonbazar.User.UI.DirectionActivity;
import peaceinfotech.malegaonbazar.User.GetData.GetNearbyPlacesData;
import peaceinfotech.malegaonbazar.User.UI.SearchLocation;

public class FragmentOffers extends Fragment implements OnMapReadyCallback,LocationListener {

    GoogleMap mMap;

    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    ImageView searchLoc;
    private MapView mMapView;
    GoogleApiClient client;
    public static final int REQUEST_LOCATION_CODE = 99;
    Double orglatitude, orglongitude, sorglat, sorglog;
    int PROXIMITY = 1000;
    FusedLocationProviderClient mfusedLocationProviderClient;
    Boolean onMarkerclick = false;
    Boolean onSearchClick = false;
    Boolean onOffersClick = false;
    String click;
    Boolean mLocationPermissionGranted = false;
    Spinner spinner;
    ArrayList<String> categories;
    TextView tvlocation;
    Geocoder geocoder;
    List<Address> addresses;
    LinearLayout layup, laysearch, layoffers;
    RelativeLayout relayFav,relayOffers,relaySpin;
    Animation infoup, infodown;
    TextView tvtitle, tvvic, tvback, tvdirec, tvviewoffers, tvboffer,tvaddFav,tvremoveFav,tvCategories;
    LatLng endlatlng, orglatlng;
    LatLng searchLatlng;
    boolean searchout = false;
    String destName;
    List<OffersListModel> offersList = new ArrayList<>();
    RecyclerView recyclerView;
    OffersListAdapter offersListAdapter;
    ScrollView scrollView;
    RatingBar ratingBar;
    SearchableSpinner searchableSpinner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_offers, container, false);

        categories = new ArrayList<String>();

        mMapView = view.findViewById(R.id.mapView);
        searchLoc = view.findViewById(R.id.searchlocation);
        tvlocation = view.findViewById(R.id.tvlocation);
        layup = view.findViewById(R.id.layup);
        tvtitle = view.findViewById(R.id.tvtitle);
        tvvic = view.findViewById(R.id.tvvic);
        tvback = view.findViewById(R.id.tvback);
        tvdirec = view.findViewById(R.id.tvdirec);
        laysearch = view.findViewById(R.id.laysearch);
        tvviewoffers = view.findViewById(R.id.tvviewoffers);
        layoffers = view.findViewById(R.id.offersup);
        tvboffer = view.findViewById(R.id.tvboffer);
        recyclerView = view.findViewById(R.id.recycler_offers);
        scrollView=view.findViewById(R.id.scroll_lay);
        ratingBar=view.findViewById(R.id.rating_star);
        tvaddFav=view.findViewById(R.id.tv_add_fav);
        tvremoveFav=view.findViewById(R.id.tv_remove_fav);
        relayFav=view.findViewById(R.id.relay_fav);
        searchableSpinner=view.findViewById(R.id.search_spinner);
        relayOffers=view.findViewById(R.id.relay_offers);
        tvCategories=view.findViewById(R.id.tv_categories);
        relaySpin=view.findViewById(R.id.spin_lay);
        initGoogleMap(savedInstanceState);

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        categories.add(0,"Select a Category");
        categories.add(1, "Restaurant");
        categories.add(2, "School");
        categories.add(3, "Hospital");


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        laysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClick = true;
                startActivityForResult(new Intent(getContext(), SearchLocation.class), 1);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchableSpinner.setAdapter(adapter);

//        tvCategories.setText("Select a Category");
//        tvCategories.setVisibility(View.VISIBLE);

        searchableSpinner.setActivated(true);
        searchableSpinner.setSelectedItem(0);

        searchableSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {

                if (searchableSpinner.getSelectedItem() == "Restaurant") {
                    mMap.clear();
                        if (onSearchClick && searchout) {
                            searchNewLocation(sorglat, sorglog);
                            click = "restaurant";
                            String url = getUrl(orglatitude, orglongitude, click);
                            Object dataTransfer[] = new Object[3];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = click;
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                            Toast.makeText(getActivity(), click, Toast.LENGTH_LONG).show();
                            tvCategories.setText(searchableSpinner.getSelectedItem().toString());
                        } else {
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
                            tvCategories.setText(searchableSpinner.getSelectedItem().toString());
                        }
                    }
                    else if (searchableSpinner.getSelectedItem() == "Hospital") {
                        mMap.clear();
                        if (onSearchClick && searchout) {
                            searchNewLocation(sorglat, sorglog);
                            click = "hospital";
                            String url = getUrl(orglatitude, orglongitude, click);
                            Object dataTransfer[] = new Object[3];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = click;
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                            Toast.makeText(getActivity(), click, Toast.LENGTH_LONG).show();
                            tvCategories.setText(searchableSpinner.getSelectedItem().toString());
                        } else {
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
                            tvCategories.setText(searchableSpinner.getSelectedItem().toString());
                        }
                    }
                    else if (searchableSpinner.getSelectedItem() == "School") {
                        mMap.clear();
                        if (onSearchClick && searchout) {
                            searchNewLocation(sorglat, sorglog);
                            click = "school";
                            String url = getUrl(orglatitude, orglongitude, click);
                            Object dataTransfer[] = new Object[3];
                            dataTransfer[0] = mMap;
                            dataTransfer[1] = url;
                            dataTransfer[2] = click;
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(dataTransfer);
                            Toast.makeText(getActivity(), click, Toast.LENGTH_LONG).show();
                            tvCategories.setText(searchableSpinner.getSelectedItem().toString());
                        } else {
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
                            tvCategories.setText(searchableSpinner.getSelectedItem().toString());
                        }
                    }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        relayOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                infodown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_info);
                layup.setVisibility(View.GONE);
                layup.setAnimation(infodown);

                infoup = AnimationUtils.loadAnimation(getActivity(), R.anim.up_info);
                layoffers.setVisibility(View.VISIBLE);
                layoffers.setAnimation(infoup);

                mMapView.setClickable(false);

                for (int i = 0; i < 5; i++) {
                    offersList.add(new OffersListModel("Offers Title", "Get 20% off on first purchase", 20,"ss",500));
                }

                offersListAdapter = new OffersListAdapter(offersList, getActivity());
                recyclerView.setAdapter(offersListAdapter);
                offersListAdapter.notifyDataSetChanged();
            }
        });

        tvviewoffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                infodown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_info);
                layup.setVisibility(View.GONE);
                layup.setAnimation(infodown);

                infoup = AnimationUtils.loadAnimation(getActivity(), R.anim.up_info);
                layoffers.setVisibility(View.VISIBLE);
                layoffers.setAnimation(infoup);

                mMapView.setClickable(false);

                for (int i = 0; i < 5; i++) {
                    offersList.add(new OffersListModel("Offers Title", "Get 20% off on first purchase", 20," ",500));
                }

                offersListAdapter = new OffersListAdapter(offersList, getActivity());
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
                searchableSpinner.setVisibility(View.VISIBLE);
                tvCategories.setVisibility(View.GONE);
                laysearch.setEnabled(true);
                tvlocation.setTextColor(Color.parseColor("#000000"));

            }
        });

        tvdirec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orglatlng = new LatLng(orglatitude, orglongitude);
                Intent putIn = new Intent(getActivity(), DirectionActivity.class);
                putIn.putExtra("origin", orglatlng);
                putIn.putExtra("end", endlatlng);
                putIn.putExtra("name", destName);
                startActivity(putIn);
            }
        });

        tvboffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                infodown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_info);
                layoffers.setVisibility(View.GONE);
                layoffers.setAnimation(infodown);

                infoup = AnimationUtils.loadAnimation(getActivity(), R.anim.up_info);
                layup.setVisibility(View.VISIBLE);
                layup.setAnimation(infoup);

            }
        });

        relayFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvaddFav.getVisibility()==View.VISIBLE){
                    tvaddFav.setVisibility(View.GONE);
                    tvremoveFav.setVisibility(View.VISIBLE);
                }
                else if(tvremoveFav.getVisibility()==View.VISIBLE){
                    tvremoveFav.setVisibility(View.GONE);
                    tvaddFav.setVisibility(View.VISIBLE);
                }
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
                    Log.d("location",orglatitude+"/"+orglongitude);
                    getGeocoder(orglatitude, orglongitude);

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

        mMap = googleMap;




        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 800, 10, 0);

        if (onSearchClick && searchout) {
            mMap.clear();
            searchNewLocation(sorglat, sorglog);
        } else {
            mMap.clear();
            getLocation();
        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                String title = marker.getTitle();
                String vicinity = marker.getSnippet();
                endlatlng = marker.getPosition();


                if (title.equals("Current Location") || title.equals("Searched Location")) {

                } else {

                    scrollView.scrollTo(0,0);

                    infoup = AnimationUtils.loadAnimation(getActivity(), R.anim.up_info);
                    layup.setVisibility(View.VISIBLE);
                    layup.setAnimation(infoup);
                    tvtitle.setText(title);
                    tvvic.setText(vicinity);
                    mMapView.setClickable(false);
                    destName = title;
                    onMarkerclick = true;

                    ratingBar.setRating(4.5f);
                    searchableSpinner.setVisibility(View.GONE);
                    tvCategories.setVisibility(View.VISIBLE);
                    //change of color
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
            if (resultCode == Activity.RESULT_OK) {
                searchout = true;
                Double lat = data.getDoubleExtra("lat", orglatitude);
                Double log = data.getDoubleExtra("log", orglongitude);
                searchLatlng = new LatLng(lat, log);
                mMap.clear();
                layup.setVisibility(View.GONE);
                searchableSpinner.setSelectedItem(0);
                searchNewLocation(lat, log);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                mMap.clear();
                if (searchout) {
                    searchNewLocation(sorglat, sorglog);
                    searchableSpinner.setSelectedItem(0);
                } else {
                    getLocation();
                    searchableSpinner.setSelectedItem(0);
                    searchout = false;
                }
            }
        }
    }

    public void getGeocoder(Double latitude, Double longitude) {

        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            String adress = addresses.get(0).getAddressLine(0);
            String area = addresses.get(0).getLocality();
            String city = addresses.get(0).getAdminArea();

            String fulladdress = adress + ", " + area + ", " + city + ".";

            tvlocation.setText(fulladdress);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchNewLocation(Double lat, Double log) {

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, log)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        if (orglatitude == lat && orglongitude == log) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(lat, log));
            markerOptions.title("Current Location");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions).showInfoWindow();

            sorglat = lat;
            sorglog = log;
            getGeocoder(orglatitude, orglongitude);
        } else {

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(lat, log));
            markerOptions.title("Searched Location");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions).showInfoWindow();

            sorglat = lat;
            sorglog = log;
            orglatitude = lat;
            orglongitude = log;

            getGeocoder(orglatitude, orglongitude);

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

    }


    @Override
    public void onResume() {
        super.onResume();
        if (onSearchClick && searchout) {
            searchNewLocation(sorglat, sorglog);
        } else {
            getLocation();
        }
        mMapView.onResume();
        onBackPressed();
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

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (layoffers.getVisibility() == View.VISIBLE || layup.getVisibility() == View.VISIBLE) {
                        if (layoffers.getVisibility() == View.VISIBLE && layup.getVisibility() == View.GONE) {

                            infodown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_info);
                            layoffers.setVisibility(View.GONE);
                            layoffers.setAnimation(infodown);

                            infoup = AnimationUtils.loadAnimation(getActivity(), R.anim.up_info);
                            layup.setVisibility(View.VISIBLE);
                            layup.setAnimation(infoup);
                        } else if (layup.getVisibility() == View.VISIBLE) {

                            infodown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_info);
                            layup.setVisibility(View.GONE);
                            layup.setAnimation(infodown);

                            searchableSpinner.setVisibility(View.VISIBLE);
                            tvCategories.setVisibility(View.GONE);
                            laysearch.setEnabled(true);
                            tvlocation.setTextColor(Color.parseColor("#000000"));
                        }
                    } else {
                        getActivity().onBackPressed();
                    }
                    return true;
                }

                return false;
            }
        });
    }
}
