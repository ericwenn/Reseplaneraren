package se.ericwenn.reseplaneraren.model.data;

import java.util.Date;
import java.util.List;

/**
 * Created by ericwenn on 7/29/16.
 */
public class Trip implements ITrip {

    private static final String TAG = "TripObject";
    public List<Leg> Leg;

    // travelWarranty (boolean, optional): IMPORTANT NOTE: journeys that are presented when the default change margin has been disregarded are not covered by V�sttrafiks travel warranty (Swedish: resegaranti).,
    private boolean travelWarranty;

    // valid (boolean, optional): The state indicates if the trip is still possible to ride based on the current realtime situation,
    private boolean valid;

    // alternative (boolean): The type indicates whether this is an original connection or an realtime alternative,
    private boolean alternative;

    // type (string, optional): Type of trip = ["WALK" or "BIKE" or "CAR"]
    private String type;

    public static class Leg implements ILeg {
        // (string, optional): Foregroundcolor of this line,
        private String fgColor;

        // (boolean, optional): Will be true if this journey needs to be booked,
        private boolean booking;

        // (string, optional): Direction information,
        private String direction;

        // TODO JourneyDetailRef (JourneyDetailRef, optional),

        // (boolean, optional): Will be true if this journey is cancelled,
        private boolean cancelled;

        // (float, optional): Energy use,
        private float kcal;

        // TODO Origin (Origin, optional),

        // (string, optional): Short name of the leg,
        private String sname;

        // (string): The attribute type specifies the type of the leg. Valid values are VAS, LDT (Long Distance Train), REG (Regional train), BUS , BOAT, TRAM, TAXI (Taxi/Telebus). Furthermore it can be of type WALK, BIKE and CAR if these legs are routes on the street network,
        private String type;

        // TODO GeometryRef (GeometryRef, optional),

        // (string, optional): Backgroundcolor of this line,
        private String bgColor;

        // TODO Notes (Notes, optional),

        // id (string, optional): ID of the journey,
        private String id;

        // (string, optional): Stroke style of this line,
        private String stroke;

        // reachable (boolean, optional): Will be true if this journey is not reachable due to delay of the feeder,
        private boolean reachable;

        // name (string): The attribute name specifies the name of the leg,
        private String name;

        // night (boolean, optional): Will be true if this journey is a night journey,
        private String night;

        // TODO Destination (Destination, optional),

        // percentBikeRoad (float, optional): Percentage of the route that is made up of bike roads,
        private float percentBikeRoad;

        // accessibility (string, optional): will only be set if the vehicle has wheelchair + ramp/lift or lowfloor according to realtime data
        private String accesibility;

        public String getName() {
            return name;
        }

    }



    public String getType() {
        return type;
    }

    public List<? extends ILeg> getLegs() {
        return Leg;
    }

    @Override
    public Date getDepartureTime() {
        return new Date();
    }

    @Override
    public Date getArrivalTime() {
        return new Date();
    }
}
