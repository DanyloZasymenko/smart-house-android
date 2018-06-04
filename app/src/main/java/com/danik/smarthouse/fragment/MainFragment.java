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
        if (UserDetails.user != null) {
            tvUserTemperature.setText(UserDetails.user.getTemperature().toString());
            String text = Temperature.getInstance().getTemperatureC().toString();
            Log.e("text", text);
            tvCurrentTemperature.setText(text);
            if (Temperature.getInstance().getTemperatureC() > UserDetails.user.getTemperature()) {
                ivTemperatureStatus.setImageResource(R.drawable.ic_thermometer_ice);
                tvTemperatureStatus.setText("Температура опускається до " + UserDetails.user.getTemperature() + " ...");
                for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_CONDITIONING", UserDetails.user.getHouse().getId())) {
                    androidService.changeActive(device.getId(), true);
                }
                for (Device device : deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_HEAT", UserDetails.user.getHouse().getId())) {
                    androidService.changeActive(device.getId(), false);
                }
            } else {
                ivTemperatureStatus.setImageResource(R.drawable.ic_thermometer_sun);
                tvTemperatureStatus.setText("Температура піднімається до " + UserDetails.user.getTemperature() + " ...");
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

        }
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Log.i("listen", "in fab");
            onButtonPressed(R.id.main_frame);
        });
//        FloatingActionButton fab2 = view.findViewById(R.id.fab2);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().recreate();
//            }
//        });

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
//        progressBar.setVisibility(true ? View.VISIBLE : View.GONE);
//        progressBar.animate().setDuration(1000).alpha(
//                true ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                progressBar.setVisibility(true ? View.VISIBLE : View.GONE);
//            }
//        });

         mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("=========================");
            }
        };

         runOnUiThread(new Runnable(){
             @Override
             public void run() {
                 System.out.println("0000000000000000000000000000");
             }
         });
// start copy
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            Boolean firstActive = true;
            public void run() {
                while(true){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("111111111111111111111111");
                            if (!firstActive && !houseService.getStatus()) {
                                generalLayout.setVisibility(View.INVISIBLE);
                                devicesLayout.setVisibility(View.INVISIBLE);
                                notFindLayout.setVisibility(View.VISIBLE);
                                progressBar.animate().setDuration(1000).alpha(1).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        progressBar.setVisibility(View.VISIBLE);
                                    }
                                });
                            }else{
                                generalLayout.setVisibility(View.VISIBLE);
                                devicesLayout.setVisibility(View.VISIBLE);
                                System.out.println("+++++++++++++++++++++++++++++");
                                notFindLayout.setVisibility(View.INVISIBLE);
                                tvCurrentTemperature.setText(Temperature.getInstance().getTemperatureC().toString());
                                firstActive=true;
                            }
                        }
                    });
                    firstActive = false;
                    try {
                        Thread.sleep(5000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
// end copy

            scheduler = Executors.newSingleThreadScheduledExecutor();
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                scheduler.scheduleAtFixedRate(() -> {
                        System.out.println("-------------------------");
                }, 0, 5, TimeUnit.SECONDS);

            }
        });
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