package se.ericwenn.reseplaneraren.model.data.trip;

import java.util.Date;

/**
 * Created by ericwenn on 8/24/16.
 */
public interface ITripStop {
    Date getTime();
    String getName();
    String getTrack();
    Date getRealTime();
    int getOffsetMinutes();
    String getID();
    boolean isCancelled();

    enum Type {
        STOP,
        ADRESS,
        POINT_OF_INTEREST
    }
}
