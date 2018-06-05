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
import android.widget.Toast;

import com.danik.smarthouse.R;
import com.danik.smarthouse.service.HouseService;
import com.danik.smarthouse.service.UserService;
import com.danik.smarthouse.service.impl.HouseServiceImpl;
import com.danik.smarthouse.service.impl.UserServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private UserService userService = new UserServiceImpl();
    private HouseService houseService = new HouseServiceImpl();

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

        houseUpdateLayout.setVisibility(View.INVISIBLE);
        userUpdateLayout.setVisibility(View.INVISIBLE);

        view.findViewById(R.id.bMenuUserUpdate).setOnClickListener(view1 -> {
            houseUpdateLayout.setVisibility(View.INVISIBLE);
            userUpdateLayout.setVisibility(View.VISIBLE);
        });
        view.findViewById(R.id.bMenuHouseUpdate).setOnClickListener(view1 -> {
            houseUpdateLayout.setVisibility(View.VISIBLE);
            userUpdateLayout.setVisibility(View.INVISIBLE);
        });

        bUpdateUser.setOnClickListener(view1 -> {
            try {
                userService.update(UserDetails.user.getId(),
                        etName.getText().toString(),
                        etMiddleName.getText().toString(),
                        etLastName.getText().toString(),
                        etEmail.getText().toString(),
                        UserDetails.user.getTemperature());
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
                        UserDetails.user.getHouse().getSerial()));
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
