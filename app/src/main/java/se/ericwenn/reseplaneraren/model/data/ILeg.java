package se.ericwenn.reseplaneraren.model.data;

import java.util.Date;

/**
 * Created by ericwenn on 7/30/16.
 */
public interface ILeg {

    String getName();

    LegRef getOrigin();
    LegRef getDestination();

    public interface LegRef {
        Date getTime();
    }
}
