package se.ericwenn.reseplaneraren.controller;

/**
 * Created by ericwenn on 7/14/16.
 */
public interface ISearchStateManager {
    enum State {
        INACTIVE(0),
        AUTOCOMPLETE(1),
        RESULT(2);

        private State(int order) {
            this.order = order;
        }
        private int order;
        public int order() {
            return this.order;
        }
    }

    void setState( State state );
    void setStateListener( StateListener l );
    void removeStateListener();


    interface StateListener {
        void onStateChange( State oldState, State newState );
    }
}
