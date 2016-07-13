package se.ericwenn.reseplaneraren;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class MainActivity extends FragmentActivity implements SearchFragment.OnSearchInteractionListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( findViewById(R.id.fragment_container) == null ) {
            throw new RuntimeException("Fragment container does not exist in main");
        }

        MapFragment acf = MapFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, acf).commit();
        findViewById(R.id.fragment_container).requestFocus();
    }


    @Override
    public void onFocusSearch(String searchTerm, SearchFragment.SearchField field) {

        AutoCompleteFragment acf;
        if(getSupportFragmentManager().findFragmentByTag("autoCompleteFragment") == null) {
            Log.d(TAG, "onFocusSearch: Is not open");
            acf = AutoCompleteFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, acf, "autoCompleteFragment");
            ft.addToBackStack(null);
            ft.commit();
        } else {
            acf = (AutoCompleteFragment) getSupportFragmentManager().findFragmentByTag("autoCompleteFragment");
        }

        onFreeSearch(searchTerm, field);


    }

    @Override
    public void onFreeSearch(String searchTerm, SearchFragment.SearchField field) {
        Log.d(TAG, "onFreeSearch() called with: " + "searchTerm = [" + searchTerm + "], field = [" + field + "]");
        acf.setSearchTerm( searchTerm );
    }


    private void
}
