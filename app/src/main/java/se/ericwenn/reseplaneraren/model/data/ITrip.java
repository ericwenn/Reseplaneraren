package se.ericwenn.reseplaneraren.model.data;

import java.util.Date;
import java.util.List;

/**
 * Created by ericwenn on 7/30/16.
 */
public interface ITrip {

    List<? extends ILeg> getLegs();
    Date getDepartureTime();
    Date getArrivalTime();
}
