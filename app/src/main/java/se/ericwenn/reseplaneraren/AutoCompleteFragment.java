package se.ericwenn.reseplaneraren;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
    public static AutoCompleteFragment newInstance( String searchTerm) {
        Bundle args = new Bundle();
        args.putString("SearchTerm", searchTerm);
        AutoCompleteFragment fragment = new AutoCompleteFragment();

        fragment.setArguments(args);
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

        if (savedInstanceState != null) {
            setSearchTerm( savedInstanceState.getString("SearchTerm"));
        }
        Log.d(TAG, "onViewCreated: textView found");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
