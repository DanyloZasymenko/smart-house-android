package com.danik.smarthouse.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.danik.smarthouse.R;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.Locale;
import java.util.Objects;

public class SettingsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

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
        view.findViewById(R.id.ibChangeLanguage).setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            builder.setTitle(R.string.choose_language);
            builder.setPositiveButton("Українська", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                updateResources(getActivity(), "uk");
            });
            builder.setNegativeButton("English", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                updateResources(getActivity(), "en");
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        view.findViewById(R.id.ibChangeTemperatureMeasurement).setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            builder.setTitle(R.string.choose_temperature_measurement);
            builder.setPositiveButton(getString(R.string.celsius), (dialogInterface, i) -> {
                dialogInterface.dismiss();
                UserDetails.temperatureCelsius = true;
                this.getActivity().recreate();
            });
            builder.setNegativeButton(getString(R.string.fahrenheit), (dialogInterface, i) -> {
                dialogInterface.dismiss();
                UserDetails.temperatureCelsius = false;
                this.getActivity().recreate();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        return view;
    }

    private boolean updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        this.getActivity().recreate();

        return true;
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
