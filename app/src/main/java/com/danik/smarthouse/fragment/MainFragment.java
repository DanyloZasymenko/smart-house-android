package com.danik.smarthouse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danik.smarthouse.R;
import com.danik.smarthouse.model.Device;
import com.danik.smarthouse.model.Temperature;
import com.danik.smarthouse.service.DeviceService;
import com.danik.smarthouse.service.HouseService;
import com.danik.smarthouse.service.impl.DeviceServiceImpl;
import com.danik.smarthouse.service.impl.HouseServiceImpl;
import com.danik.smarthouse.service.utils.UserDetails;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
//    final Handler mHandler = new Handler();
    TextView textView;
    private DeviceService deviceService = new DeviceServiceImpl();
    private HouseService houseService = new HouseServiceImpl();
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
        List<Device> devices = deviceService.findAllByHouseId(UserDetails.user.getHouse().getId());
        for (Device device : devices) {
            devicesLayout.addView(new OneDeviceMainFragment().setDevice(device).onCreateView(inflater, container, savedInstanceState));
        }
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

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}