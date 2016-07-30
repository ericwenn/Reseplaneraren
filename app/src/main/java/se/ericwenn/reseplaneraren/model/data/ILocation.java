package se.ericwenn.reseplaneraren.model.data;

/**
 * Created by ericwenn on 7/27/16.
 */
public interface ILocation {

    String getName();
    Double getLatitude();
    Double getLongitude();
    LocationType getLocationType();
    String getID();


    enum LocationType {
        POI,
        ADRESS,
        STOP
    }
}
