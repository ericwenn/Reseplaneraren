package se.ericwenn.reseplaneraren.model.data.trip;

import java.util.Date;
import java.util.List;

/**
 * Created by ericwenn on 7/30/16.
 */
public interface ITrip {

    boolean isValid();

    List<? extends ILeg> getLegs();


    Date getDepartureTime();
    Date getArrivalTime();


    int getNumberOfSwitches();
    int getTotalTravelMinutes();
}
