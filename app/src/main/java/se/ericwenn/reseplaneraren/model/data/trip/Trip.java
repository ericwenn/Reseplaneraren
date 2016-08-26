package se.ericwenn.reseplaneraren.model.data.trip;

import android.util.Log;

import java.util.Date;
import java.util.List;

/**
 * Created by ericwenn on 7/29/16.
 */
public class Trip implements ITrip {

    private static final String TAG = "TripObject";

    public List<Leg> Leg;

    // (optional): IMPORTANT NOTE: journeys that are presented when the default change margin has been disregarded are not covered by V�sttrafiks travel warranty (Swedish: resegaranti).,
    private boolean travelWarranty;

    // (optional) The state indicates if the trip is still possible to ride based on the current realtime situation,
    private boolean valid;

    // The type indicates whether this is an original connection or an realtime alternative,
    private boolean alternative;

    // (optional): Type of trip = ["WALK" or "BIKE" or "CAR"]
    private String type;


    public String getType() {
        return type;
    }


    @Override
    public boolean isValid() {
        return false;
    }

    public List<? extends ILeg> getLegs() {
        return Leg;
    }



    @Override
    public Date getDepartureTime() {
        Log.d(TAG, "getDepartureTime: legs.size() = "+getLegs().size());
        ILeg firstLeg = getLegs().get(0);
        Log.d(TAG, "getDepartureTime: firstLeg.origin = "+ firstLeg.getOrigin());
        if( firstLeg.getOrigin() == null ) {
            return new Date();
        } else {
            return firstLeg.getOrigin().getTime();

        }
    }

    @Override
    public Date getArrivalTime() {

        ILeg lastLeg = getLegs().get( getLegs().size() - 1);
        if( lastLeg.getDestination() == null ) {
            return new Date();
        } else {
            return lastLeg.getDestination().getTime();

        }
    }

    @Override
    public int getNumberOfSwitches() {
        return getLegs().size();
    }

    @Override
    public int getTotalTravelMinutes() {
        return 0;
    }
}
