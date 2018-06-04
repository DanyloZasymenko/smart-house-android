package com.danik.smarthouse.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.model.enums.DeviceType;
import com.danik.smarthouse.service.ClimateConfigService;
import com.danik.smarthouse.service.DeviceService;
import com.danik.smarthouse.service.LightConfigService;
import com.danik.smarthouse.service.impl.ClimateConfigServiceImpl;
import com.danik.smarthouse.service.impl.DeviceServiceImpl;
import com.danik.smarthouse.service.impl.LightConfigServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class MyDevicesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private DeviceService deviceService = new DeviceServiceImpl();
    private ClimateConfigService climateConfigService = new ClimateConfigServiceImpl();
    private LightConfigService lightConfigService = new LightConfigServiceImpl();

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

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_devices, container, false);

        List<Device> devices = deviceService.findAllByHouseId(UserDetails.user.getHouse().getId());
        Spinner spinnerSelectDevice = view.findViewById(R.id.spinnerSelectDevice);
        Button bSelectDevice = view.findViewById(R.id.bSelectDevice);
        ConstraintLayout addConfigLayout = view.findViewById(R.id.addConfigLayout);
        EditText etChooseTemperature = view.findViewById(R.id.etChooseTemperature);
        EditText etStartTime = view.findViewById(R.id.etStartTime);
        EditText etEndTime = view.findViewById(R.id.etEndTime);
        CheckBox cbIsActive = view.findViewById(R.id.cbIsActive);
        Button bSaveConfig = view.findViewById(R.id.bSaveConfig);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            onButtonPressed(R.id.main_frame);
        });
        addConfigLayout.setVisibility(View.INVISIBLE);
        if(devices.size() != 0) {
            ArrayAdapter adapter = new ArrayAdapter(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item);
            adapter.addAll(devices.stream().map(Device::getName).collect(toList()));
            spinnerSelectDevice.setAdapter(adapter);
            bSelectDevice.setOnClickListener(view1 -> {
                addConfigLayout.setVisibility(View.VISIBLE);
                if(devices.get(spinnerSelectDevice.getSelectedItemPosition()).getDeviceType().equals(DeviceType.LIGHT))
                    etChooseTemperature.setVisibility(View.INVISIBLE);
                else
                    etChooseTemperature.setVisibility(View.VISIBLE);
            });
            bSaveConfig.setOnClickListener(view1 -> {
                if(devices.get(spinnerSelectDevice.getSelectedItemPosition()).getDeviceType().equals(DeviceType.LIGHT)){
                        lightConfigService.save(
                                etStartTime.getText().toString(),
                                etEndTime.getText().toString(),
                                cbIsActive.isChecked());
                }
            });

        }
//        LinearLayout myDevicesLayout = view.findViewById(R.id.myDevicesLayout);
//        Log.i("devices_size", String.valueOf(devices.size()));
//        for (Device device : devices) {
//            Log.e("device", device.toString());
//            myDevicesLayout.addView(new OneDeviceMyDevicesFragment().setDevice(device).onCreateView(inflater, container, savedInstanceState));
//        }



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
