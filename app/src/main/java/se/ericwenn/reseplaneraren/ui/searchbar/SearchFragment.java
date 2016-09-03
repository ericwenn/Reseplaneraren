package se.ericwenn.reseplaneraren.ui.searchbar;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.ui.FragmentController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SearchFragment extends Fragment implements ISearchFragment {


    private static final String TAG = "SearchFragment";
    private EditText originInput;
    private EditText destinationInput;


    private FragmentController mController;

    private OnFocusChangeListener mOriginFocusChangeListener;
    private OnFocusChangeListener mDestinationFocusChangeListener;

    private OnSearchTermChangedListener mOriginSearchTermChangedListener;
    private OnSearchTermChangedListener mDestinationSearchTermChangedListener;

    private boolean originIsFinal = false;
    private boolean destinationIsFinal = false;



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
        originInput.setSelectAllOnFocus(true);

        destinationInput = (EditText) v.findViewById(R.id.destination_input);
        destinationInput.setSelectAllOnFocus(true);
        
        mOriginFocusChangeListener = new OnFocusChangeListener(FragmentController.Field.ORIGIN);
        mDestinationFocusChangeListener = new OnFocusChangeListener(FragmentController.Field.DESTINATION);

        mOriginSearchTermChangedListener = new OnSearchTermChangedListener(FragmentController.Field.ORIGIN);
        mDestinationSearchTermChangedListener = new OnSearchTermChangedListener(FragmentController.Field.DESTINATION);

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
        originInput.setOnFocusChangeListener( mOriginFocusChangeListener );
        destinationInput.setOnFocusChangeListener( mDestinationFocusChangeListener );

        originInput.addTextChangedListener( mOriginSearchTermChangedListener );
        destinationInput.addTextChangedListener( mDestinationSearchTermChangedListener );
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach()");
        super.onAttach(context);
        try {
            mController = (FragmentController) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SearchFragmentController");
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }



    @Override
    public void setOriginLocation(ILocation origin) {
        if( origin == null ) {
            throw new IllegalArgumentException("Origin is null");
        }
        originIsFinal = true;
        originInput.removeTextChangedListener( mOriginSearchTermChangedListener );
        originInput.setText( origin.getName() );
        originInput.addTextChangedListener( mOriginSearchTermChangedListener);
        setFieldCompletion( originInput, true);
    }

    @Override
    public void setDestinationLocation(ILocation destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination is null");
        }
        destinationInput.removeTextChangedListener( mDestinationSearchTermChangedListener );
        destinationInput.setText( destination.getName() );
        destinationInput.addTextChangedListener( mDestinationSearchTermChangedListener );
        setFieldCompletion( destinationInput, true);
    }

    @Override
    public void focusField(FragmentController.Field f) {
        if( f == FragmentController.Field.ORIGIN ) {
            originInput.requestFocus();
        } else {
            destinationInput.requestFocus();
        }
    }

    @Override
    public void swapSearchFields() {

    }







    private class OnFocusChangeListener implements View.OnFocusChangeListener {

        private FragmentController.Field field;

        public OnFocusChangeListener(FragmentController.Field f) {
            field = f;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if( hasFocus ) {
                mController.onSearchTermChanged( ((EditText) v).getText().toString(), field);
            }
        }
    }

    private class OnSearchTermChangedListener implements TextWatcher {

        private FragmentController.Field field;

        public OnSearchTermChangedListener(FragmentController.Field f) {

            field = f;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mController.onSearchTermChanged( s.toString(), field);
            EditText textField = field == FragmentController.Field.DESTINATION ? destinationInput : originInput;
            setFieldCompletion( textField, false );
        }
    }



    private void setFieldCompletion(EditText field, boolean isComplete) {
        Resources res = getResources();
        int color = isComplete ? res.getColor(R.color.search_field_complete) : res.getColor(R.color.search_field_incomplete);

        field.setTextColor( color );
    }


}
