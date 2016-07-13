package se.ericwenn.reseplaneraren;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( findViewById(R.id.fragment_container) == null ) {
            throw new RuntimeException("Fragment container does not exist in main");
        }

        MapFragment acf = MapFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, acf).commit();
    }


}
