package com.danik.smarthouse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

import java.util.Objects;

public class NewHouseFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private HouseService houseService = new HouseServiceImpl();

    public NewHouseFragment() {
    }

    public static NewHouseFragment newInstance(String param1, String param2) {
        NewHouseFragment fragment = new NewHouseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_house, container, false);
        EditText etFillInSerial = view.findViewById(R.id.etFillInSerial);
        Button bSendSerial = view.findViewById(R.id.bSendSerial);
        EditText etFillInName = view.findViewById(R.id.etFillInName);
        Button bSendName = view.findViewById(R.id.bSendName);
        ConstraintLayout addHouseSerialLayout = view.findViewById(R.id.addHouseSerialLayout);
        ConstraintLayout addHouseNameLayout = view.findViewById(R.id.addHouseNameLayout);
        addHouseNameLayout.setVisibility(View.INVISIBLE);
        bSendSerial.setOnClickListener(v -> {
            House house = houseService.createOrFindBySerial(etFillInSerial.getText().toString());
            UserDetails.user.setHouse(house);
            if (house.getName() == null) {
                addHouseNameLayout.setVisibility(View.VISIBLE);
            } else {
                Objects.requireNonNull(this.getActivity()).recreate();
            }
        });
        bSendName.setOnClickListener(v -> {
            House house = UserDetails.user.getHouse();
            UserDetails.user.setHouse(houseService.update(house.getId(), etFillInName.getText().toString(), house.getSerial()));
            Objects.requireNonNull(this.getActivity()).recreate();
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
