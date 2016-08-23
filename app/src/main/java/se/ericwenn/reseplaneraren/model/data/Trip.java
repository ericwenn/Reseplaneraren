package se.ericwenn.reseplaneraren.model.data;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        public Origin Origin;

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
        public Destination Destination;

        // percentBikeRoad (float, optional): Percentage of the route that is made up of bike roads,
        private float percentBikeRoad;

        // accessibility (string, optional): will only be set if the vehicle has wheelchair + ramp/lift or lowfloor according to realtime data
        private String accesibility;

        public String getName() {
            return name;
        }

        @Override
        public LegRef getOrigin() {
            Log.d(TAG, "getOrigin: "+ Origin);
            return Origin;
        }

        @Override
        public LegRef getDestination() {
            return Destination;
        }

        public static class Origin implements ILeg.LegRef {

            //routeIdx (string, optional): Route index of a stop/station. Can be used as a reference of the stop/station in a journeyDetail response,

            //$ (string),
            // cancelled (boolean, optional): Will be set to true if departure/arrival at this stop is cancelled,
            private boolean cancelled;

            //        track (string, optional): Track information, if available,
            private String track;

            //        rtTrack (string, optional): Realtime track information, if available,

            //        type (string): The attribute type specifies the type of location. Valid values are ST (stop/station), ADR (address) or POI (point of interest),
            private String type;

            //date (date): Date in format YYYY-MM-DD,
            public String date;

            //        Notes (Notes, optional),

            //id (string, optional): ID of this stop,
            private String id;

            //        rtDate (date, optional): Realtime date in format YYYY-MM-DD, if available,

            // time (string): Time in format HH:MM,
            public String time;

            //directdate (date, optional): Date in format YYYY-MM-DD. Based on the direct travel time,

            // name (string): Contains the name of the location,
            private String name;

            @Override
            public Date getTime() {
                Log.d(TAG, "Orgiin getTime: date=["+date+"] time=["+time+"]");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                try {
                    return sdf.parse(date + " " + time);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            //rtTime (string, optional): Realtime time in format HH:MM if available,
            // directtime (string, optional): Direct Time format HH:MM. Based on the direct travel time
        }


        public static class Destination implements ILeg.LegRef {

            //routeIdx (string, optional): Route index of a stop/station. Can be used as a reference of the stop/station in a journeyDetail response,

            //$ (string),
            // cancelled (boolean, optional): Will be set to true if departure/arrival at this stop is cancelled,
            private boolean cancelled;

            //        track (string, optional): Track information, if available,
            private String track;

            //        rtTrack (string, optional): Realtime track information, if available,

            //        type (string): The attribute type specifies the type of location. Valid values are ST (stop/station), ADR (address) or POI (point of interest),
            private String type;

            //date (date): Date in format YYYY-MM-DD,
            public String date;

            //        Notes (Notes, optional),

            //id (string, optional): ID of this stop,
            private String id;

            //        rtDate (date, optional): Realtime date in format YYYY-MM-DD, if available,

            // time (string): Time in format HH:MM,
            public String time;

            //directdate (date, optional): Date in format YYYY-MM-DD. Based on the direct travel time,

            // name (string): Contains the name of the location,
            private String name;


            @Override
            public Date getTime() {
                Log.d(TAG, "Destination getTime: date=["+date+"] time=["+time+"]");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                try {
                    return sdf.parse(date + " " + time);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            //rtTime (string, optional): Realtime time in format HH:MM if available,
            // directtime (string, optional): Direct Time format HH:MM. Based on the direct travel time
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
}
