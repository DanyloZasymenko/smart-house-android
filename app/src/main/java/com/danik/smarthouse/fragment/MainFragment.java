package com.danik.smarthouse.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.AlertButtons;
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
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainFragment extends Fragment {
    Handler mHandler = new Handler();
    TextView textView;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ConstraintLayout generalLayout = view.findViewById(R.id.generalLayout);
        ConstraintLayout notFindLayout = view.findViewById(R.id.notFindLayout);
        LinearLayout devicesLayout = view.findViewById(R.id.devicesLayout);
        ImageView ivTemperatureStatus = view.findViewById(R.id.ivTemperatureStatus);
        TextView tvTemperatureStatus = view.findViewById(R.id.tvTemperatureStatus);
        TextView tvUserTemperature = view.findViewById(R.id.tvUserTemperature);
        TextView tvCurrentTemperature = view.findViewById(R.id.tvCurrentTemperature);
        tvTemperatureStatus.setVisibility(View.INVISIBLE);
        ivTemperatureStatus.setVisibility(View.INVISIBLE);
        if (UserDetails.user != null) {
            tvUserTemperature.setText(UserDetails.user.getTemperature().toString());
            try {
                String text = Temperature.getInstance().getTemperatureC().toString();
                Log.e("text", text);
                tvCurrentTemperature.setText(text);
                if (Temperature.getInstance().getTemperatureC() > UserDetails.user.getTemperature()) {
                    ivTemperatureStatus.setImageResource(R.drawable.ic_thermometer_ice);
                    tvTemperatureStatus.setText("Температура опускається до " + UserDetails.user.getTemperature() + " ...");
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
                    tvTemperatureStatus.setText("Температура піднімається до " + UserDetails.user.getTemperature() + " ...");
                    tvTemperatureStatus.setVisibility(View.VISIBLE);
                    ivTemperatureStatus.setVisibility(View.VISIBLE);
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_HEAT", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), true);
                    }
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_CONDITIONING", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), false);
                    }
                }
                if (Temperature.getInstance().getTemperatureC().equals(UserDetails.user.getTemperature())) {
                    ivTemperatureStatus.setVisibility(View.INVISIBLE);
                    tvTemperatureStatus.setText("Температура в нормі");
                    tvTemperatureStatus.setVisibility(View.VISIBLE);
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_CONDITIONING", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), false);
                    }
                    for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_HEAT", UserDetails.user.getHouse().getId())) {
                        androidService.changeActive(device.getId(), false);
                    }
                }
                List<Device> devices = deviceService.findAllByHouseId(UserDetails.user.getHouse().getId());
                for (Device device : devices) {
                    devicesLayout.addView(new OneDeviceMainFragment().setDevice(device).onCreateView(inflater, container, savedInstanceState));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        FloatingActionButton fab = view.findViewById(R.id.fab);
        FloatingActionButton fab2 = view.findViewById(R.id.fab2);
        fab.setOnClickListener(v -> {
            Log.i("listen", "in fab");
            onButtonPressed(R.id.main_frame);
        });
        fab2.setOnClickListener(v -> getActivity().recreate());

        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("=========================");
            }
        };

        runOnUiThread(() -> System.out.println("0000000000000000000000000000"));
// start copy
        try {
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                Boolean firstActive = true;

                public void run() {
                    while (true) {
                        handler.post(() -> {
                            System.out.println("111111111111111111111111");
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
                                System.out.println("+++++++++++++++++++++++++++++");
                                notFindLayout.setVisibility(View.INVISIBLE);
                                tvCurrentTemperature.setText(Temperature.getInstance().getTemperatureC().toString());
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
        }catch (Exception e){
            e.printStackTrace();
        }
// end copy

        scheduler = Executors.newSingleThreadScheduledExecutor();
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> scheduler.scheduleAtFixedRate(() -> {
            try {
                AlertButtons.getInstance().setValues(androidService.checkAlert());
                Log.i("alert", AlertButtons.getInstance().toString());
                if(AlertButtons.getInstance().getFire()){
                    Toast.makeText(getContext(), "Спрацювала пожежна сигналізація!", Toast.LENGTH_SHORT).show();
                }
                if(AlertButtons.getInstance().getPolice()){
                    Toast.makeText(getContext(), "Спрацювала тривожна кнопка!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS));
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

//    public final void runOnUiThread(Runnable action) {
//        while (true) {
//            if (Thread.currentThread() != mUiThread) {
//                mHandler.post(action);
//            } else {
//                action.run();
//            }
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

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