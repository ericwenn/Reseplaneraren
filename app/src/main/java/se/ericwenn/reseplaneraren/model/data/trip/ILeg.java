package se.ericwenn.reseplaneraren.model.data.trip;

/**
 * Created by ericwenn on 7/30/16.
 */
public interface ILeg {

    String getName();
    String getShortName();
    Type getType();

    int getTravelMinutes();

    int getBackgroundColor() throws NullPointerException;
    int getForegroundColor() throws NullPointerException;

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
