package com.energy.uniqgrid;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;


public class AddEstablishmentActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private TextView mPlaceDetailsText;
    private TextView mPlaceAttribution;
    NestedScrollView nsAddEst;
    HashMap<String,Place> markersList = new HashMap<>();

    TextInputLayout tilEstName,tilAccountOwner,tilSanctionedLoad;
    TextInputLayout tilAddress,tilCity,tilState,tilPincode;

    String estName="",accountOwner="",sanctionedLoad="",address="",city="",state="",pincode="";

    Spinner spSegment;


    private GoogleMap mMap;
    private GoogleMap googleMap;

    SupportMapFragment mapFragment;

    MapView mapView;
    Place place = null;

    int PLACE_PICKER_REQUEST = 1;

    CardView cdChooseOnMap;


    public static final String TAG = "AddEst Activity";
    public static final String PLACE_TAG = "New Location";

    String[] segmentTypes = {"Commercial","Industrial","Residential"};

    FloatingActionButton fabForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_establishment);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("New Establishment");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Retrieve the TextViews that will display details about the selected place.
        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);

        cdChooseOnMap = (CardView) findViewById(R.id.cdChooseOnMap);
        fabForward = (FloatingActionButton) findViewById(R.id.fabForward);
        nsAddEst = (NestedScrollView) findViewById(R.id.nsAddEst);



        tilEstName = (TextInputLayout) findViewById(R.id.tilEstName);
        tilAccountOwner = (TextInputLayout) findViewById(R.id.tilAccountOwner);
        tilSanctionedLoad = (TextInputLayout) findViewById(R.id.tilSanctionedLoad);
        tilAddress = (TextInputLayout) findViewById(R.id.tilAddress);
        tilCity = (TextInputLayout) findViewById(R.id.tilCity);
        tilState = (TextInputLayout) findViewById(R.id.tilState);
        tilPincode = (TextInputLayout) findViewById(R.id.tilPincode);
        spSegment = (Spinner) findViewById(R.id.spSegment);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, segmentTypes); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSegment.setAdapter(spinnerArrayAdapter);
        spSegment.setSelection(0);


        fabForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                estName = tilEstName.getEditText().getText().toString();
                accountOwner = tilAccountOwner.getEditText().getText().toString();
                sanctionedLoad = tilSanctionedLoad.getEditText().getText().toString();

                address = tilAddress.getEditText().getText().toString();
                city = tilCity.getEditText().getText().toString();
                state = tilState.getEditText().getText().toString();
                pincode = tilPincode.getEditText().getText().toString();


                if(estName.isEmpty()) {
                    tilEstName.getEditText().setError("Please enter Establishment Name");
                }else if (accountOwner.isEmpty()) {
                    tilAccountOwner.getEditText().setError("Please enter Acc. Owner Name");
                }else if (sanctionedLoad.isEmpty()) {
                    tilSanctionedLoad.getEditText().setError("Please enter Sanctioned Load ");
                }else if (address.isEmpty()) {
                    tilAddress.getEditText().setError("Please enter address");
                }else if(city.isEmpty()) {
                    tilCity.getEditText().setError("Please enter city");
                }else if (state.isEmpty()) {
                    tilState.getEditText().setError("Please enter State");
                }else if (pincode.isEmpty()) {
                    tilPincode.getEditText().setError("Please enter Pincode ");
                }else {
                    Intent intent = new Intent(AddEstablishmentActivity.this,ScheduleActivity.class);
                    startActivity(intent);

                }




            }
        });




        // Solving clash between scrolls of map and scroll view
        final ImageView transparentImageView = (ImageView) findViewById(R.id.transparent_image);

        transparentImageView.setOnTouchListener(
                new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        nsAddEst.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        v.performClick();
                        // Allow ScrollView to intercept touch events.
                        nsAddEst.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        nsAddEst.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }

            }
        });




        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // PICK A PLACE
        cdChooseOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(AddEstablishmentActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(this, data);
                mapFragment.getMapAsync(this);
                setLocation();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setLocation();
        // Zoom Buttons
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Setting Map Type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

      /* // Requesting Permissions for location
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);


        }else {
            ActivityCompat.requestPermissions(AddEstablishmentActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }
        */

        mMap.setOnMarkerClickListener(this);

    }



    public void setLocation(){
        Log.d("Location Selected ",place!=null?place.getName().toString():"null");
        if(place !=null) {

            mMap.clear();
            address="";city="";state="";pincode="";
            MarkerOptions markerOptions = new MarkerOptions().position(place.getLatLng()).title(place.getName().toString());
            //   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_trends));
            Marker marker = mMap.addMarker(markerOptions);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    place.getLatLng()).zoom(14).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            getAddressDetails(place);

            tilAddress.getEditText().setText(address);
            tilCity.getEditText().setText(city);
            tilState.getEditText().setText(state);
            tilPincode.getEditText().setText(pincode);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return true;
    }

    public void getAddressDetails(Place place) {
        String country="",stAddress1="",stAddress2="";
        String latitude="",longitude="";

        if (place.getAddress() != null) {
            String[] addressSlice = place.getAddress().toString().split(", ");
            country = addressSlice[addressSlice.length - 1];
            if (addressSlice.length > 1) {
                String[] stateAndPostalCode = addressSlice[addressSlice.length - 2].split(" ");
                if (stateAndPostalCode.length > 1) {
                    pincode = stateAndPostalCode[stateAndPostalCode.length - 1];
                    state = "";
                    for (int i = 0; i < stateAndPostalCode.length - 1; i++) {
                        state += (i == 0 ? "" : " ") + stateAndPostalCode[i];
                    }
                } else {
                    state = stateAndPostalCode[stateAndPostalCode.length - 1];
                }
            }
            if (addressSlice.length > 2)
                city = addressSlice[addressSlice.length - 3];
            if (addressSlice.length == 4)
                stAddress1 = addressSlice[0];
            else if (addressSlice.length > 3) {
                stAddress2 = addressSlice[addressSlice.length - 4];
                stAddress1 = "";
                for (int i = 0; i < addressSlice.length - 4; i++) {
                    stAddress1 += (i == 0 ? "" : ", ") + addressSlice[i];
                }
            }
        }
        if(place.getLatLng()!=null)
        {
            latitude = "" + place.getLatLng().latitude;
            longitude = "" + place.getLatLng().longitude;
        }

        address = stAddress1+" "+ stAddress2;
    }


}

