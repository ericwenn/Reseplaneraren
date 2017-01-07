package se.ericwenn.reseplaneraren.ui.result;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.trip.ILeg;
import se.ericwenn.reseplaneraren.model.data.trip.ITrip;

/**
 * Created by ericwenn on 8/28/16.
 */
public class ResultAdapter extends RecyclerView.Adapter {
    private static final String TAG = "ResultAdapter";

    private List<ITrip> mDataset = new ArrayList<>();
    private final Context context;

    private int expandedTripPosition = -1;


    public ResultAdapter(Context context) {
        super();
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mDepartureTime;
        public TextView mDepartureOffset;
        public TextView mArrivalTime;
        public TextView mArrivalOffset;
        public TextView mAdditionInformation;
        public LinearLayout mToggleTrigger;

        public ImageView mTrackTripButton;

        public RecyclerView mRecyclerView;

        public LinearLayout mLegIcons;


        public ViewHolder(final View itemView) {
            super(itemView);

            mToggleTrigger = (LinearLayout) itemView.findViewById(R.id.toggle_trigger);
            mDepartureTime = (TextView) itemView.findViewById(R.id.trip_departure_time);
            mDepartureOffset = (TextView) itemView.findViewById(R.id.trip_departure_offset);

            mArrivalTime = (TextView) itemView.findViewById(R.id.trip_arrival_time);
            mArrivalOffset = (TextView) itemView.findViewById(R.id.trip_arrival_offset);

            mAdditionInformation = (TextView) itemView.findViewById(R.id.trip_additional_info);

            mTrackTripButton = (ImageView) itemView.findViewById(R.id.trip_track_button);


            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.leg_recyclerView);
            mRecyclerView.setAdapter( new TripLegAdapter( context ) );
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( context );
            mRecyclerView.setLayoutManager( mLayoutManager );

            mLegIcons = (LinearLayout) itemView.findViewById(R.id.trip_leg_icons);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_trip, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");

        final ViewHolder mHolder = (ViewHolder) holder;
        final ITrip t = mDataset.get(position);
        Resources res = context.getResources();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // Trip departure
        mHolder.mDepartureTime.setText( sdf.format( t.getDepartureTime()) );


        // Trip departureOffset
        if( t.getDepartureOffset() != 0) {
            String token;
            if( t.getDepartureOffset() < 0 ) {
                token = "-";
            } else {
                token = "+";
            }
            mHolder.mDepartureOffset.setText( res.getString(R.string.time_offset, token, t.getDepartureOffset()));
        } else {
            mHolder.mDepartureOffset.setVisibility(View.GONE);
        }




        // Trip arrival
        mHolder.mArrivalTime.setText( sdf.format(t.getArrivalTime()) );

        // Trip arrivalOffset
        if( t.getArrivalOffset() != 0) {
            String token;
            if( t.getArrivalOffset() < 0 ) {
                token = "-";
            } else {
                token = "+";
            }
            mHolder.mArrivalOffset.setText( res.getString(R.string.time_offset, token, t.getDepartureOffset()));
        } else {
            mHolder.mArrivalOffset.setVisibility( View.GONE);
        }


        if( mHolder.mLegIcons.getChildCount() == 0) {

            List<? extends ILeg> legs = t.getLegs();
            float scale = res.getDisplayMetrics().density;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0, (int) (scale*4 + 0.5f), 0);
            for (ILeg leg : legs) {
                TextView icon = new TextView(context);
                icon.setTypeface(null, Typeface.BOLD);
                icon.setLayoutParams(layoutParams);

                icon.setPadding((int) (5*scale + 0.5f), (int) (3*scale + 0.5f), (int) (5*scale + 0.5f), (int) (3*scale + 0.5f));
                icon.setTextSize( 12 );
                try {
                    icon.setBackgroundColor(leg.getForegroundColor());
                    icon.setTextColor(leg.getBackgroundColor());
                } catch (NullPointerException e) {
                    continue;
                }
                icon.setText(leg.getShortName());

                mHolder.mLegIcons.addView(icon);
            }
        }
        String additional = "";

        int childCount = mHolder.mLegIcons.getChildCount();
        if( childCount == 1) {
            additional += "Inga byten";
        } else if( childCount == 2 ) {
            additional += "1 byte";
        } else {
            additional += (childCount - 1) + " byten";
        }
        mHolder.mAdditionInformation.setText(additional);



        final TripLegAdapter legAdapter = (TripLegAdapter) mHolder.mRecyclerView.getAdapter();
        final RecyclerView legRecyclerView = mHolder.mRecyclerView;

        if( position == expandedTripPosition ) {
            legAdapter.updateDataset( t.getLegs() );
            legRecyclerView.setVisibility( View.VISIBLE );
        } else {
            legAdapter.updateDataset( new ArrayList<ILeg>() );
            legRecyclerView.setVisibility( View.GONE );
        }


        mHolder.mToggleTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mHolder.getAdapterPosition();
                if (position == expandedTripPosition) {
                    expandedTripPosition = -1;
                    notifyItemChanged(position);
                } else {
                    if( expandedTripPosition > -1) {
                        int lastExpandedPosition = expandedTripPosition;
                        notifyItemChanged(lastExpandedPosition);
                    }
                    expandedTripPosition = position;
                    notifyItemChanged(expandedTripPosition);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public void updateDataset( List<ITrip> newDataset ) {
        mDataset = newDataset;
        notifyDataSetChanged();


    }



    private void closeCurrentlyExpandedItem() {
        if (expandedTripPosition > -1) {

        }
    }

}