package se.ericwenn.reseplaneraren;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

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

        final SearchController sc = SearchController.getInstance();
        final SearchFragmentManager sfm = SearchFragmentManager.getInstance( getSupportFragmentManager(), R.id.fragment_container);

        sc.addOnSearchTermChangeListener(new ISearchController.SearchTermChangeListener() {
            @Override
            public void onChange(String oldSearchTerm, String newSearchTerm, ISearchController.SearchField field) {

                Log.d(TAG, "onChange() called with: " + "oldSearchTerm = [" + oldSearchTerm + "], newSearchTerm = [" + newSearchTerm + "], field = [" + field + "]");
                // Search is active
                if( newSearchTerm != null ) {

                    // start autoCompleteFragment if not started
                    sfm.requestAutoComplete(newSearchTerm);

                }
            }
        });
    }






}
