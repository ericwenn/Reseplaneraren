package se.ericwenn.reseplaneraren;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {


    private static final String TAG = "MapFragment";
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mapFragmentContainer, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }


}
