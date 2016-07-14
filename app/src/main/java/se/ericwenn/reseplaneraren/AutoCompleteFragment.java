package se.ericwenn.reseplaneraren;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.ericwenn.reseplaneraren.controller.ISearchField;
import se.ericwenn.reseplaneraren.controller.ISearchFieldManager;
import se.ericwenn.reseplaneraren.controller.SearchController;


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
    private TextView text;

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

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = (TextView) view.findViewById(R.id.search_term);

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
            text.setText(searchTerm);
        }

        @Override
        public void onFinalChanged(ISearchField.Final finalValue) {

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





    public void setSearchTerm(final String searchTerm ) {
        text.setText(searchTerm);

    }
}
