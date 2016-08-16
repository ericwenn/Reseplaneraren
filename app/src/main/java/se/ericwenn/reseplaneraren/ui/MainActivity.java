package se.ericwenn.reseplaneraren.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.ITrip;
import se.ericwenn.reseplaneraren.model.data.VasttrafikAPIBridge;
import se.ericwenn.reseplaneraren.ui.locationsearch.ILocationSearchFragment;
import se.ericwenn.reseplaneraren.ui.locationsearch.LocationSearchFragmentFactory;
import se.ericwenn.reseplaneraren.ui.map.IMapFragment;
import se.ericwenn.reseplaneraren.ui.map.MapFragmentFactory;
import se.ericwenn.reseplaneraren.ui.result.ITripSearchFragment;
import se.ericwenn.reseplaneraren.ui.result.TripSearchFragmentFactory;
import se.ericwenn.reseplaneraren.ui.searchbar.ISearchFragment;
import se.ericwenn.reseplaneraren.util.DataPromise;

public class MainActivity extends FragmentActivity implements
        FragmentController {

    private static final String TAG = "MainActivity";
    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener;

    private FragmentSwitcher mFragmentSwitcher;
    private ISearchFragment mSearchFragment;
    private ILocationSearchFragment mLocationSearchFragment;
    private IMapFragment mMapFragment;
    private ITripSearchFragment mTripSearchFragment;

    private BottomSheetBehavior mBottomSheetBehavior;




    private ILocation mOrigin = null;
    private ILocation mDestination = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if( findViewById(R.id.fragment_container) == null ) {
            throw new RuntimeException("Fragment container does not exist in main");
        }

        mFragmentSwitcher = new FragmentSwitcher( R.id.fragment_container );
        mSearchFragment = (ISearchFragment) getSupportFragmentManager().findFragmentById(R.id.search);
        mLocationSearchFragment = LocationSearchFragmentFactory.create();
        mMapFragment = MapFragmentFactory.create();
        mTripSearchFragment = TripSearchFragmentFactory.create();


        final FrameLayout bottomSheetFrame = (FrameLayout) findViewById(R.id.bottomsheet_frame);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.bottomsheet_frame, (Fragment) mLocationSearchFragment, null);
        fragmentTransaction.commit();


        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFrame);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if( newState == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetFrame.requestLayout();
                    bottomSheetFrame.invalidate();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        mFragmentSwitcher.showFragment( (Fragment) mMapFragment );


        DataPromise<List<ILocation>> promise = VasttrafikAPIBridge.getInstance().findNearbyStops( 57.6906366, 11.9871087, 20000);
        promise.onResolve(new DataPromise.ResolvedHandler<List<ILocation>>() {
            @Override
            public void onResolve(List<ILocation> data) {
                mMapFragment.addMarkers( data );
            }
        });
        promise.onReject(new DataPromise.RejectedHandler<List<ILocation>>() {
            @Override
            public void onReject(Exception e) {
                DataPromise<List<ILocation>> promise = VasttrafikAPIBridge.getInstance().findNearbyStops( 57.6906366, 11.9871087, 2000);
                promise.onResolve(new DataPromise.ResolvedHandler<List<ILocation>>() {
                    @Override
                    public void onResolve(List<ILocation> data) {
                        mMapFragment.addMarkers( data );
                    }
                });
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();





    }

    @Override
    protected void onStop() {
        super.onStop();
    }



















    @Override
    public void onSearchTermChanged(String searchTerm, Field f) {
        Log.d(TAG, "onSearchTermChanged() called with: " + "searchTerm = [" + searchTerm + "], f = [" + f + "]");

        updateCurrentLocation(null, f);

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //mFragmentSwitcher.showFragment((Fragment) mLocationSearchFragment);
        mLocationSearchFragment.changeSearchTerm( searchTerm, f );

    }

    @Override
    public void onLocationSelected(ILocation l, Field f) {
        Log.d(TAG, "onLocationSelected() called with: " + "l = [" + l + "], f = [" + f + "]");
        updateCurrentLocation(l, f);
        if( f == Field.ORIGIN ) {
            mSearchFragment.setOriginLocation(l);
        } else {
            mSearchFragment.setDestinationLocation(l);
        }

        if( mOrigin != null && mDestination != null) {
            Log.d(TAG, "onLocationSelected: Both origin and destination is set, starting search");
            mFragmentSwitcher.showFragment((Fragment) mTripSearchFragment);
            mTripSearchFragment.changeRoute( mOrigin, mDestination );
            mBottomSheetBehavior.setState( BottomSheetBehavior.STATE_COLLAPSED );
        } else if( f == Field.ORIGIN ) {
            mSearchFragment.focusField( Field.DESTINATION );
        }

    }

    private void updateCurrentLocation( ILocation l, Field f) {
        if( f == Field.ORIGIN ) {
            mOrigin = l;
        } else {
            mDestination = l;
        }
    }

    @Override
    public void onTripSelected(ITrip t) {
        Log.d(TAG, "onTripSelected() called with: " + "t = [" + t + "]");
    }




    private class FragmentSwitcher {
        private int container;

        public FragmentSwitcher(int container) {
            this.container = container;
        }


        public void showFragment( Fragment f ) {

            String fragmentTag;

            if( f instanceof IMapFragment ) {
                fragmentTag = "MapFragment";
            } else if( f instanceof ILocationSearchFragment ) {
                fragmentTag = "LocationSearchFragment";
            } else if( f instanceof ITripSearchFragment ) {
                fragmentTag = "TripSearchFragment";
            } else {
                throw new IllegalArgumentException( f.toString() + " is needs to implement one of the fragment interfaces");
            }


            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if( getSupportFragmentManager().findFragmentByTag( fragmentTag ) == null ) {
                Log.d(TAG, "showFragment: Fragment is not in manager yet, adding");
                ft.replace(container, f, fragmentTag);
            } else {
                Log.d(TAG, "showFragment: Fragment is in manager, showing");
                ft.show(f);
            }

            if( getSupportFragmentManager().findFragmentByTag("MapFragment") != null && getSupportFragmentManager().findFragmentByTag("MapFragment").isVisible()) {
                ft.addToBackStack(null);
            }

            ft.commit();


        }

    }
}