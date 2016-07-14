package se.ericwenn.reseplaneraren.controller;

/**
 * Created by ericwenn on 7/14/16.
 */
interface ISearchDataManager {
    void setOrigin( ISearchField.Final origin);
    void removeOrigin();
    void setDestination( ISearchField.Final destination);
    void removeDestination();
}