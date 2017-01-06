package se.ericwenn.reseplaneraren.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.Location;
import se.ericwenn.reseplaneraren.util.DataPromise;
import se.ericwenn.reseplaneraren.util.DataPromiseImpl;

/**
 * Created by ericwenn on 1/4/17.
 */

public class StoredLocations {
    private final Context mContext;
    private Gson mGson;
    private List<ILocation> mStoredLocations;
    private static final String STORED_LOCATIONS_FILENAME = "stored_locations.json";

    private boolean fileIsFetched = false;

    private static final String TAG = "StoredLocations";
    public StoredLocations(Context context) {
        mContext = context;
        mGson = new Gson();
    }



    public DataPromise<List<ILocation>> getLastLocations() {
        Log.d(TAG, "Getting stored locations...");
        if( !fileIsFetched) {
            fetchLocations();
        }

        DataPromiseImpl<List<ILocation>> r = new DataPromiseImpl<>();
        r.resolveData(mStoredLocations);
        return r;
    }

    public void addLocation( ILocation l ) {
        Log.d(TAG, "Adding location ["+l.getName()+"] to stored locations...");
        if( !fileIsFetched) {
            fetchLocations();
        }

        if( mStoredLocations.contains(l) ) {
            mStoredLocations.remove(l);
        }
        mStoredLocations.add(0,l);

        storeLocations(mStoredLocations);

    }

    private void fetchLocations() {
        Log.d(TAG, "Fetching stored locations from file...");
        File f = getFile();
        InputStream in = null;
        byte[] bytes = new byte[(int) f.length()];
        try {
            in = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String json = new String(bytes);

        mStoredLocations = mGson.fromJson(json, new TypeToken<List<Location>>(){}.getType());
        if( mStoredLocations == null ) {
            mStoredLocations = new ArrayList<ILocation>();
        }
        Log.d(TAG, "Found " + mStoredLocations.size() + " stored locations");
        fileIsFetched = true;

    }

    private void storeLocations(List<ILocation> locations) {
        Log.d(TAG, "Attempting to store locations to file...");
        String json = mGson.toJson(mStoredLocations, new TypeToken<List<Location>>(){}.getType());

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            out.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "Successfully stored locations to file");
    }


    private File getFile() {
        File f = new File(mContext.getFilesDir(), STORED_LOCATIONS_FILENAME);
        if(!f.exists()) {
            Log.d(TAG, "Stored locations file does not exist, creating now...");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "File created successfully");
        }
        return f;

    }

}
