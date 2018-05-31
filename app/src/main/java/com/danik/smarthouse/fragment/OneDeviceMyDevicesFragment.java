package com.danik.smarthouse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.Device;

public class OneDeviceMyDevicesFragment extends Fragment {

    private Device device;

    private OnFragmentInteractionListener mListener;

    public OneDeviceMyDevicesFragment() {
    }

    public static OneDeviceMyDevicesFragment newInstance(String param1, String param2) {
        OneDeviceMyDevicesFragment fragment = new OneDeviceMyDevicesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_device_my_devices, container, false);
        ((TextView) view.findViewById(R.id.tvDeviceNameMyDevices)).setText(device.getName());
        ImageView ivDeviceIcon = view.findViewById(R.id.ivDeviceIconMyDevices);
        Button bDeviceSettings = view.findViewById(R.id.bDeviceSettings);
        switch (device.getDeviceType()) {
            case CLIMATE_HEAT:
                ivDeviceIcon.setImageResource(R.drawable.ic_climate);
                break;
            case CLIMATE_CONDITIONING:
                ivDeviceIcon.setImageResource(R.drawable.ic_climate);
                break;
            case LIGHT:
                ivDeviceIcon.setImageResource(R.drawable.ic_lamp_bulb);
                break;
            default:
                break;
        }
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public Device getDevice() {
        return device;
    }

    public OneDeviceMyDevicesFragment setDevice(Device device) {
        this.device = device;
        return this;
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
