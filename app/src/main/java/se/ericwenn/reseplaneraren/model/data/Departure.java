package se.ericwenn.reseplaneraren.model.data;

import android.graphics.Color;

/**
 * Created by ericwenn on 8/4/16.
 */
public class Departure implements IDeparture {

    //fgColor (string): Foregroundcolor of this line,
    private String fgColor;
    //stop (string): Contains the name of the stop/station,
    //booking (boolean, optional): Will be true if this journey needs to be booked,
    //direction (string): Direction information,
    private String direction;
    //JourneyDetailRef (JourneyDetailRef),
    //track (string): Track information, if available,
    private String track;
    //rtTrack (string, optional): Realtime track information, if available,
    //sname (string): Short name of the leg,
    private String sname;
    //type (string): The attribute type specifies the type of the departing journey. Valid values are VAS, LDT (Long Distance Train), REG (Regional train), BUS , BOAT, TRAM, TAXI (Taxi/Telebus),
    //date (date): Date in format YYYY-MM-DD,
    private String date;
    //bgColor (string): Backgroundcolor of this line,
    private String bgColor;
    //stroke (string): Stroke style of this line,
    //rtDate (date): Realtime date in format YYYY-MM-DD, if available,
    //time (string): Time in format HH:MM,
    private String time;
    //name (string): The attribute name specifies the name of the departing journey,
    private String name;

    @Override
    public int getForegroundColor() {
        return Color.parseColor( fgColor );
    }

    @Override
    public int getBackgroundColor() {
        return Color.parseColor( bgColor );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public String getShortName() {
        return sname;
    }

    @Override
    public String getDestination() {
        return direction;
    }

    //rtTime (string): Realtime time in format HH:MM if available,
    //night (boolean, optional): Will be true if this journey is a night journey,
    //stopid (string): Contains the id of the stop/station,
    //journeyid (string): Contains the id of the journey,
    //accessibility (string): will only be set if the vehicle has wheelchair + ramp/lift or lowfloor according to realtime data = ["wheelChair" or "lowFloor"]
}
