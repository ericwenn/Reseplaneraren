package se.ericwenn.reseplaneraren.controller;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ericwenn on 7/14/16.
 */
public class SearchField implements ISearchField {

    private static final String TAG = "SearchField";
    private String searchTerm = null;
    private List<IFieldListener> listeners = new LinkedList<>();

    @Override
    public void setFinal(Final finalValue) {

    }

    @Override
    public void setSearchTerm(String searchTerm) {

        Log.d(TAG, "setSearchTerm() called with: " + "searchTerm = [" + searchTerm + "]");
        this.searchTerm = searchTerm;

        for( IFieldListener l : listeners ) {
            l.onSearchTermChanged(searchTerm);
        }
    }

    @Override
    public String getSearchTerm() {
        return searchTerm;
    }

    @Override
    public void addFieldListener(IFieldListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeFieldListener(IFieldListener listener) {
        listeners.remove(listener);
    }
}
