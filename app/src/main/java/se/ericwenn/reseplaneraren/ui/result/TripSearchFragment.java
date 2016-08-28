package se.ericwenn.reseplaneraren.ui.result;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.VasttrafikAPIBridge;
import se.ericwenn.reseplaneraren.model.data.trip.ITrip;
import se.ericwenn.reseplaneraren.ui.FragmentController;
import se.ericwenn.reseplaneraren.ui.shared.SimpleRecyclerViewDivider;
import se.ericwenn.reseplaneraren.util.DataPromise;

public class TripSearchFragment extends Fragment implements ITripSearchFragment {

    private static final String TAG = "TripSearchFragment";
    private ResultAdapter mAdapter;
    private ILocation originLocation;
    private ILocation destinationLocation;

    private FragmentController mController;

    public TripSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TripSearchFragment.
     */
    public static TripSearchFragment newInstance() {
        return new TripSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ResultAdapter( getActivity() );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.result_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration( new SimpleRecyclerViewDivider(4));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
        return v;
    }



    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            mController = (FragmentController) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentController");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void changeRoute(ILocation origin, ILocation destination) {
        Log.d(TAG, "changeRoute() called with: " + "origin = [" + origin + "], destination = [" + destination + "]");
        originLocation = origin;
        destinationLocation = destination;
        performSearch();
    }


    private void performSearch() {
        if( originLocation == null || destinationLocation == null) {
            throw new IllegalStateException("originLocation and destinationLocation must be set");
        }
        performSearch(originLocation, destinationLocation);
    }
    private void performSearch( ILocation from, ILocation to) {
        DataPromise<List<ITrip>> promise = VasttrafikAPIBridge.getInstance().getTrips(from, to);
        promise.onResolve(new DataPromise.ResolvedHandler<List<ITrip>>() {
            @Override
            public void onResolve(List<ITrip> data) {
                mAdapter.updateDataset(data);
            }
        });

    }













}
