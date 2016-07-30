package se.ericwenn.reseplaneraren.controller;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 7/14/16.
 */
public class SearchField implements ISearchField {

    private static final String TAG = "SearchField";
    private String searchTerm = null;
    private List<IFieldListener> listeners = new LinkedList<>();
    private ILocation finalValue = null;

    @Override
    public void setFinal(ILocation finalValue) {
        this.finalValue = finalValue;
        Log.d(TAG, "setFinal() called with: " + "finalValue = [" + finalValue + "]");
        for( IFieldListener l : listeners ) {
            l.onFinalChanged( finalValue );
        }
    }

    @Override
    public ILocation getFinal() {
        return finalValue;
    }

    @Override
    public void setSearchTerm(String searchTerm) {

        setFinal( null );
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
