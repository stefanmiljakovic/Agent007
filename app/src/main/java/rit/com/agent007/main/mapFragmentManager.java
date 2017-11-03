package rit.com.agent007.main;

import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.ImageButton;

import rit.com.agent007.R;
import rit.com.agent007.mapFragment;

/**
 * Created by Stefan on 3/11/17.
 */

public class mapFragmentManager extends sensorGyroscope {

    private FragmentManager fragmentManager;
    private mapFragment mFragment;

    private Boolean initialLocation = true;

    private void replaceView(){
        // Get supported manager
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_FrameLayout_map, mFragment)
                .commit();

    }

    protected void onLocationChanges(Location location, String agentID){
        if(initialLocation){
        moveCameraTo(location);
        initialLocation = !initialLocation;
        }

        dynamicMarker(location, agentID);
    }

    private void moveCameraTo(Location location){
        mFragment.moveCameraTo(location);
    }

    private void dynamicMarker(Location location, String agentID){
        mFragment.addDynamicMarker(location, agentID);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragment = new mapFragment();
        replaceView();
    }
}
