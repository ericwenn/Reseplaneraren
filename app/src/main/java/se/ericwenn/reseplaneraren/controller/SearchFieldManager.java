package se.ericwenn.reseplaneraren.controller;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 7/14/16.
 */
public class SearchFieldManager implements ISearchFieldManager {

    private static final String TAG = "SearchFieldManager";
    private static SearchFieldManager instance = null;

    private ISearchField activeField = null;
    private ISearchField originField = null;
    private ISearchField destinationField = null;

    private boolean originIsFinal = false;
    private boolean destinationIsFinal = false;

    private List<IActiveSearchFieldChangeListener> listeners = new LinkedList<>();

    public static synchronized SearchFieldManager getInstance() {
        if( instance == null) {
            instance = new SearchFieldManager();
        }
        return instance;
    }

    private SearchFieldManager() {
        Log.d(TAG, "SearchFieldManager init");
    }


    @Override
    public void setActiveField(ISearchField field) {
        Log.d(TAG, "setActiveField() called with: " + "field = [" + field + "]");
        SearchController.getInstance().getSearchStateManager().setState(ISearchStateManager.State.AUTOCOMPLETE);

        ISearchField oldField = activeField;
        activeField = field;

        for( IActiveSearchFieldChangeListener l : listeners ) {
            l.onChange(oldField, activeField);
        }
    }

    @Override
    public void removeActiveField() {
        Log.d(TAG, "removeActiveField() called with: " + "");

        ISearchField oldField = activeField;
        activeField = null;

        for( IActiveSearchFieldChangeListener l : listeners ) {
            l.onChange(oldField, activeField);
        }
    }

    @Override
    public ISearchField getActiveField() {
        return activeField;
    }

    @Override
    public void addActiveSearchFieldChangeListener(IActiveSearchFieldChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeActiveSearchFieldChangeListener(IActiveSearchFieldChangeListener l) {
        listeners.remove(l);
    }

    @Override
    public synchronized ISearchField getOriginField() {
        if( originField == null) {
            originField = new SearchField();

            originField.addFieldListener(new ISearchField.IFieldListener() {
                @Override
                public void onSearchTermChanged(String searchTerm) {

                }

                @Override
                public void onFinalChanged(ILocation finalValue) {
                    originIsFinal = finalValue != null;
                    SearchController.getInstance().getSearchDataManager().setOrigin(finalValue);
                    tryToSearchTrip();
                }
            });
        }

        return originField;
    }

    @Override
    public synchronized ISearchField getDestinationField() {
        if( destinationField == null) {
            destinationField = new SearchField();

            destinationField.addFieldListener(new ISearchField.IFieldListener() {
                @Override
                public void onSearchTermChanged(String searchTerm) {

                }

                @Override
                public void onFinalChanged(ILocation finalValue) {
                    destinationIsFinal = finalValue != null;
                    SearchController.getInstance().getSearchDataManager().setDestination(finalValue);

                    tryToSearchTrip();
                }
            });
        }

        return destinationField;
    }




    private void tryToSearchTrip() {
        Log.d(TAG, "tryToSearchTrip: Checking if both fields are final");
        if( originIsFinal && destinationIsFinal ) {
            Log.d(TAG, "tryToSearchTrip: Changing state");
            SearchController.getInstance().getSearchStateManager().setState(ISearchStateManager.State.RESULT);
        }
    }
}