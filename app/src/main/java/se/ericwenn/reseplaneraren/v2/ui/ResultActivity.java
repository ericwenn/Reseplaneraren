package se.ericwenn.reseplaneraren.v2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import se.ericwenn.reseplaneraren.R;
import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 11/6/16.
 */

public class ResultActivity extends AppCompatActivity {

    public static final String ORIGIN_EXTRA = "com.reseplaneraren.result.origin";
    public static final String DESTINATION_EXTRA = "com.reseplaneraren.result.origin";
    private static final String TAG = "ResultActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i = getIntent();
        ILocation origin = (ILocation) i.getSerializableExtra(ORIGIN_EXTRA);
        Log.d(TAG, "onCreate: "+origin);


        TextView textView = (TextView) findViewById(R.id.result_origin_name);
        if (textView != null) {
            textView.setText( origin.getName());
        }
    }
}
