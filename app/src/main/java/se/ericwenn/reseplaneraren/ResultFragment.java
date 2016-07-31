package se.ericwenn.reseplaneraren;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.ericwenn.reseplaneraren.controller.SearchController;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.ITrip;
import se.ericwenn.reseplaneraren.model.data.VasttrafikAPIBridge;
import se.ericwenn.reseplaneraren.util.DataPromise;

public class ResultFragment extends Fragment {

    private static final String TAG = "ResultFragment";
    private ResultAdapter mAdapter;

    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ResultFragment.
     */
    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.result_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ResultAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }



    @Override
    public void onAttach(Context context) {

        Log.d(TAG, "onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();

        ILocation from = SearchController.getInstance().getSearchDataManager().getOrigin();
        ILocation to = SearchController.getInstance().getSearchDataManager().getDestination();

        DataPromise<List<ITrip>> promise = VasttrafikAPIBridge.getInstance().getTrips(from, to);

        promise.onResolve(new DataPromise.ResolvedHandler<List<ITrip>>() {
            @Override
            public void onResolve(List<ITrip> data) {
                mAdapter.updateDataset(data);
            }
        });
    }



    private class ResultAdapter extends RecyclerView.Adapter {
        private List<ITrip> mDataset = new ArrayList<>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTripType;
            public TextView mLegNames;
            public ViewHolder(View itemView) {
                super(itemView);

                mTripType = (TextView) itemView.findViewById(R.id.trip_type);
                mLegNames = (TextView) itemView.findViewById(R.id.trip_legnames);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_trip, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ITrip t = mDataset.get(position);

            // Trip type
            ((ViewHolder) holder).mTripType.setText( t.getArrivalTime().toString() );

            // Trip leg names
            String legNames = "";
            for(se.ericwenn.reseplaneraren.model.data.ILeg l : t.getLegs()) {
                legNames = legNames + "\r\n" + l.getName();
            }

            ((ViewHolder) holder).mLegNames.setText(legNames);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }


        public void updateDataset( List<ITrip> newDataset ) {
            mDataset = newDataset;
            notifyDataSetChanged();


        }
    }



}
