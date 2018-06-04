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
import com.danik.smarthouse.model.User;
import com.danik.smarthouse.service.UserService;
import com.danik.smarthouse.service.impl.UserServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

public class TemperatureFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private UserService userService = new UserServiceImpl();

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
        Button bSaveTemperature = view.findViewById(R.id.bSaveTemperature);
        bSaveTemperature.setOnClickListener(view1 -> {
            UserDetails.user.setTemperature(Float.parseFloat(etSaveTemperature.getText().toString()));
            User user = UserDetails.user;
            userService.update(user.getId(),
                    user.getName(),
                    user.getMiddleName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getTemperature());
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
