package com.danik.smarthouse.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.model.enums.DeviceType;
import com.danik.smarthouse.service.DeviceService;
import com.danik.smarthouse.service.HouseService;
import com.danik.smarthouse.service.UserService;
import com.danik.smarthouse.service.impl.DeviceServiceImpl;
import com.danik.smarthouse.service.impl.HouseServiceImpl;
import com.danik.smarthouse.service.impl.UserServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.List;
import java.util.Objects;

public class SettingsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private UserService userService = new UserServiceImpl();
    private HouseService houseService = new HouseServiceImpl();
    private DeviceService deviceService = new DeviceServiceImpl();

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        EditText etName = view.findViewById(R.id.etNameSettings);
        EditText etMiddleName = view.findViewById(R.id.etMiddleNameSettings);
        EditText etLastName = view.findViewById(R.id.etLastNameSettings);
        EditText etEmail = view.findViewById(R.id.etEmailSettings);
        Button bUpdateUser = view.findViewById(R.id.bUpdateUser);
        EditText etHouseNameSettings = view.findViewById(R.id.etHouseNameSettings);
        Button bUpdateHouse = view.findViewById(R.id.bUpdateHouse);
        EditText etDeviceName = view.findViewById(R.id.etConfigDeviceName);
        EditText etDevicePin = view.findViewById(R.id.etConfigDevicePin);
        Spinner spinnerAllDevices = view.findViewById(R.id.spinnerAllDevices);
        Spinner spinnerConfigDeviceType = view.findViewById(R.id.spinnerConfigDeviceType);
        Button bUpdateDevice = view.findViewById(R.id.bUpdateDevice);
        TextView tvNotFound = view.findViewById(R.id.tvNotFound);

        tvNotFound.setVisibility(View.INVISIBLE);

        try {
            etName.setText(UserDetails.user.getName());
            etMiddleName.setText(UserDetails.user.getMiddleName());
            etLastName.setText(UserDetails.user.getLastName());
            etEmail.setText(UserDetails.user.getEmail());
            etHouseNameSettings.setText(UserDetails.user.getHouse().getName());
        } catch (Exception e) {

        }

        ConstraintLayout houseUpdateLayout = view.findViewById(R.id.houseUpdateLayout);
        ConstraintLayout userUpdateLayout = view.findViewById(R.id.userUpdateLayout);
        ConstraintLayout configSelectDeviceLayout = view.findViewById(R.id.configSelectDeviceLayout);
        ConstraintLayout deviceUpdateLayout = view.findViewById(R.id.deviceUpdateLayout);

        houseUpdateLayout.setVisibility(View.INVISIBLE);
        userUpdateLayout.setVisibility(View.INVISIBLE);
        configSelectDeviceLayout.setVisibility(View.INVISIBLE);
        deviceUpdateLayout.setVisibility(View.INVISIBLE);

        view.findViewById(R.id.bMenuUserUpdate).setOnClickListener(view1 -> {
            houseUpdateLayout.setVisibility(View.INVISIBLE);
            configSelectDeviceLayout.setVisibility(View.INVISIBLE);
            deviceUpdateLayout.setVisibility(View.INVISIBLE);
            userUpdateLayout.setVisibility(View.VISIBLE);
        });
        view.findViewById(R.id.bMenuHouseUpdate).setOnClickListener(view1 -> {
            houseUpdateLayout.setVisibility(View.VISIBLE);
            userUpdateLayout.setVisibility(View.INVISIBLE);
            configSelectDeviceLayout.setVisibility(View.INVISIBLE);
            deviceUpdateLayout.setVisibility(View.INVISIBLE);
        });
        view.findViewById(R.id.bMenuDeviceUpdate).setOnClickListener(view1 -> {
            houseUpdateLayout.setVisibility(View.INVISIBLE);
            userUpdateLayout.setVisibility(View.INVISIBLE);
            configSelectDeviceLayout.setVisibility(View.VISIBLE);
            deviceUpdateLayout.setVisibility(View.INVISIBLE);
        });

        try {
            List<Device> devices = deviceService.findAllByHouseId(UserDetails.user.getHouse().getId());
            if (devices.size() == 0) {
                tvNotFound.setVisibility(View.VISIBLE);
                spinnerAllDevices.setVisibility(View.INVISIBLE);
            } else {
                tvNotFound.setVisibility(View.INVISIBLE);
                spinnerAllDevices.setVisibility(View.VISIBLE);
                ArrayAdapter<Device> adapter = new ArrayAdapter<Device>(Objects.requireNonNull(getContext()),
                        android.R.layout.simple_spinner_dropdown_item,
                        devices);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAllDevices.setAdapter(adapter);

                spinnerAllDevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Device device = (Device) parent.getItemAtPosition(position);
                        try {
                            deviceUpdateLayout.setVisibility(View.VISIBLE);
                            etDeviceName.setText(device.getName());
                            etDevicePin.setText(device.getPin().toString());
                            ArrayAdapter adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.deviceTypes, android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerConfigDeviceType.setAdapter(adapter);
                            spinnerConfigDeviceType.setSelection(device.getDeviceType().ordinal());
                            bUpdateDevice.setOnClickListener(view1 -> {
                                deviceService.update(device.getId(),
                                        etDeviceName.getText().toString(),
                                        Integer.parseInt(etDevicePin.getText().toString()),
                                        DeviceType.values()[spinnerConfigDeviceType.getSelectedItemPosition()].toString(),
                                        UserDetails.user.getHouse().getId());
                                getActivity().recreate();
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Вибачте, сталася помилка", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Вибачте, сталася помилка", Toast.LENGTH_SHORT).show();
        }
        bUpdateUser.setOnClickListener(view1 -> {
            try {
                userService.update(UserDetails.user.getId(),
                        etName.getText().toString(),
                        etMiddleName.getText().toString(),
                        etLastName.getText().toString(),
                        etEmail.getText().toString());
                UserDetails.logout();
                Objects.requireNonNull(getActivity()).recreate();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Вибачте, сталася помилка", Toast.LENGTH_SHORT).show();
            }
        });
        bUpdateHouse.setOnClickListener(view1 -> {
            try {
                UserDetails.user.setHouse(houseService.update(UserDetails.user.getHouse().getId(),
                        etHouseNameSettings.getText().toString(),
                        UserDetails.user.getHouse().getSerial(),
                        UserDetails.user.getHouse().getTemperature(),
                        UserDetails.user.getHouse().getHumidity()));
                getActivity().recreate();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Вибачте, сталася помилка", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
}
