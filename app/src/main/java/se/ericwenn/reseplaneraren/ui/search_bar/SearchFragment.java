package se.ericwenn.reseplaneraren.ui.search_bar;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.ui.search_bar.searchfield.ILocationSearchField;
import se.ericwenn.reseplaneraren.ui.search_bar.searchfield.LocationSearchField;
import se.ericwenn.reseplaneraren.ui.search_bar.searchfield.LocationSearchFieldListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SearchFragment extends Fragment implements ISearchFragment {



    private static final String TAG = "SearchFragment";
    private SearchFragmentController mController;


    private ILocationSearchField originSearchField;
    private ILocationSearchField destinationSearchField;
    private Button searchButton;


    public SearchFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View v = inflater.inflate(R.layout.fragment_search, container, false);


        EditText originInput = (EditText) v.findViewById(R.id.origin_input);
        originSearchField = new LocationSearchField(originInput, new LocationSearchFieldListener() {
            @Override
            public void searchChanged(String searchTerm) {
                mController.originChanged(searchTerm);
                updateSearchState();
            }
        });


        EditText destinationInput = (EditText) v.findViewById(R.id.destination_input);
        destinationSearchField = new LocationSearchField(destinationInput, new LocationSearchFieldListener() {
            @Override
            public void searchChanged(String searchTerm) {
                mController.destinationChanged(searchTerm);
                updateSearchState();
            }
        });


        searchButton = (Button) v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( destinationSearchField.isFinal() && originSearchField.isFinal()) {
                    mController.search(originSearchField.getFinalLocation(), destinationSearchField.getFinalLocation());
                }
            }
        });
        updateSearchState();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onResume() {
        super.onResume();
        destinationSearchField.start();
        originSearchField.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        destinationSearchField.stop();
        originSearchField.stop();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mController = (SearchFragmentController) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SearchFragmentController");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setOriginLocation(ILocation origin) {
        // Change text of origin field
        // If both origin and destination are final, enable the searchbutton
        originSearchField.setFinal(origin);
        updateSearchState();
    }

    @Override
    public void setDestinationLocation(ILocation destination) {
        destinationSearchField.setFinal(destination);
        updateSearchState();
    }


    private void updateSearchState() {

        if( destinationSearchField.isFinal() && originSearchField.isFinal()) {
            Log.d(TAG, "Both origin and destination are valid locations, search is enabled...");
            enableSearch();
        } else {
            Log.d(TAG, "One of origin and destination are not valid locations, search is disabled...");
            disableSearch();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void disableSearch() {
        searchButton.setAlpha(0.5f);
        searchButton.setClickable(false);
    }

    private void enableSearch() {
        searchButton.setAlpha(1f);
        searchButton.setClickable(true);
    }




}
