package com.danik.smarthouse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.service.DeviceService;
import com.danik.smarthouse.service.impl.DeviceServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.List;
import java.util.Objects;

public class MyDevicesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private DeviceService deviceService = new DeviceServiceImpl();

    public MyDevicesFragment() {
    }

    public static MyDevicesFragment newInstance() {
        MyDevicesFragment fragment = new MyDevicesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_devices, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
//            changeFragment(R.id.my_devices_frame, view, NewDeviceFragment.newInstance());
//            Fragment fragment = new NewDeviceFragment();
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.my_devices_frame, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
            onButtonPressed(R.id.main_frame);
        });

        LinearLayout myDevicesLayout = view.findViewById(R.id.myDevicesLayout);
        List<Device> devices = deviceService.findAllByHouseId(UserDetails.user.getHouse().getId());
        Log.i("devices_size", String.valueOf(devices.size()));
        for (Device device : devices) {
            Log.e("device", device.toString());
            myDevicesLayout.addView(new OneDeviceMyDevicesFragment().setDevice(device).onCreateView(inflater, container, savedInstanceState));
        }
        return view;
    }

    public void onButtonPressed(Integer id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Integer id);
        }

    private void changeFragment(Integer id, View view, Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(id, fragment).commit();
    }

}
