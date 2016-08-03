package se.ericwenn.reseplaneraren;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import se.ericwenn.reseplaneraren.controller.ISearchField;
import se.ericwenn.reseplaneraren.controller.ISearchFieldManager;
import se.ericwenn.reseplaneraren.controller.SearchController;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.IVasttrafikAPIBridge;
import se.ericwenn.reseplaneraren.model.data.VasttrafikAPIBridge;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AutoCompleteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AutoCompleteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutoCompleteFragment extends Fragment {
    private static final String TAG = "AutoCompleteFragment";

    private RecyclerView mRecyclerView;
    private AutoCompleteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public AutoCompleteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AutoCompleteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AutoCompleteFragment newInstance() {
        AutoCompleteFragment fragment = new AutoCompleteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d(TAG, "onCreateView: s");
        View v = inflater.inflate(R.layout.fragment_auto_complete, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.results_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        mAdapter = new AutoCompleteAdapter();
        mRecyclerView.setAdapter(mAdapter);




        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Log.d(TAG, "onViewCreated: textView found");
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach() called with: " + "context = [" + context + "]");
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();

        final FieldListener listener = new FieldListener();

        Log.d(TAG, "onResume() called with: " + "");

        SearchController.getInstance().getSearchFieldManager().addActiveSearchFieldChangeListener(new ISearchFieldManager.IActiveSearchFieldChangeListener() {
            @Override
            public void onChange(ISearchField oldField, ISearchField newField) {
                if( oldField != null) {
                    oldField.removeFieldListener(listener);
                }
                newField.addFieldListener(listener);
                listener.onSearchTermChanged( newField.getSearchTerm() );
            }
        });
        if( SearchController.getInstance().getSearchFieldManager().getActiveField() != null) {
            SearchController.getInstance().getSearchFieldManager().getActiveField().addFieldListener(listener);
            listener.onSearchTermChanged(SearchController.getInstance().getSearchFieldManager().getActiveField().getSearchTerm());
        }

    }


    class FieldListener implements ISearchField.IFieldListener {

        @Override
        public void onSearchTermChanged(String searchTerm) {

            VasttrafikAPIBridge.getInstance().findLocations(searchTerm, new IVasttrafikAPIBridge.Listener() {
                @Override
                public void onSuccess(Object o) {
                    try {
                        List<ILocation> locations = (List<ILocation>) o;
                        mAdapter.updateDataset(locations);
                    } catch (Exception e) {
                        Log.e(TAG, "onSuccess: Casting failed", e);
                    }
                }

                @Override
                public void onFailure(Object o) {
                }
            });

        }

        @Override
        public void onFinalChanged(ILocation finalValue) {

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                    SearchController.getInstance().getSearchFieldManager().getActiveField().setFinal(selected);
                }
            });
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).mTextView.setText( mDataset.get(position).getName() );
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }


        public void updateDataset( List<ILocation> newDataset ) {
            mDataset = newDataset;
            notifyDataSetChanged();


        }
    }





}
