package se.ericwenn.reseplaneraren.ui.map;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.List;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.ui.LocationProvider;
import se.ericwenn.reseplaneraren.ui.MarkerProviderImpl;
import se.ericwenn.reseplaneraren.util.DataPromise;
import se.ericwenn.reseplaneraren.ui.map.sheet.ILocationBottomSheet;
import se.ericwenn.reseplaneraren.ui.map.sheet.LocationBottomSheetFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements IMapFragment, LocationProvider.OnLocationChangedListener {


    private static final String TAG = "MapFragment";


    // MainActivity
    private MapFragmentController mController;
    private BiMap<ILocation, Marker> mMarkers = HashBiMap.create();


    private GoogleMap mMap;
    private ILocationBottomSheet mBottomSheet;
    private MarkerProviderImpl mMarkerProvider;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mController = (MapFragmentController) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MapFragmentController");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMarkerProvider = new MarkerProviderImpl();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBottomSheet = LocationBottomSheetFactory.create();

        setupMap();

    }

    private void setupMap() {
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");


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

                mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        LatLng latLng = cameraPosition.target;

                        DataPromise<List<ILocation>> markerPromise = mMarkerProvider.getMarkers(latLng.latitude, latLng.longitude);
                        markerPromise.onResolve(new DataPromise.ResolvedHandler<List<ILocation>>() {
                            @Override
                            public void onResolve(List<ILocation> data) {
                                addMarkers( data );
                            }
                        });
                    }
                });

                /*
                final Location lastLocation = mLocationProvider.getLastLocation();
                setCenter( lastLocation.getLatitude(), lastLocation.getLongitude());
                */
                setZoom( 14f );






                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        ILocation loc = mMarkers.inverse().get(marker);

                        mBottomSheet.setLocation( loc );
                        mBottomSheet.show( getFragmentManager(), mBottomSheet.getTag() );

                        return true;
                    }
                });



            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
        mController = null;
    }


    @Override
    public void setMarkers(List<ILocation> markers) {
        mMarkers.clear();
        mMap.clear();
        addMarkers(markers);
    }

    @Override
    public void addMarkers(List<ILocation> markers) {
        // Necessary to not get a nullpointer TODO
        while( mMap == null) {

        }
        for( ILocation toMark : markers) {
            if( !mMarkers.containsKey(toMark)) {
                MarkerOptions mo = new MarkerOptions().position( new LatLng( toMark.getLatitude(), toMark.getLongitude() ) ).title( toMark.getName() );
                Marker m = mMap.addMarker( mo );
                mMarkers.put( toMark, m);

            }
        }
    }

    @Override
    public void removeMarkers(List<ILocation> markers) {
        for( ILocation toRemove : markers) {
            removeMarker( toRemove );
        }
    }

    @Override
    public void removeMarker( ILocation marker ) {
        Marker toRemove = mMarkers.remove( marker );
        toRemove.remove();
    }

    @Override
    public void setCenter(double latitude, double longitude) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        mMap.moveCamera( cameraUpdate );
    }

    @Override
    public void setZoom( float zlevel ) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(zlevel);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onLocationChanged(Location newLocation) {
        setCenter( newLocation.getLatitude(), newLocation.getLongitude());
    }
}
