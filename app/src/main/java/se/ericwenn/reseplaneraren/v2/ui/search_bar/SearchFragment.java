package se.ericwenn.reseplaneraren.v2.ui.search_bar;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SearchFragment extends Fragment implements ISearchFragment {



    private static final String TAG = "SearchFragment";
    private SearchFragmentController mController;
    private FieldWrapper originListener;
    private FieldWrapper destinationListener;

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
        originInput.setSelectAllOnFocus(true);
        originListener = new FieldWrapper(originInput, new FieldListener() {
            @Override
            public void onSearchTermChanged(String searchTerm) {
                mController.originChanged(searchTerm);
            }

            @Override
            public void onFocusGained() {
                Log.d(TAG, "onFocusGained() called with: " + "");
            }
        });


        EditText destinationInput = (EditText) v.findViewById(R.id.destination_input);
        destinationInput.setSelectAllOnFocus(true);
        destinationListener = new FieldWrapper(destinationInput, new FieldListener() {
            @Override
            public void onSearchTermChanged(String searchTerm) {
                mController.destinationChanged(searchTerm);
            }

            @Override
            public void onFocusGained() {
                Log.d(TAG, "onFocusGained() called with: " + "");
            }
        });

        Button searchButton = (Button) v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.search();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        originListener.start();
        destinationListener.start();
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
        originListener.setFinal(origin);
    }

    @Override
    public void setDestinationLocation(ILocation destination) {
        destinationListener.setFinal(destination);

    }


    private class FieldWrapper {
        private final EditText field;
        private final FieldListener listener;
        private boolean isFinal = false;
        private TextWatcher onChangeListener;

        public FieldWrapper(EditText field, final FieldListener listener) {
            this.field = field;
            this.listener = listener;


            this.onChangeListener = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    listener.onSearchTermChanged(s.toString());
                    isFinal = false;
                }
            };
        }

        public void start() {
            this.field.addTextChangedListener(this.onChangeListener);
        }

        public void stop() {
            this.field.removeTextChangedListener(this.onChangeListener);
        }

        public void setFinal( ILocation l) {
            this.stop();
            field.setText( l.getName() );
            isFinal = true;
            this.start();
        }

    }

    private interface FieldListener {
        void onSearchTermChanged(String searchTerm);
        void onFocusGained();
    }


}
