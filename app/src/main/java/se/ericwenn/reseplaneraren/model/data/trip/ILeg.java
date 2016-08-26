package se.ericwenn.reseplaneraren.model.data.trip;

/**
 * Created by ericwenn on 7/30/16.
 */
public interface ILeg {

    String getName();
    Type getType();

    int getBackgroundColor();
    int getForegroundColor();

    String getID();

    boolean isAccessible();

    ITripStop getOrigin();
    ITripStop getDestination();

    enum Type {
        VASTTRAFIK,
        LONG_DISTANCE_TRAIN,
        REGIONAL_TRAIN,
        BUS,
        BOAT,
        TRAM,
        TAXI,
        WALK,
        BIKE,
        CAR
    }

}
