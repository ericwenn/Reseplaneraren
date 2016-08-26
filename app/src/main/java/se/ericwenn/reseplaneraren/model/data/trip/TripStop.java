package se.ericwenn.reseplaneraren.model.data.trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ericwenn on 8/24/16.
 */
public class TripStop implements ITripStop {


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
    public String rtDate;

    //        Notes (Notes, optional),

    //id (string, optional): ID of this stop,
    private String id;

    //        rtDate (date, optional): Realtime date in format YYYY-MM-DD, if available,

    // time (string): Time in format HH:MM,
    public String time;
    public String rtTime;

    //directdate (date, optional): Date in format YYYY-MM-DD. Based on the direct travel time,

    // name (string): Contains the name of the location,
    private String name;

    @Override
    public Date getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            return sdf.parse( date + " " + time);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public Date getRealTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            return sdf.parse( rtDate + " " + rtTime);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
