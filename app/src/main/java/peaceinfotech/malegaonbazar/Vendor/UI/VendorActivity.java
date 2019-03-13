package peaceinfotech.malegaonbazar.Vendor.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import peaceinfotech.malegaonbazar.StartUI.LoginActivity;
import peaceinfotech.malegaonbazar.R;
import peaceinfotech.malegaonbazar.SaveSharedPreference;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentAddOffers;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentOfferList;
import peaceinfotech.malegaonbazar.Vendor.Fragment.FragmentProfile;

public class VendorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vendor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

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
            case R.id.nav_vendor_profile:
                fragment=new FragmentProfile();
                break;
            case R.id.nav_add_offers:
                fragment=new FragmentAddOffers();
                break;
            case R.id.nav_offers_list:
                fragment=new FragmentOfferList();
                break;
            case R.id.nav_logout:
                alertDialog.show();
        }

        if(fragment!=null){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_user,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}