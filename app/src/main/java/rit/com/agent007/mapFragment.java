package rit.com.agent007;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class mapFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap googleMap;
    private MapView mapView;
    private View mView;

    private List<Marker> markerList = new ArrayList<>();
    private boolean locationUpdated = false;

    private ImageButton imageButton;

    private Location lastLocation;

    public mapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView)mView.findViewById(R.id.fragment_MapView);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    public void moveCameraTo(Location location){

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .bearing(0)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void addDynamicMarker(Location location, String agentID){
        lastLocation = location;

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("AGENT " + agentID.toUpperCase())
                .snippet(Calendar.getInstance().getTime().toString())
                .icon(BitmapDescriptorFactory.fromResource(R.raw.google_map_grey_marker));
        Marker marker = googleMap.addMarker(markerOptions);

        if(!locationUpdated){
            markerList.add(marker);
            locationUpdated = !locationUpdated;
        }
        else{
            markerList.remove(markerList.get(markerList.size() - 1));
            markerList.add(marker);
        }

    }

    private void callLastLocationCamera(){
        if(lastLocation != null)
        moveCameraTo(lastLocation);
    }

    private void imageButtonClickListener(ImageButton imgButton){


        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLastLocationCamera();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap gMap) {

        MapsInitializer.initialize(this.getContext());

        googleMap = gMap;
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));

        imageButton = (ImageButton)getView().findViewById(R.id.fragment_ImageButton_findLocation);
        imageButtonClickListener(imageButton);


    }
}
