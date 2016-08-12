package se.ericwenn.reseplaneraren.ui.locationsearch;

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

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.VasttrafikAPIBridge;
import se.ericwenn.reseplaneraren.ui.FragmentController;
import se.ericwenn.reseplaneraren.util.DataPromise;


public class LocationSearchFragment extends Fragment implements ILocationSearchFragment {


    private static final String TAG = "LocationSearchFragment";

    private RecyclerView mRecyclerView;
    private AutoCompleteAdapter mAdapter;



    private FragmentController mController;
    private FragmentController.Field activeField;

    public LocationSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LocationSearchFragment.
     */
    public static LocationSearchFragment newInstance() {
        return new LocationSearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach()");
        super.onAttach(context);
        try {
            mController = (FragmentController) context;
        } catch (ClassCastException e) {
            throw new ClassCastException( context.toString() + " must implement FragmentController");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        mAdapter = new AutoCompleteAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.bottomsheet_locationsearch, container, false);


        setupRecyclerView(v);

        return v;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();

    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();

    }


    private void setupRecyclerView(View v) {

        mRecyclerView = (RecyclerView) v.findViewById(R.id.location_results);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void changeSearchTerm(String searchTerm, FragmentController.Field f) {
        activeField = f;
        Log.d(TAG, "changeSearchTerm() called with: " + "searchTerm = [" + searchTerm + "], f = [" + f + "]");
        DataPromise<List<ILocation>> promise = VasttrafikAPIBridge.getInstance().findLocations(searchTerm);

        promise.onResolve(new DataPromise.ResolvedHandler<List<ILocation>>() {
            @Override
            public void onResolve(List<ILocation> data) {
                mAdapter.updateDataset(data);
            }
        });
        promise.onReject(new DataPromise.RejectedHandler<List<ILocation>>() {
            @Override
            public void onReject(Exception e) {
                Log.d(TAG, "onReject() called with: " + "e = [" + e + "]");
            }
        });
    }





    private class AutoCompleteAdapter extends RecyclerView.Adapter {
        private List<ILocation> mDataset = new ArrayList<>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(View itemView) {
                super(itemView);

                mTextView = (TextView) itemView.findViewById(R.id.autocomplete_title);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_autocomplete, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int childLayoutPosition = mRecyclerView.getChildLayoutPosition(v);
                    ILocation selected = mDataset.get(childLayoutPosition);
                    mController.onLocationSelected( selected, activeField);
                }
            });
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder() called with: " + "holder = [" + holder + "], position = [" + position + "]");
            ((ViewHolder) holder).mTextView.setText( mDataset.get(position).getName() );
        }

        @Override
        public int getItemCount() {

            Log.d(TAG, "getItemCount: "+mDataset.size());
            return mDataset.size();
        }


        public void updateDataset( List<ILocation> newDataset ) {
            Log.d(TAG, "updateDataset() called with: " + "newDataset = [" + newDataset + "]" + " length = ["+ newDataset.size() + "]");
            mDataset = newDataset;
            notifyDataSetChanged();


        }
    }





}
