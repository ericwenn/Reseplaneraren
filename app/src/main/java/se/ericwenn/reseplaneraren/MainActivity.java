package se.ericwenn.reseplaneraren;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import se.ericwenn.reseplaneraren.controller.ISearchController;
import se.ericwenn.reseplaneraren.controller.ISearchStateManager;
import se.ericwenn.reseplaneraren.controller.SearchController;

public class MainActivity extends FragmentActivity {

    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener;
    private ISearchStateManager.StateListener onStateChangedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if( findViewById(R.id.fragment_container) == null ) {
            throw new RuntimeException("Fragment container does not exist in main");
        }


        // TODO Make a function instead to handle this DRY
        MapFragment acf = MapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, acf, "mapFragment").commit();

        createBackStackListener();
        createSearchStateListener();


    }

    @Override
    protected void onResume() {
        super.onResume();

        listenToBackStackChange();
        listenToStateChange();


    }

    @Override
    protected void onStop() {
        super.onStop();
        stopListeningToBackStackChange();
        stopListeningToStateChange();
    }


    private void createSearchStateListener() {
        if( onStateChangedListener == null) {

            onStateChangedListener = new ISearchStateManager.StateListener() {
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
                            startFragment( tag, newFragment, newState.order() > oldState.order());
                            break;
                        case AUTOCOMPLETE:
                            newFragment = AutoCompleteFragment.newInstance();
                            tag = "AutoCompleteFragment";
                            startFragment( tag, newFragment, newState.order() > oldState.order());
                            FrameLayout fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
                            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) fragmentContainer.getLayoutParams();
                            p.addRule(RelativeLayout.BELOW, R.id.search);
                            fragmentContainer.setLayoutParams(p);
                            break;
                        case RESULT:
                            newFragment = ResultFragment.newInstance();
                            tag = "ResultFragment";
                            startFragment(tag, newFragment, newState.order() > oldState.order());
                            break;
                    }
                    if(newFragment == null) {
                        throw new IllegalArgumentException("State ["+newState+"] doesnt have a fragment connected to it");
                    }




                }
            };
        }
    }




    private void listenToStateChange() {
        getSearchController().getSearchStateManager().setStateListener(onStateChangedListener);
    }

    private void stopListeningToStateChange() {
        getSearchController().getSearchStateManager().removeStateListener();
    }



    private void createBackStackListener() {
        if( onBackStackChangedListener == null) {

            onBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {

                @Override
                public void onBackStackChanged() {
                    if( getSupportFragmentManager().getBackStackEntryCount() == 0 ) {
                        SearchController.getInstance().getSearchStateManager().setState(ISearchStateManager.State.INACTIVE);
                    }
                }
            };
        }
    }

    private void listenToBackStackChange() {
        getSupportFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
    }
    private void stopListeningToBackStackChange() {
        getSupportFragmentManager().removeOnBackStackChangedListener(onBackStackChangedListener);
    }






    private void startFragment( String tag, Fragment fragment, boolean addToBackStack) {
        if(getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment, tag);

            if( addToBackStack ) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    private ISearchController getSearchController() {
        return SearchController.getInstance();
    }






}
