package se.ericwenn.reseplaneraren.v2.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.v2.ui.search_bar.SearchFragment;

public class MapActivity extends AppCompatActivity {
    private static final int SEARCH_BAR_FRAME = R.id.search_bar_frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setupSearchBar();
    }


    private void setupSearchBar() {


        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("SearchBarFragment");
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add( SEARCH_BAR_FRAME, searchFragment, "SearchBarFragment");
            fragmentTransaction.commit();

        }

    }

}
