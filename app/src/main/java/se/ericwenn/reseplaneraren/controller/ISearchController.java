package se.ericwenn.reseplaneraren.controller;

/**
 * Created by ericwenn on 7/14/16.
 */
public interface ISearchController {

    ISearchStateManager getSearchStateManager();
    ISearchFieldManager getSearchFieldManager();
    ISearchDataManager getSearchDataManager();


}
