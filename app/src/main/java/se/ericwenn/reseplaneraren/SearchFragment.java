package se.ericwenn.reseplaneraren;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import se.ericwenn.reseplaneraren.controller.ISearchField;
import se.ericwenn.reseplaneraren.controller.SearchController;
import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SearchFragment extends Fragment {


    private static final String TAG = "SearchFragment";
    private EditText originInput;
    private EditText destinationInput;


    private ISearchField originField = null;
    private ISearchField destinationField = null;


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
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        originInput = (EditText) v.findViewById(R.id.origin_input);
        destinationInput = (EditText) v.findViewById(R.id.destination_input);


        return v;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();


        originField = SearchController.getInstance().getSearchFieldManager().getOriginField();
        destinationField = SearchController.getInstance().getSearchFieldManager().getDestinationField();





        SearchWatcher originSearchWatcher = new SearchWatcher( originField );
        originInput.addTextChangedListener( originSearchWatcher );
        originInput.setOnFocusChangeListener( originSearchWatcher );
        originField.addFieldListener(new ISearchField.IFieldListener() {
            @Override
            public void onSearchTermChanged(String searchTerm) {

            }

            @Override
            public void onFinalChanged(ILocation finalValue) {
                if( finalValue != null) {
                    originInput.setText(finalValue.getName());
                    originInput.setTextColor(Color.parseColor("red"));
                } else {
                    originInput.setTextColor( Color.parseColor("black"));
                }
            }
        });

        SearchWatcher destinationSearchWatcher = new SearchWatcher( destinationField );
        destinationInput.addTextChangedListener(destinationSearchWatcher );
        destinationInput.setOnFocusChangeListener(destinationSearchWatcher );
        destinationField.addFieldListener(new ISearchField.IFieldListener() {
            @Override
            public void onSearchTermChanged(String searchTerm) {

            }

            @Override
            public void onFinalChanged(ILocation finalValue) {
                if( finalValue != null) {
                    destinationInput.setText(finalValue.getName());
                    destinationInput.setTextColor(Color.parseColor("red"));
                } else {
                    originInput.setTextColor( Color.parseColor("black"));
                }

            }
        });
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








    public class SearchWatcher implements TextWatcher, View.OnFocusChangeListener {

        private ISearchField field;
        public SearchWatcher( ISearchField field ) {
            this.field = field;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (field.getFinal() == null || !field.getFinal().getName().equals(s.toString())) {
                field.setSearchTerm( s.toString() );
            }
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if( hasFocus ) {
                SearchController.getInstance().getSearchFieldManager().setActiveField(field);
            } else {
             //   SearchController.getInstance().getSearchFieldManager().removeActiveField();
            }
        }
    }


}
