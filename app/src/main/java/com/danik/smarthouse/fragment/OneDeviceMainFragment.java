package com.danik.smarthouse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.service.AndroidService;
import com.danik.smarthouse.service.impl.AndroidServiceImpl;

public class OneDeviceMainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private AndroidService androidService = new AndroidServiceImpl();

    private Device device;

    public OneDeviceMainFragment() {
    }

    public Device getDevice() {
        return device;
    }

    public OneDeviceMainFragment setDevice(Device device) {
        this.device = device;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_device_main, container, false);
        ((TextView) view.findViewById(R.id.tvDeviceNameMain)).setText(device.getName());
        Switch sDeviceActive = view.findViewById(R.id.sDeviceActive);
        sDeviceActive.setChecked(device.getActive());
        ImageView ivDeviceIcon = view.findViewById(R.id.ivDeviceIconMain);
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
        sDeviceActive.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i("change active", String.valueOf(isChecked));
            androidService.changeActive(device.getId(), isChecked);
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
