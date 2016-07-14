package se.ericwenn.reseplaneraren.controller;

import android.util.Log;

/**
 * Created by ericwenn on 7/14/16.
 */
public class SearchStateManager implements ISearchStateManager {
    private static final String TAG = "SearchStateManager";
    private State currentState = null;
    private StateListener listener = null;

    private static SearchStateManager instance = null;

    public static synchronized SearchStateManager getInstance() {
        if( instance == null) {
            instance = new SearchStateManager();
        }
        return instance;
    }


    private SearchStateManager() {}


    @Override
    public void setState(State state) {
        Log.d(TAG, "setState() called with: " + "state = [" + state + "]");
        State oldState = currentState == null ? State.INACTIVE : currentState;

        currentState = state;

        if( listener != null) {
            listener.onStateChange(oldState, state);
        }
    }

    @Override
    public void setStateListener(StateListener l) {
        listener = l;
    }

    @Override
    public void removeStateListener() {
        listener = null;
    }
}
