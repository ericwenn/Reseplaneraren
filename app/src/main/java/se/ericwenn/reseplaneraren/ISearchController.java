package se.ericwenn.reseplaneraren;

/**
 * Created by ericwenn on 7/13/16.
 */
public interface ISearchController {

    enum SearchField {
        ORIGIN,
        DESTINATION
    }

    void addOnSearchTermChangeListener( SearchTermChangeListener l);
    void removeOnSearchTermChangeListener( SearchTermChangeListener l);

    void setSearchTerm( String searchTerm, SearchField f);

    void addOnResultChangeListener( ResultChangeListener l);
    void removeOnResultChangeListener( ResultChangeListener l);

    void setResult( String resultName );

    interface SearchTermChangeListener {
        void onChange( String oldSearchTerm, String newSearchTerm, SearchField f);
    }
    interface ResultChangeListener {
        void onChange( String oldResult, String newResult, SearchField f);
    }
}
