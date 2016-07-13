package se.ericwenn.reseplaneraren;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SearchFragment extends Fragment {


    private static final String TAG = "SearchFragment";
    private OnSearchInteractionListener mListener;
    private EditText originInput;
    private EditText destinationInput;

    public enum SearchField {
        DESTINATION,
        ORIGIN
    }


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

        originInput = (EditText) v.findViewById(R.id.origin_input);
        destinationInput = (EditText) v.findViewById(R.id.destination_input);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();


        SearchWatcher originSearchWatcher = new SearchWatcher(SearchField.ORIGIN);
        originInput.addTextChangedListener( originSearchWatcher );
        originInput.setOnFocusChangeListener( originSearchWatcher );

        SearchWatcher destinationSearchWatcher = new SearchWatcher(SearchField.DESTINATION);
        destinationInput.addTextChangedListener(destinationSearchWatcher );
        destinationInput.setOnFocusChangeListener(destinationSearchWatcher );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchInteractionListener) {
            mListener = (OnSearchInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }






    public interface OnSearchInteractionListener {

        void onFocusSearch(String searchTerm, SearchField field);
        void onFreeSearch(String searchTerm, SearchField field);

    }


    public class SearchWatcher implements TextWatcher, View.OnFocusChangeListener {

        private SearchField field;
        public SearchWatcher( SearchField field) {
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
            mListener.onFreeSearch(s.toString(), field);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if( hasFocus ) {

                mListener.onFocusSearch(((EditText) v).getText().toString(), field);
            }
        }
    }


}
