package se.ericwenn.reseplaneraren.controller;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 7/29/16.
 */
public class SearchDataManager implements ISearchDataManager {
    private ILocation origin;
    private ILocation destination;
    private static SearchDataManager instance = null;

    public static synchronized SearchDataManager getInstance() {
        if( instance == null ) {
            instance = new SearchDataManager();
        }
        return instance;
    }
    private SearchDataManager() {

    }

    @Override
    public void setOrigin(ILocation origin) {

        this.origin = origin;
    }

    @Override
    public ILocation getOrigin() {
        return this.origin;

    }

    @Override
    public void removeOrigin() {
        this.origin = null;
    }

    @Override
    public void setDestination(ILocation destination) {

        this.destination = destination;
    }

    @Override
    public ILocation getDestination() {
        return this.destination;
    }

    @Override
    public void removeDestination() {
        this.destination = null;
    }
}
