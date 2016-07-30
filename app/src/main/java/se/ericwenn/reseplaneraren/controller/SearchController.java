package se.ericwenn.reseplaneraren.controller;

/**
 * Created by ericwenn on 7/14/16.
 */
public class SearchController implements ISearchController {

    private static SearchController instance = null;

    public static synchronized SearchController getInstance() {
        if( instance == null) {
            instance = new SearchController();
        }

        return instance;
    }

    private SearchController() {}

    @Override
    public ISearchStateManager getSearchStateManager() {
        return SearchStateManager.getInstance();
    }

    @Override
    public ISearchFieldManager getSearchFieldManager() {
        return SearchFieldManager.getInstance();
    }

    @Override
    public ISearchDataManager getSearchDataManager() {
        return SearchDataManager.getInstance();
    }
}
