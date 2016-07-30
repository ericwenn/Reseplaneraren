package se.ericwenn.reseplaneraren.controller;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 7/14/16.
 */
public interface ISearchField {
    void setFinal( ILocation finalValue );
    ILocation getFinal();

    void setSearchTerm( String searchTerm );
    String getSearchTerm();

    void addFieldListener( IFieldListener listener );
    void removeFieldListener( IFieldListener listener );
    interface IFieldListener {
        void onSearchTermChanged( String searchTerm );
        void onFinalChanged( ILocation finalValue );
    }

}
