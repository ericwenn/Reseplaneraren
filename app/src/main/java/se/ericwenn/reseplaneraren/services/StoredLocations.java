package se.ericwenn.reseplaneraren.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    public StoredLocations(Context context) {
        mContext = context;
        mGson = new Gson();
    }



    public DataPromise<List<ILocation>> getLastLocations() {
        return new DataPromiseImpl<>();
    }

    public void addLocation( ILocation l ) {

    }

    private void fetchLocations() {
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

        mStoredLocations = (List<ILocation>) mGson.fromJson(json, new TypeToken<List<Location>>(){}.getType());

    }

    private void storeLocations(List<ILocation> locations) {

    }


    private File getFile() {
        File f = new File(mContext.getFilesDir(), STORED_LOCATIONS_FILENAME);
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;

    }

}
