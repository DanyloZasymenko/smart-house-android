package com.danik.smarthouse.fragment;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.TextView;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.model.Temperature;
import com.danik.smarthouse.model.enums.DeviceType;
import com.danik.smarthouse.service.AndroidService;
import com.danik.smarthouse.service.DeviceService;
import com.danik.smarthouse.service.HouseService;
import com.danik.smarthouse.service.impl.AndroidServiceImpl;
import com.danik.smarthouse.service.impl.DeviceServiceImpl;
import com.danik.smarthouse.service.impl.HouseServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.List;

public class MainFragment extends Fragment {
    //    final Handler mHandler = new Handler();
    TextView textView;
    private DeviceService deviceService = new DeviceServiceImpl();
    private HouseService houseService = new HouseServiceImpl();
    private AndroidService androidService = new AndroidServiceImpl();
    private OnFragmentInteractionListener mListener;
//    private Thread mUiThread;
//    private ScheduledExecutorService scheduler ;

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
        ConstraintLayout temperatureLayout = view.findViewById(R.id.temperatureLayout);
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
                for (Device device: deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_CONDITIONING", UserDetails.user.getHouse().getId())) {
                    androidService.changeActive(device.getId(), true);
                }
            } else {
                ivTemperatureStatus.setImageResource(R.drawable.ic_thermometer_sun);
                tvTemperatureStatus.setText("Температура піднімається до " + UserDetails.user.getTemperature() + " ...");
                for (Device device: deviceService.findAllByDeviceTypeAndHouseId("CLIMATE_HEAT", UserDetails.user.getHouse().getId())) {
                    androidService.changeActive(device.getId(), true);
                }
            }
            if (Temperature.getInstance().getTemperatureC().equals(UserDetails.user.getTemperature())) {
                ivTemperatureStatus.setVisibility(View.INVISIBLE);
                tvTemperatureStatus.setText("Температура в нормі");
            }
            List<Device> devices = deviceService.findAllByHouseId(UserDetails.user.getHouse().getId());
            for (Device device : devices) {
                devicesLayout.addView(new OneDeviceMainFragment().setDevice(device).onCreateView(inflater, container, savedInstanceState));
            }

        }
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(R.id.main_frame);
            }
        });
        FloatingActionButton fab2 = view.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().recreate();
            }
        });

//        scheduler= Executors.newSingleThreadScheduledExecutor();
//        textView = view.findViewById(R.id.onlinestatus);
//        scheduler.scheduleAtFixedRate(() -> {
//            textView.setText("online:["+houseService.getStatus() + "]");
//        }, 0, 5, TimeUnit.SECONDS);
        return view;
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