package se.ericwenn.reseplaneraren;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import se.ericwenn.reseplaneraren.controller.ISearchController;
import se.ericwenn.reseplaneraren.controller.ISearchStateManager;
import se.ericwenn.reseplaneraren.controller.SearchController;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( findViewById(R.id.fragment_container) == null ) {
            throw new RuntimeException("Fragment container does not exist in main");
        }

        MapFragment acf = MapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, acf, "mapFragment").commit();

    }




    @Override
    protected void onStart() {
        super.onStart();


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                if( getSupportFragmentManager().getBackStackEntryCount() == 0 ) {
                    SearchController.getInstance().getSearchStateManager().setState(ISearchStateManager.State.INACTIVE);
                }
            }
        });

        final ISearchController searchController = getSearchController();
        searchController.getSearchStateManager().setStateListener(new ISearchStateManager.StateListener() {
            public static final String TAG = "StateListener";

            @Override
            public void onStateChange(ISearchStateManager.State oldState, ISearchStateManager.State newState) {
                Log.d(TAG, "onStateChange() called with: " + "oldState = [" + oldState + "], newState = [" + newState + "]");
                Fragment newFragment = null;
                String tag = null;
                switch (newState) {
                    case INACTIVE:
                        newFragment = MapFragment.newInstance();
                        tag = "MapFragment";
                        break;
                    case AUTOCOMPLETE:
                        newFragment = AutoCompleteFragment.newInstance();
                        tag = "AutoCompleteFragment";
                        break;
                    case RESULT:
                        newFragment = AutoCompleteFragment.newInstance();
                        tag = "ResultFragment";
                        break;
                }
                if(newFragment == null) {
                    throw new IllegalArgumentException("State ["+newState+"] doesnt have a fragment connected to it");
                }

                if(getSupportFragmentManager().findFragmentByTag(tag) == null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, newFragment, tag);

                    if( newState.order() > oldState.order()) {
                        ft.addToBackStack(null);
                    }
                    ft.commit();
                }


            }
        });


    }




    private ISearchController getSearchController() {
        return SearchController.getInstance();
    }






}
