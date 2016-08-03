package se.ericwenn.reseplaneraren.ui.searchbar;

/**
 * Created by ericwenn on 8/2/16.
 */
public class SearchFragmentFactory {
    public static ISearchFragment create() {
        return SearchFragment.newInstance();
    }
}
