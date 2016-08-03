package se.ericwenn.reseplaneraren.ui.map;

import java.util.List;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 8/1/16.
 */
public interface IMapFragment {

    void setMarkers( List<ILocation> markers );
    void addMarkers( List<ILocation> markers );
    void removeMarkers( List<ILocation> markers );
    void removeMarker( ILocation marker );

    void setCenter( long latitude, long longitude );

    void setZoom( float zlevel );


}
