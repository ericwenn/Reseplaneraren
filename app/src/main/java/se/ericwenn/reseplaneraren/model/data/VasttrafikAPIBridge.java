package se.ericwenn.reseplaneraren.model.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import se.ericwenn.reseplaneraren.services.IAuthorizer;
import se.ericwenn.reseplaneraren.services.IResponseAction;
import se.ericwenn.reseplaneraren.services.IRestClient;
import se.ericwenn.reseplaneraren.services.VasttrafikAuthorizer;
import se.ericwenn.reseplaneraren.util.DataPromise;
import se.ericwenn.reseplaneraren.util.DataPromiseImpl;
import se.ericwenn.reseplaneraren.util.Util;

/**
 * Created by ericwenn on 7/20/16.
 */
public class VasttrafikAPIBridge extends AbstractVasttrafikAPIBridge {


    private static final String TAG = "ApiBridge";
    private static VasttrafikAPIBridge instance = null;

    public synchronized static VasttrafikAPIBridge getInstance() {
        if( instance == null) {
            instance = new VasttrafikAPIBridge();
        }
        return instance;
    }

    private VasttrafikAPIBridge() {

    }

    @Override
    public DataPromise<List<ITrip>> getTrips(@NonNull final ILocation from, @NonNull  final ILocation to) {


        if( from == null ) {
            throw new IllegalArgumentException("Origin cant be null");
        }
        if( to == null ) {
            throw new IllegalArgumentException("Destination cant be null");
        }

        final DataPromiseImpl<List<ITrip>> promise = new DataPromiseImpl<>();


        VasttrafikAuthorizer.getInstance().authorize(getClient(), new IAuthorizer.AuthorizationListener() {
            @Override
            public void onAuthorized(IRestClient client) {


                HashMap<String,String> p = new HashMap<>();
                p.put("format", "json");

                if( from.getLocationType() == ILocation.LocationType.STOP ) {
                    p.put("originId", from.getID());
                } else {
                    p.put("originCoordLat", from.getLatitude().toString());
                    p.put("originCoordLong", from.getLongitude().toString());
                    p.put("originCoordName", from.getName());
                }

                if( to.getLocationType() == ILocation.LocationType.STOP ) {
                    p.put("destId", to.getID());
                } else {
                    p.put("destCoordLat", to.getLatitude().toString());
                    p.put("destCoordLong", to.getLongitude().toString());
                    p.put("destCoordName", to.getName());
                }

                getClient().get("bin/rest.exe/v2/trip", p, new IResponseAction() {
                    @Override
                    public void onSuccess(String responseBody) {

                        List<ITrip> trips;

                        try {
                            JSONObject o = new JSONObject(responseBody);

                            String s = o.getJSONObject("TripList").getString("Trip");


                            ObjectMapper m = new ObjectMapper();
                            m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                            m.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

                            trips = m.readValue(s, new TypeReference<List<Trip>>(){});
                            Log.d(TAG, "onSuccess: Fetched "+trips.size()+" trips from api.");

                            promise.resolveData(trips);

                        } catch (JSONException | JsonSyntaxException | IOException e) {
                            Log.e(TAG, "onSuccess: "+ responseBody, e);
                            promise.rejectData(e);
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, String responseBody) {
                        promise.rejectData(new Exception(responseBody));
                    }
                });
            }
        });

        return promise;
    }

    @Override
    public DataPromise<List<ILocation>> findLocations(final String search) {

        final DataPromiseImpl<List<ILocation>> promise = new DataPromiseImpl<>();


        VasttrafikAuthorizer.getInstance().authorize(getClient(), new IAuthorizer.AuthorizationListener() {
            @Override
            public void onAuthorized(IRestClient client) {

                HashMap<String,String> p = new HashMap<>();
                p.put("input", search);
                p.put("format", "json");

                getClient().get("bin/rest.exe/v2/location.name", p, new IResponseAction() {
                    @Override
                    public void onSuccess(String responseBody) {

                        List<ILocation> locations;

                        try {
                            JSONObject o = new JSONObject(responseBody);
                            String s = o.getJSONObject("LocationList").getString("StopLocation");

                            Gson g = new Gson();

                            locations = g.fromJson(s, new TypeToken<List<Location>>(){}.getType());

                            Log.d(TAG, "onSuccess: Fetched "+locations.size()+" locations from api.");

                            promise.resolveData(locations);

                        } catch (JSONException | JsonSyntaxException e) {
                            Log.e(TAG, "onSuccess: "+ responseBody, e);
                            promise.rejectData(e);
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, String responseBody) {
                        promise.rejectData( new Exception(responseBody));
                    }
                });
            }
        });

        return promise;


    }



    @Override
    public DataPromise<List<ILocation>> findNearbyStops(final double lat, final double lon) {
        return findNearbyStops(lat, lon, 0);
    }

    @Override
    public DataPromise<List<ILocation>> findNearbyStops(final double lat, final double lon, @Nullable final int maxDistance) {
        if( maxDistance < 0 ) {
            throw new IllegalArgumentException("maxDistance must be positive");
        }
        final DataPromiseImpl<List<ILocation>> promise = new DataPromiseImpl<>();

        VasttrafikAuthorizer.getInstance().authorize(getClient(), new IAuthorizer.AuthorizationListener() {
            @Override
            public void onAuthorized(IRestClient client) {
                Log.d(TAG, "onAuthorized() called with: " + "client = [" + client + "]");
                HashMap<String, String> p = new HashMap<>();
                p.put("originCoordLat", Double.toString(lat) );
                p.put("originCoordLong", Double.toString(lon) );
                if( maxDistance != 0) {
                    p.put("maxDist", String.valueOf(maxDistance));
                }
                p.put("maxNo", "100");
                p.put("format", "json");

                getClient().get("bin/rest.exe/v2/location.nearbystops", p, new IResponseAction() {
                    @Override
                    public void onSuccess(String responseBody) {
                        List<ILocation> locations;

                        try {
                            JSONObject o = new JSONObject(responseBody);
                            String s = o.getJSONObject("LocationList").getString("StopLocation");

                            Gson g = new Gson();

                            locations = g.fromJson(s, new TypeToken<List<Location>>(){}.getType());

                            // Locations returned contain same stop with different track, prefer only one per stop
                            locations = Util.removeDuplicates(locations, new Util.DuplicateChecker<ILocation>() {
                                @Override
                                public Object uniqueAttribute(ILocation original) {
                                    return original.getName();
                                }
                            });

                            Log.d(TAG, "onSuccess: Fetched "+locations.size()+" locations from api.");

                            promise.resolveData(locations);

                        } catch (JSONException | JsonSyntaxException e) {
                            Log.e(TAG, "onSuccess: "+ responseBody, e);
                            promise.rejectData(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String responseBody) {
                        Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], responseBody = [" + responseBody + "]");
                        promise.rejectData(new Exception());
                    }
                });
            }
        });
        return promise;
    }




    public DataPromise<List<IDeparture>> getDepartures(final ILocation l) {
        final DataPromiseImpl<List<IDeparture>> promise = new DataPromiseImpl<>();

        VasttrafikAuthorizer.getInstance().authorize(getClient(), new IAuthorizer.AuthorizationListener() {
            @Override
            public void onAuthorized(IRestClient client) {
                HashMap<String, String> p = new HashMap<>();

                p.put("id", l.getID() );

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat stf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                p.put("date", sdf.format( new Date() ));
                p.put("time", stf.format( new Date() ));
                p.put("format", "json");
                p.put("timespan", "180");
                p.put("maxDeparturesPerLine", "1");

                getClient().get("bin/rest.exe/v2/departureBoard", p, new IResponseAction() {
                    @Override
                    public void onSuccess(String responseBody) {
                        List<IDeparture> departures;

                        try {
                            JSONObject o = new JSONObject(responseBody);
                            String s = o.getJSONObject("DepartureBoard").getString("Departure");

                            Gson g = new Gson();

                            departures = g.fromJson(s, new TypeToken<List<Departure>>(){}.getType());


                            Log.d(TAG, "onSuccess: Fetched "+departures.size()+" departures from api.");

                            promise.resolveData(departures);

                        } catch (JSONException | JsonSyntaxException e) {
                            Log.e(TAG, "onSuccess: "+ responseBody, e);
                            promise.rejectData(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String responseBody) {
                        Log.d(TAG, "onFailure() called with: " + "statusCode = [" + statusCode + "], responseBody = [" + responseBody + "]");
                        promise.rejectData(new Exception());
                    }
                });
            }
        });


        return promise;
    }
}
