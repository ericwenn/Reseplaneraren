package se.ericwenn.reseplaneraren.model.data.trip;

import android.graphics.Color;

/**
 * Created by ericwenn on 8/24/16.
 */
public class Leg implements ILeg {

    // (string, optional): Foregroundcolor of this line,
    public String fgColor;

    // (boolean, optional): Will be true if this journey needs to be booked,
    private boolean booking;

    // (string, optional): Direction information,
    private String direction;

    // TODO JourneyDetailRef (JourneyDetailRef, optional),

    // (boolean, optional): Will be true if this journey is cancelled,
    private boolean cancelled;

    // (float, optional): Energy use,
    private float kcal;

    public TripStop Origin;

    // (string, optional): Short name of the leg,
    public String sname;

    // (string): The attribute type specifies the type of the leg. Valid values are VAS, LDT (Long Distance Train), REG (Regional train), BUS , BOAT, TRAM, TAXI (Taxi/Telebus). Furthermore it can be of type WALK, BIKE and CAR if these legs are routes on the street network,
    private String type;

    // TODO GeometryRef (GeometryRef, optional),

    // (string, optional): Backgroundcolor of this line,
    public String bgColor;

    // TODO Notes (Notes, optional),

    // id (string, optional): ID of the journey,
    private String id;

    // (string, optional): Stroke style of this line,
    private String stroke;

    // reachable (boolean, optional): Will be true if this journey is not reachable due to delay of the feeder,
    private boolean reachable;

    // name (string): The attribute name specifies the name of the leg,
    public String name;

    // night (boolean, optional): Will be true if this journey is a night journey,
    private String night;

    public TripStop Destination;

    // percentBikeRoad (float, optional): Percentage of the route that is made up of bike roads,
    private float percentBikeRoad;

    // accessibility (string, optional): will only be set if the vehicle has wheelchair + ramp/lift or lowfloor according to realtime data
    private String accesibility;



    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getShortName() {
        return sname;
    }

    @Override
    public Type getType() {
        Type r = null;
        switch (type) {
            case "VAS":
                r = Type.VASTTRAFIK;
                break;
            case "LDT":
                r = Type.LONG_DISTANCE_TRAIN;
                break;
            case "REG":
                r = Type.REGIONAL_TRAIN;
                break;
            case "BUS":
                r = Type.BUS;
                break;
            case "BOAT":
                r = Type.BOAT;
                break;
            case "TRAM":
                r = Type.TRAM;
                break;
            case "TAXT":
                r = Type.TAXI;
                break;
            case "WALK":
                r = Type.WALK;
                break;
            case "BIKE":
                r = Type.BIKE;
                break;
            case "CAR":
                r = Type.CAR;
                break;
        }
        return r;
    }

    @Override
    public int getBackgroundColor() throws NullPointerException {
        if( bgColor == null) {
            throw new NullPointerException("Backgroundcolor is null");
        }

        return Color.parseColor( bgColor );
    }

    @Override
    public int getForegroundColor() throws NullPointerException {
        if( fgColor == null ) {
            throw new NullPointerException("Foreground color is null");
        }
        return Color.parseColor( fgColor );
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public boolean isAccessible() {
        return accesibility != null;
    }

    @Override
    public ITripStop getOrigin() {
        return Origin;
    }

    @Override
    public ITripStop getDestination() {
        return Destination;
    }


    @Override
    public int getTravelMinutes() {
        return (int)(getDestination().getTime().getTime() - getOrigin().getTime().getTime())/60000;
    }
}
