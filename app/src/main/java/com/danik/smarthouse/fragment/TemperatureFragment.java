package com.danik.smarthouse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.House;
import com.danik.smarthouse.service.HouseService;
import com.danik.smarthouse.service.impl.HouseServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

public class TemperatureFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private HouseService houseService = new HouseServiceImpl();

    public TemperatureFragment() {
    }

    public static TemperatureFragment newInstance() {
        return new TemperatureFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);
        EditText etSaveTemperature = view.findViewById(R.id.etSaveTemperature);
        EditText etSaveHumidity = view.findViewById(R.id.etSaveHumidity);
        Button bSaveTemperature = view.findViewById(R.id.bSaveTemperature);

        try {
            etSaveTemperature.setText(UserDetails.user.getHouse().getTemperature().toString());
            etSaveHumidity.setText(UserDetails.user.getHouse().getHumidity().toString());
        } catch (Exception e) {

        }

        bSaveTemperature.setOnClickListener(view1 -> {
            UserDetails.user.getHouse().setTemperature(Float.parseFloat(etSaveTemperature.getText().toString()));
            UserDetails.user.getHouse().setHumidity(Float.parseFloat(etSaveHumidity.getText().toString()));
            House house = UserDetails.user.getHouse();
            houseService.update(house.getId(),
                    house.getName(),
                    house.getSerial(),
                    house.getTemperature(),
                    house.getHumidity());
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
