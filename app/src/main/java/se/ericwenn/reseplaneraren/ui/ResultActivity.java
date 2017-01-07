package se.ericwenn.reseplaneraren.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.VasttrafikAPIBridge;
import se.ericwenn.reseplaneraren.model.data.trip.ITrip;
import se.ericwenn.reseplaneraren.services.StoredLocations;
import se.ericwenn.reseplaneraren.util.DataPromise;
import se.ericwenn.reseplaneraren.ui.result.ResultAdapter;

/**
 * Created by ericwenn on 11/6/16.
 */

public class ResultActivity extends AppCompatActivity {

    public static final String ORIGIN_EXTRA = "com.reseplaneraren.result.origin";
    public static final String DESTINATION_EXTRA = "com.reseplaneraren.result.destination";
    private static final String TAG = "ResultActivity";

    private ResultAdapter mAdapter;

    private ILocation from;
    private ILocation to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i = getIntent();
        from = (ILocation) i.getSerializableExtra(ORIGIN_EXTRA);
        to = (ILocation) i.getSerializableExtra(DESTINATION_EXTRA);

        saveLocations(from,to);


        Log.d(TAG, "Search started, origin = ["+from.getName()+"] destination = ["+to.getName()+"]");


        mAdapter = new ResultAdapter(this);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.result_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration( new SimpleRecyclerViewDivider(4));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void saveLocations(ILocation from, ILocation to) {
        StoredLocations storedLocations = new StoredLocations(getApplicationContext());
        storedLocations.addLocation(from);
        storedLocations.addLocation(to);
    }


    @Override
    protected void onStart() {
        super.onStart();

        DataPromise<List<ITrip>> promise = VasttrafikAPIBridge.getInstance().getTrips(from, to);
        promise.onResolve(new DataPromise.ResolvedHandler<List<ITrip>>() {
            @Override
            public void onResolve(List<ITrip> data) {
                mAdapter.updateDataset(data);
            }
        });
    }
}

