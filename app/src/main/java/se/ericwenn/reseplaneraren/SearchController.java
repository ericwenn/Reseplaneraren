package se.ericwenn.reseplaneraren;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ericwenn on 7/13/16.
 */
public class SearchController implements ISearchController {
    private static SearchController instance = null;
    public synchronized static SearchController getInstance() {
        if( instance == null) {
            instance = new SearchController();
        }
        return instance;
    }

    private HashMap<SearchField, String> searchStrings = new HashMap<SearchField,String>();

    private SearchController() {

    }

    private List<SearchTermChangeListener> searchTermChangeListeners = new LinkedList<>();

    @Override
    public void addOnSearchTermChangeListener(SearchTermChangeListener l) {
        searchTermChangeListeners.add(l);
    }

    @Override
    public void removeOnSearchTermChangeListener(SearchTermChangeListener l) {
        searchTermChangeListeners.remove(l);
    }

    @Override
    public void setSearchTerm(String searchTerm, SearchField field) {
        String oldText = searchStrings.get(field);
        searchStrings.put(field, searchTerm);

        for( SearchTermChangeListener l : searchTermChangeListeners) {
            l.onChange(oldText, searchTerm, field);
        }

    }

    @Override
    public void addOnResultChangeListener(ResultChangeListener l) {

    }

    @Override
    public void removeOnResultChangeListener(ResultChangeListener l) {

    }

    @Override
    public void setResult(String resultName) {

    }
}
