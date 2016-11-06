package se.ericwenn.reseplaneraren.v2.ui;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.v2.SearchField;
import se.ericwenn.reseplaneraren.v2.ui.locationsearch.ILocationSearchFragment;
import se.ericwenn.reseplaneraren.v2.ui.locationsearch.LocationSearchFragmentController;
import se.ericwenn.reseplaneraren.v2.ui.locationsearch.LocationSearchFragmentFactory;
import se.ericwenn.reseplaneraren.v2.ui.search_bar.ISearchFragment;
import se.ericwenn.reseplaneraren.v2.ui.search_bar.SearchFragment;
import se.ericwenn.reseplaneraren.v2.ui.search_bar.SearchFragmentController;

public class MapActivity extends AppCompatActivity implements SearchFragmentController, LocationSearchFragmentController {
    private static final int SEARCH_BAR_FRAME = R.id.search_bar_frame;
    private static final int SEARCH_BAR_OUTERHEIGHT_DP = 192;
    private static final String TAG = "MapActivity";
    private ILocationSearchFragment mLocationSearchFragment;
    private BottomSheetBehavior<FrameLayout> mBottomSheetBehavior;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setupSearchBar();
        setupLocationSearchBottomSheet();
    }

    private void setupLocationSearchBottomSheet() {

        // Create a new locationSearchFragment
        mLocationSearchFragment = LocationSearchFragmentFactory.create();

        final FrameLayout bottomSheetFrame = (FrameLayout) findViewById(R.id.bottomsheet_frame);

        // Change height of layout to be below the search bar
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        Resources r = getResources();
        ViewGroup.LayoutParams param = bottomSheetFrame.getLayoutParams();
        param.height = height - (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SEARCH_BAR_OUTERHEIGHT_DP, r.getDisplayMetrics());
        bottomSheetFrame.setLayoutParams(param);




        // Put the fragment inside the frame in bottom sheet
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.bottomsheet_frame, (Fragment) mLocationSearchFragment, null);
        fragmentTransaction.commit();


        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetFrame);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d(TAG, "onStateChanged() called with: bottomSheet = [" + bottomSheet + "], newState = [" + newState + "]");
                if( newState == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetFrame.requestLayout();
                    bottomSheetFrame.invalidate();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }


    private void changeSearchTerm(String searchTerm, SearchField f) {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mLocationSearchFragment.changeSearchTerm(searchTerm, f);
    }


    private void setupSearchBar() {
        getSearchFragment();

    }

    private ISearchFragment getSearchFragment() {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("SearchBarFragment");
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add( SEARCH_BAR_FRAME, searchFragment, "SearchBarFragment");
            fragmentTransaction.commit();

        }
        return searchFragment;
    }



    @Override
    public void originChanged(String searchTerm) {
        Log.d(TAG, "originChanged() called with: " + "searchTerm = [" + searchTerm + "]");
        changeSearchTerm(searchTerm, SearchField.ORIGIN);
    }

    @Override
    public void destinationChanged(String searchTerm) {
        Log.d(TAG, "destinationChanged() called with: " + "searchTerm = [" + searchTerm + "]");
        changeSearchTerm(searchTerm, SearchField.DESTINATION);
    }

    @Override
    public void search(ILocation origin, ILocation destination) {
        Log.d(TAG, "search() called with: origin = [" + origin + "], destination = [" + destination + "]");
    }

    @Override
    public void onLocationSelected(ILocation l, SearchField field) {
        if( field == SearchField.ORIGIN) {
            getSearchFragment().setOriginLocation(l);
        } else {
            getSearchFragment().setDestinationLocation(l);
        }
        Log.d(TAG, "onLocationSelected() called with: " + "l = [" + l + "], field = [" + field + "]");
    }
}
