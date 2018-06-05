package com.danik.smarthouse;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.danik.smarthouse.activity.LoginActivity;
import com.danik.smarthouse.fragment.MainFragment;
import com.danik.smarthouse.fragment.MyDevicesFragment;
import com.danik.smarthouse.fragment.NewDeviceFragment;
import com.danik.smarthouse.fragment.NewHouseFragment;
import com.danik.smarthouse.fragment.NewUserDataFragment;
import com.danik.smarthouse.fragment.OneDeviceMyDevicesFragment;
import com.danik.smarthouse.fragment.SettingsFragment;
import com.danik.smarthouse.fragment.TemperatureFragment;
import com.danik.smarthouse.model.Temperature;
import com.danik.smarthouse.service.AndroidService;
import com.danik.smarthouse.service.impl.AndroidServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.Optional.ofNullable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnFragmentInteractionListener,
        MyDevicesFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        NewHouseFragment.OnFragmentInteractionListener,
        NewDeviceFragment.OnFragmentInteractionListener,
        OneDeviceMyDevicesFragment.OnFragmentInteractionListener,
        NewUserDataFragment.OnFragmentInteractionListener,
        TemperatureFragment.OnFragmentInteractionListener {

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private AndroidService androidService = new AndroidServiceImpl();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Log.i("user main act", UserDetails.user.toString());
        Log.i("access token main act", String.valueOf(ofNullable(UserDetails.accessToken).isPresent()));

        View headerView = navigationView.getHeaderView(0);
        TextView tvUserName = (TextView) headerView.findViewById(R.id.tvUserName);
        TextView tvHouseName = (TextView) headerView.findViewById(R.id.tvHouseName);

        if (UserDetails.user == null) {
            MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (UserDetails.user.getHouse() == null) {
            changeFragment(R.id.main_frame, NewHouseFragment.newInstance("", ""));
        } else {
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    Temperature.getInstance().setValues(androidService.getTemperature());
                    Log.i("temperature", Temperature.getInstance().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 0, 10, TimeUnit.SECONDS);
            changeFragment(R.id.main_frame, MainFragment.newInstance());
            tvUserName.setText(UserDetails.user.getName() + " " + UserDetails.user.getMiddleName() + " " + UserDetails.user.getLastName());
            tvHouseName.setText(UserDetails.user.getHouse().getName());
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.recreate();
//            int count = getFragmentManager().getBackStackEntryCount();
//
//            if (count == 0) {
//                super.onBackPressed();
//            } else {
//                getFragmentManager().popBackStack();
//            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            changeFragment(R.id.main_frame, TemperatureFragment.newInstance());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            changeFragment(R.id.main_frame, MainFragment.newInstance());
//        } else if (id == R.id.nav_my_devices) {
//            changeFragment(R.id.main_frame, MyDevicesFragment.newInstance());
        } else if (id == R.id.nav_settings) {
            changeFragment(R.id.main_frame, SettingsFragment.newInstance());
        } else if (id == R.id.nav_exit) {
            UserDetails.logout();
            this.recreate();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Integer id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(id, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(Integer id) {
        changeFragment(id, NewDeviceFragment.newInstance());
    }

    public void moveToNewUserData() {
        Log.i("move", "in move");
        changeFragment(R.id.main_frame, NewUserDataFragment.newInstance());
    }


}
