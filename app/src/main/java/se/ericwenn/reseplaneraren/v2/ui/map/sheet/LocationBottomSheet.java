package se.ericwenn.reseplaneraren.v2.ui.map.sheet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.IDeparture;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.VasttrafikAPIBridge;
import se.ericwenn.reseplaneraren.ui.FragmentController;
import se.ericwenn.reseplaneraren.util.DataPromise;

/**
 * Created by ericwenn on 8/4/16.
 */
public class LocationBottomSheet extends BottomSheetDialogFragment implements ILocationBottomSheet {

    private static final String TAG = "LocationBottomSheet";
    private ILocation mLocation;
    private DepartureAdapter mAdapter;
    private FragmentController mController;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach()");
        try {
            mController = (FragmentController) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException( activity.toString() + " must implement FragmentController");
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( savedInstanceState != null ) {
            mLocation = (ILocation) savedInstanceState.get("location");
        }
        Log.d(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        Log.d(TAG, "setupDialog()");
        View contentView = View.inflate(getContext(), R.layout.map_bottomsheet, null);
        dialog.setContentView(contentView);

        TextView mLocationName = (TextView) contentView.findViewById(R.id.stop_name);
        mLocationName.setText( mLocation.getName() );

        RecyclerView mRecyclerView = (RecyclerView) contentView.findViewById(R.id.departureBoard);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);


        LinearLayout mTravelFromHereButton = (LinearLayout) contentView.findViewById(R.id.travel_from_here);
        LinearLayout mTravelToHereButton = (LinearLayout) contentView.findViewById(R.id.travel_to_here);
        LinearLayout starLocationButton = (LinearLayout) contentView.findViewById(R.id.star);

        mTravelFromHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.onLocationSelected( mLocation, FragmentController.Field.ORIGIN);
                dismiss();

            }
        });

        mTravelToHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.onLocationSelected( mLocation, FragmentController.Field.DESTINATION);
                dismiss();
            }
        });

        starLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.starLocation( mLocation );
            }
        });



        Log.d(TAG, "setupDialog: mLocationName = ["+mLocationName+"]");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("location", (Parcelable) mLocation);
    }

    @Override
    public void setLocation(ILocation l) {
        Log.d(TAG, "setLocation()");
        mLocation = l;
        mAdapter = new DepartureAdapter();

        DataPromise<List<IDeparture>> promise = VasttrafikAPIBridge.getInstance().getDepartures(l);
        promise.onResolve(new DataPromise.ResolvedHandler<List<IDeparture>>() {
            @Override
            public void onResolve(List<IDeparture> data) {
                mAdapter.updateDataset(data);
            }
        });
    }




    private class DepartureAdapter extends RecyclerView.Adapter {
        private List<IDeparture> mDataset = new ArrayList<>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mDestination;
            public TextView mDepartureTime;
            public TextView mDepartureIcon;
            public ViewHolder(View itemView) {
                super(itemView);

                mDepartureTime = (TextView) itemView.findViewById(R.id.departure_time);
                mDestination = (TextView) itemView.findViewById(R.id.departure_name);
                mDepartureIcon = (TextView) itemView.findViewById(R.id.departure_icon);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_bottomsheet_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            IDeparture t = mDataset.get(position);
            ViewHolder vh = (ViewHolder) holder;
            vh.mDestination.setText(t.getDestination());
            vh.mDepartureTime.setText( t.getTime() );
            vh.mDepartureIcon.setText( t.getShortName() );
            vh.mDepartureIcon.setBackgroundColor( t.getForegroundColor() );
            vh.mDepartureIcon.setTextColor( t.getBackgroundColor() );
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }


        public void updateDataset( List<IDeparture> newDataset ) {
            mDataset = newDataset;
            notifyDataSetChanged();


        }
    }


}
