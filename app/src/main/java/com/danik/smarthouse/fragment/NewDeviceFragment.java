package com.danik.smarthouse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.enums.DeviceType;
import com.danik.smarthouse.service.DeviceService;
import com.danik.smarthouse.service.impl.DeviceServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.Objects;

public class NewDeviceFragment extends Fragment {

    private DeviceService deviceService = new DeviceServiceImpl();

    private OnFragmentInteractionListener mListener;

    public NewDeviceFragment() {
    }

    public static NewDeviceFragment newInstance() {
        NewDeviceFragment fragment = new NewDeviceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_device, container, false);
        EditText etDeviceNameSave = view.findViewById(R.id.etDeviceNameSave);
        EditText etDevicePinSave = view.findViewById(R.id.etDevicePinSave);
        Spinner spinner = view.findViewById(R.id.spinnerDeviceType);
        Button bDeviceSave = view.findViewById(R.id.bDeviceSave);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.deviceTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        bDeviceSave.setOnClickListener(v -> {
            deviceService.save(etDeviceNameSave.getText().toString(),
                    Integer.parseInt(etDevicePinSave.getText().toString()),
                    DeviceType.values()[spinner.getSelectedItemPosition()].toString(),
                    UserDetails.user.getHouse().getId());
            this.getActivity().recreate();
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
