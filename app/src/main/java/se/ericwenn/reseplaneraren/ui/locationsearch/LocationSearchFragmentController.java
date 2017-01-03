package se.ericwenn.reseplaneraren.ui.locationsearch;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.SearchField;

/**
 * Created by ericwenn on 11/5/16.
 */
public interface LocationSearchFragmentController {
    void onLocationSelected(ILocation l, SearchField field);
}
