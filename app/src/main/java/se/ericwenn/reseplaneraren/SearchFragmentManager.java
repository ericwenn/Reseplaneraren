package se.ericwenn.reseplaneraren;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by ericwenn on 7/13/16.
 */
public class SearchFragmentManager {

    private static final String TAG = "SearchFragmentManager";
    private final FragmentManager manager;
    private final int containerViewId;
    private static SearchFragmentManager instance = null;

    public static SearchFragmentManager getInstance( final FragmentManager m, final @IdRes int containerViewId) {
        if(instance == null) {
            instance = new SearchFragmentManager(m, containerViewId);
        }
        return instance;
    }


    public SearchFragmentManager(FragmentManager manager, @IdRes int containerViewId) {
        this.manager = manager;
        this.containerViewId = containerViewId;
    }

    public void requestAutoComplete( String searchTerm ) {
        Log.d(TAG, "requestAutoComplete() called with: " + "searchTerm = [" + searchTerm + "]");
        AutoCompleteFragment acf;
        if(manager.findFragmentByTag("autoCompleteFragment") == null) {

            Log.d(TAG, "requestAutoComplete: autoCompleteFragment finns inte");
            acf = AutoCompleteFragment.newInstance( searchTerm );

            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(containerViewId, acf, "autoCompleteFragment");
            ft.addToBackStack(null);
            ft.commit();



        } else {
            Log.d(TAG, "requestAutoComplete: autoCompleteFragment finns");
            acf = (AutoCompleteFragment) manager.findFragmentByTag("autoCompleteFragment");
            acf.setSearchTerm( searchTerm );
        }


    }


}
