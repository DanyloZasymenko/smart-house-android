package com.danik.smarthouse.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.model.Temperature;
import com.danik.smarthouse.service.AndroidService;
import com.danik.smarthouse.service.DeviceService;
import com.danik.smarthouse.service.HouseService;
import com.danik.smarthouse.service.impl.AndroidServiceImpl;
import com.danik.smarthouse.service.impl.DeviceServiceImpl;
import com.danik.smarthouse.service.impl.HouseServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class MainFragment extends Fragment {
    Handler mHandler = new Handler();
    private DeviceService deviceService = new DeviceServiceImpl();
    private HouseService houseService = new HouseServiceImpl();
    private AndroidService androidService = new AndroidServiceImpl();
    private OnFragmentInteractionListener mListener;
    private Thread mUiThread;
    private ScheduledExecutorService scheduler;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ScrollView scrollView = view.findViewById(R.id.scrollViewMainFragment);
        ConstraintLayout generalLayout = view.findViewById(R.id.generalLayout);
        ConstraintLayout notFindLayout = view.findViewById(R.id.notFindLayout);
        LinearLayout devicesLayout = view.findViewById(R.id.devicesLayout);
        ImageView ivTemperatureStatus = view.findViewById(R.id.ivTemperatureStatus);
        TextView tvTemperatureStatus = view.findViewById(R.id.tvTemperatureStatus);
        TextView tvUserTemperature = view.findViewById(R.id.tvUserTemperature);
        TextView tvCurrentTemperature = view.findViewById(R.id.tvCurrentTemperature);
        TextView tvCurrentHumidity = view.findViewById(R.id.tvCurrentHumidity);
        TextView tvUserHumidity = view.findViewById(R.id.tvUserHumidity);
        TextView tvHumidityStatus = view.findViewById(R.id.tvHumidityStatus);
        tvTemperatureStatus.setVisibility(View.INVISIBLE);
        ivTemperatureStatus.setVisibility(View.INVISIBLE);
        if (UserDetails.user != null) {
            tvUserTemperature.setText(UserDetails.user.getHouse().getTemperature().toString());
            tvUserHumidity.setText(UserDetails.user.getHouse().getHumidity().toString() + "%");
            try {
                Float temperature;
                if (UserDetails.temperatureCelsius)
                    temperature = Temperature.getInstance().getTemperatureC();
                else
                    temperature = Temperature.getInstance().getTemperatureF();
                Float humidity = Temperature.getInstance().getHumidity();
                if (temperature > UserDetails.user.getHouse().getTemperature()) {
                    ivTemperatureStatus.setImageResource(R.drawable.ic_thermometer_ice);
                    tvTemperatureStatus.setText(getString(R.string.temperature_down)+ " " + UserDetails.user.getHouse().getTemperature() + " ...");
                    tvTemperatureStatus.setVisibility(View.VISIBLE);
                    ivTemperatureStatus.setVisibility(View.VISIBLE);
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_CONDITIONING", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), true);
                    }
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_HEAT", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), false);
                    }
                } else {
                    ivTemperatureStatus.setImageResource(R.drawable.ic_thermometer_sun);
                    tvTemperatureStatus.setText(getString(R.string.temperature_up)+ " " + UserDetails.user.getHouse().getTemperature() + " ...");
                    tvTemperatureStatus.setVisibility(View.VISIBLE);
                    ivTemperatureStatus.setVisibility(View.VISIBLE);
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_HEAT", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), true);
                    }
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_CONDITIONING", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), false);
                    }
                }
                if (temperature.equals(UserDetails.user.getHouse().getTemperature())) {
                    ivTemperatureStatus.setVisibility(View.INVISIBLE);
                    tvTemperatureStatus.setText(getString(R.string.temperature_ok));
                    tvTemperatureStatus.setVisibility(View.VISIBLE);
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_CONDITIONING", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), false);
                    }
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_HEAT", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), false);
                    }
                }
                if (humidity > UserDetails.user.getHouse().getHumidity())
                    tvHumidityStatus.setText(getString(R.string.humidity_down));
                else
                    tvHumidityStatus.setText(getString(R.string.humidity_up));
                List<Device> devices = deviceService.findAllByHouseId(UserDetails.user.getHouse().getId());
                for (Device device : devices) {
                    devicesLayout.addView(new OneDeviceMainFragment().setDevice(device).onCreateView(inflater, container, savedInstanceState));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FloatingActionButton fab = view.findViewById(R.id.fab);
        FloatingActionButton fab2 = view.findViewById(R.id.fab2);
        fab.setOnClickListener(v -> {
            onButtonPressed(R.id.main_frame);
        });
        fab2.setOnClickListener(v -> getActivity().recreate());

        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        try {
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                Boolean firstActive = true;

                public void run() {
                    while (true) {
                        handler.post(() -> {
                            if (!firstActive && !houseService.getStatus()) {
                                generalLayout.setVisibility(View.INVISIBLE);
                                devicesLayout.setVisibility(View.INVISIBLE);
                                fab.setVisibility(View.INVISIBLE);
                                fab2.setVisibility(View.INVISIBLE);
                                notFindLayout.setVisibility(View.VISIBLE);
                                progressBar.animate().setDuration(1000).alpha(1).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        progressBar.setVisibility(View.VISIBLE);
                                    }
                                });
                            } else {
                                generalLayout.setVisibility(View.VISIBLE);
                                fab.setVisibility(View.VISIBLE);
                                fab2.setVisibility(View.VISIBLE);
                                devicesLayout.setVisibility(View.VISIBLE);
                                notFindLayout.setVisibility(View.INVISIBLE);
                                if (UserDetails.temperatureCelsius)
                                    tvCurrentTemperature.setText(Temperature.getInstance().getTemperatureC().toString());
                                else
                                    tvCurrentTemperature.setText(Temperature.getInstance().getTemperatureF().toString());
                                tvCurrentHumidity.setText(Temperature.getInstance().getHumidity().toString() + "%");
                                firstActive = true;
                            }
                        });
                        firstActive = false;
                        try {
                            Thread.sleep(15000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            new Thread(runnable).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public final void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != mUiThread) {
            mHandler.post(action);
        } else {
            action.run();
        }
    }

    @Override
    public void onDestroyView() {
//        scheduler.shutdown();
        super.onDestroyView();
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

}