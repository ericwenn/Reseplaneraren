package se.ericwenn.reseplaneraren.model.data;

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
import java.util.HashMap;
import java.util.List;

import se.ericwenn.reseplaneraren.services.IAuthorizer;
import se.ericwenn.reseplaneraren.services.IResponseAction;
import se.ericwenn.reseplaneraren.services.IRestClient;
import se.ericwenn.reseplaneraren.services.VasttrafikAuthorizer;

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
    public void getTrips(final ILocation from, final ILocation to, final Listener l) {
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

                        Log.d(TAG, "onSuccess() called with: " + "responseBody = [" + responseBody + "]");
                        List<Trip> trips;

                        try {
                            JSONObject o = new JSONObject(responseBody);

                            String s = o.getJSONObject("TripList").getString("Trip");


                            ObjectMapper m = new ObjectMapper();
                            m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                            m.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

                            trips = m.readValue(s, new TypeReference<List<Trip>>(){});
                            Log.d(TAG, "onSuccess: Fetched "+trips.size()+" trips from api.");

                            l.onSuccess(trips);

                        } catch (JSONException | JsonSyntaxException | IOException e) {
                            Log.e(TAG, "onSuccess: "+ responseBody, e);
                            l.onFailure(null);
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, String responseBody) {
                        l.onFailure(responseBody);
                    }
                });
            }
        });
    }

    @Override
    public void findLocations(final String search, final Listener l) {

        VasttrafikAuthorizer.getInstance().authorize(getClient(), new IAuthorizer.AuthorizationListener() {
            @Override
            public void onAuthorized(IRestClient client) {
                HashMap<String,String> p = new HashMap<>();
                p.put("input", search);
                p.put("format", "json");

                getClient().get("bin/rest.exe/v2/location.name", p, new IResponseAction() {
                    @Override
                    public void onSuccess(String responseBody) {

                        List<Location> locations;

                        try {
                            JSONObject o = new JSONObject(responseBody);
                            String s = o.getJSONObject("LocationList").getString("StopLocation");



                            Gson g = new Gson();

                            locations = g.fromJson(s, new TypeToken<List<Location>>(){}.getType());

                            Log.d(TAG, "onSuccess: "+locations.get(0).name);
                            l.onSuccess(locations);

                        } catch (JSONException | JsonSyntaxException e) {
                            Log.e(TAG, "onSuccess: "+ responseBody, e);
                            l.onFailure(null);
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, String responseBody) {
                        l.onFailure(responseBody);
                    }
                });
            }
        });


    }

    @Override
    public void findNearbyStops(double lat, double lon, Listener l) {

    }
}
