package se.ericwenn.reseplaneraren.v2.ui.map;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.v2.SearchField;

/**
 * Created by ericwenn on 11/7/16.
 */

public interface MapFragmentController {
    void onLocationSelected(ILocation l, SearchField field);
}
