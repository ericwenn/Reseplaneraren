package se.ericwenn.reseplaneraren.model.data;

import java.io.Serializable;

/**
 * Created by ericwenn on 7/27/16.
 */
public interface ILocation extends Serializable {

    String getName();
    Double getLatitude();
    Double getLongitude();
    LocationType getLocationType();
    String getID();
    boolean isTrackSpecificLocation();


    enum LocationType {
        POI,
        ADRESS,
        STOP
    }
}
