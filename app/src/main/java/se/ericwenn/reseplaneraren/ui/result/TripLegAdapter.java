package se.ericwenn.reseplaneraren.ui.result;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.trip.ILeg;

class TripLegAdapter extends RecyclerView.Adapter {

    private static final String TAG = "TripLegAdapter";
    private List<? extends ILeg> mDataset = new ArrayList<>();
    private final Context context;

    public TripLegAdapter(Context context) {
        super();
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mLegName;
        public TextView mLegIcon;
        public TextView mLegTravelTime;
        public TextView mLegOrigin;
        public TextView mLegDestination;

        public TextView mLegDestinationTime;
        public TextView mLegOriginTime;

        public ViewHolder(final View itemView) {
            super(itemView);
            mLegName = (TextView) itemView.findViewById(R.id.leg_name);
            mLegIcon = (TextView) itemView.findViewById(R.id.leg_icon);
            mLegTravelTime = (TextView) itemView.findViewById(R.id.leg_travel_time);
            mLegOrigin = (TextView) itemView.findViewById(R.id.leg_origin);
            mLegDestination = (TextView) itemView.findViewById(R.id.leg_destination);

            mLegDestinationTime = (TextView) itemView.findViewById(R.id.leg_destination_time);
            mLegOriginTime = (TextView) itemView.findViewById(R.id.leg_origin_time);

        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext()).inflate(R.layout.view_tripleg, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ILeg leg = mDataset.get(position);
        ViewHolder h = (ViewHolder) holder;

        h.mLegName.setText(mDataset.get(position).getName());

        Resources res = context.getResources();
        h.mLegTravelTime.setText(res.getString(R.string.leg_travel_time, leg.getTravelMinutes()));


        try {
            h.mLegIcon.setBackgroundColor(leg.getForegroundColor());
            h.mLegIcon.setTextColor(leg.getBackgroundColor());
        } catch (NullPointerException e) {
            h.mLegIcon.setVisibility(View.GONE);
        }
        if (leg.getShortName() == null) {
            h.mLegIcon.setVisibility(View.GONE);
        } else {
            h.mLegIcon.setText(leg.getShortName());
        }

        if (leg.getOrigin().getTrack() == null) {
            h.mLegOrigin.setText(leg.getOrigin().getName());
        } else {
            h.mLegOrigin.setText(res.getString(R.string.leg_node, leg.getOrigin().getName(), leg.getOrigin().getTrack()));
        }

        if (leg.getDestination().getTrack() == null) {
            h.mLegDestination.setText(leg.getDestination().getName());
        } else {
            h.mLegDestination.setText(res.getString(R.string.leg_node, leg.getDestination().getName(), leg.getDestination().getTrack()));
        }

        SimpleDateFormat stopTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        h.mLegOriginTime.setText(formatTimeWithDelay(leg.getOrigin().getTime(), leg.getOrigin().getOffsetMinutes(), stopTimeFormat));
        h.mLegDestinationTime.setText(formatTimeWithDelay(leg.getDestination().getTime(), leg.getDestination().getOffsetMinutes(), stopTimeFormat));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateDataset( List<? extends ILeg> newDataset ) {
        mDataset = newDataset;
        notifyDataSetChanged();
    }


    private String formatTimeWithDelay(Date originalTime, int offsetInMinutes, SimpleDateFormat dateFormat) {
        String time = dateFormat.format(originalTime);
        if (offsetInMinutes != 0) {
            time += offsetInMinutes < 0 ? "-" : "+";
            time += offsetInMinutes;
        }
        return time;
    }
}
