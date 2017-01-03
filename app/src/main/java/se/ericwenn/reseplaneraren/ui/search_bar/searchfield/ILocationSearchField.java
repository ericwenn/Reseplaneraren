package se.ericwenn.reseplaneraren.ui.search_bar.searchfield;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 11/6/16.
 */

public interface ILocationSearchField {
    String getSearchTerm();
    boolean isFinal();
    ILocation getFinalLocation();
    void setFinal(ILocation finalLoc);
    void start();
    void stop();
}
