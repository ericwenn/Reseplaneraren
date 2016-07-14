package se.ericwenn.reseplaneraren.controller;

/**
 * Created by ericwenn on 7/14/16.
 */
public interface ISearchField {
    void setFinal( Final finalValue );
    void setSearchTerm( String searchTerm );
    String getSearchTerm();

    void addFieldListener( IFieldListener listener );
    void removeFieldListener( IFieldListener listener );
    interface IFieldListener {
        void onSearchTermChanged( String searchTerm );
        void onFinalChanged( Final finalValue );
    }

    interface Final {

    }
}
