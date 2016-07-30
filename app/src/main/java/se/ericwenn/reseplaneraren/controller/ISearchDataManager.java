package se.ericwenn.reseplaneraren.controller;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 7/14/16.
 */
public interface ISearchDataManager {
    void setOrigin( ILocation origin);
    ILocation getOrigin();
    void removeOrigin();
    void setDestination(ILocation destination);
    ILocation getDestination();
    void removeDestination();
}