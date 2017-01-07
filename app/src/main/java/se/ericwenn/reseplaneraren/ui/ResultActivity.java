package se.ericwenn.reseplaneraren.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.VasttrafikAPIBridge;
import se.ericwenn.reseplaneraren.model.data.trip.ITrip;
import se.ericwenn.reseplaneraren.services.StoredLocations;
import se.ericwenn.reseplaneraren.ui.result.ResultAdapter;
import se.ericwenn.reseplaneraren.util.DataPromise;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "Search started, origin = ["+from.getName()+"] destination = ["+to.getName()+"]");


        mAdapter = new ResultAdapter(this);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.result_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration( new SimpleRecyclerViewDivider(4));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        TextView from_name = (TextView) findViewById(R.id.result_from_name);
        TextView to_name = (TextView) findViewById(R.id.result_to_name);

        if( from_name != null && to_name != null) {
            from_name.setText(from.getName());
            to_name.setText(to.getName());
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                //Intent intent = new Intent(this, MapActivity.class);
                //startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

