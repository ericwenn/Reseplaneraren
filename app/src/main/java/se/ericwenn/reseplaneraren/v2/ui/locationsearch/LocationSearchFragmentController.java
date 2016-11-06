package se.ericwenn.reseplaneraren.v2.ui.locationsearch;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.v2.SearchField;

/**
 * Created by ericwenn on 11/5/16.
 */
public interface LocationSearchFragmentController {
    void onLocationSelected(ILocation l, SearchField field);
}
