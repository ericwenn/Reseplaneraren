package se.ericwenn.reseplaneraren.controller;

/**
 * Created by ericwenn on 7/14/16.
 */
public interface ISearchFieldManager {
    void setActiveField( ISearchField field );
    void removeActiveField();
    ISearchField getActiveField();

    void addActiveSearchFieldChangeListener( IActiveSearchFieldChangeListener l);
    void removeActiveSearchFieldChangeListener( IActiveSearchFieldChangeListener l);
    ISearchField getOriginField();
    ISearchField getDestinationField();

    interface IActiveSearchFieldChangeListener {
        void onChange( ISearchField oldField, ISearchField newField);
    }
}
