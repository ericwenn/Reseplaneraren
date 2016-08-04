package se.ericwenn.reseplaneraren.ui.map.sheet;

import android.support.v4.app.FragmentManager;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 8/4/16.
 */
public interface ILocationBottomSheet {

    void setLocation( ILocation l );
    void show(FragmentManager fm, String tag);
    String getTag();


    interface LocationBottomSheetController {
        void travelFromHere( ILocation l);
        void travelToHere( ILocation l );
        void starLocation( ILocation l );
    }
}
