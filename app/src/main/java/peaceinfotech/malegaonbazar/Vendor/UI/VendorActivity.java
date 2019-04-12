package peaceinfotech.malegaonbazar.Vendor.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import peaceinfotech.malegaonbazar.StartUI.LoginActivity;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentAddOffers;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentChangePassword;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentServices;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentBusiness;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentHistory;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentOfferList;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentProfile;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentRequest;

public class VendorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



//    @Override
//    protected void onStart() {
//        super.onStart();
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        TextView wallet ;
//        wallet = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_request));
//        wallet.setGravity(Gravity.CENTER_VERTICAL);
//        wallet.setTypeface(null, Typeface.BOLD);
//        wallet.setTextColor(getResources().getColor(R.color.colorPrimary));
//        wallet.setText("3");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        menuItemsSelected(R.id.nav_vendor_profile);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to Exit");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vendor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        menuItemsSelected(id);
        return true;
    }

    private void menuItemsSelected(Integer item) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to Log-Out");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
                startActivity(new Intent(VendorActivity.this,LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();

        Fragment fragment=null;

        switch (item){
            case R.id.nav_business:
                fragment = new FragmentBusiness();
                break;
            case R.id.nav_vendor_profile:
                fragment=new FragmentProfile();
                break;
//            case R.id.nav_add_offers:
//                fragment=new FragmentAddOffers();
//                break;
            case R.id.nav_offers_list:
                fragment=new FragmentOfferList();
                break;
            case R.id.nav_add_services:
                fragment=new FragmentServices();
                break;
            case R.id.nav_request:
                fragment = new FragmentRequest();
                NavigationView navigationView = findViewById(R.id.nav_view);
                TextView wallet = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_request));
                wallet.setText("");
                break;
            case R.id.nav_history:
                fragment = new FragmentHistory();
                break;
            case R.id.nav_change_password:
                fragment = new FragmentChangePassword();
                break;
            case R.id.nav_logout:
                alertDialog.show();
        }

        if(fragment!=null){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_vendor,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

//    public GeoPoint getLocationFromAddress(String strAddress){
//
//        Geocoder coder = new Geocoder(this);
//        List<Address> address;
//        GeoPoint p1 = null;
//
//        try {
//            address = coder.getFromLocationName(strAddress,5);
//            if (address==null) {
//                return null;
//            }
//            Address location=address.get(0);
//            location.getLatitude();
//            location.getLongitude();
//
//            p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
//                    (double) (location.getLongitude() * 1E6));
//
//            return p1;
//        }
//    }


}
