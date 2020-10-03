package app.kurosaki.developer.vegidel.ui.activities;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityPickAddressBinding;
import app.kurosaki.developer.vegidel.utils.Common;

import static app.kurosaki.developer.vegidel.utils.LocationAddress.getAddressFromLocation;

public class PickAddress extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double dlat = 0.0, dlong = 0.0;
    private String[] PERMISSIONSLOCATION = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private FusedLocationProviderClient mFusedLocationClient;
    private String location,postcode,city,state,country,feature;
    MarkerOptions markerOptions;
    ActivityPickAddressBinding pickAddressBinding;
    LatLng sydney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickAddressBinding= DataBindingUtil.setContentView(mContext, R.layout.activity_pick_address);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Common.setToolbarWithBackAndTitle(mContext,"Pick Address",false,R.drawable.ic_back);
        pickAddressBinding.mToolbar.toolbar.setNavigationOnClickListener(v->onBackPressed());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        getLastLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        pickAddressBinding.con.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.putExtra("ADDRESS",location);
            intent.putExtra("LATIUDE",dlat);
            intent.putExtra("LONGITUDE",dlong);
            intent.putExtra("postcode",postcode);
            intent.putExtra("country",country);
            intent.putExtra("city",city);
            intent.putExtra("state",state);
            intent.putExtra("feature",feature);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(mContext, R.raw.map));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        setmMap();
    }

    public void setmMap()  {
        sydney = new LatLng(dlat, dlong);
        mMap.clear();
        markerOptions=new MarkerOptions()
                .position(sydney)
                .draggable(true)
                .title(location);
        mMap.addMarker(markerOptions);
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mMap.clear();
                dlat=marker.getPosition().latitude;
                dlong=marker.getPosition().longitude;
                getAddressFromLocation(dlat,dlong,mContext,new GeocoderHandler());
                sydney = new LatLng(dlat, dlong);
                markerOptions=new MarkerOptions()
                        .position(sydney)
                        .draggable(true)
                        .title(location);
                mMap.addMarker(markerOptions);
            }
        });
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sydney)
                .zoom(17)
                .bearing(0)
                .tilt(15)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NotNull LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            if (mLastLocation != null)
                dlat = mLastLocation.getLatitude();
            assert mLastLocation != null;
            dlong = mLastLocation.getLongitude();
            if (dlat != 0.0 && dlong != 0.0) {
                getAddressFromLocation(dlat, dlong,
                        mContext, new GeocoderHandler());
            }
        }
    };

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (hasPermissions(mContext, PERMISSIONSLOCATION)) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        task -> {
                            Location location = task.getResult();
                            if (location == null) {
                                requestNewLocationData();
                            } else {
                                dlat = location.getLatitude();
                                dlong = location.getLongitude();
                                getAddressFromLocation(dlat, dlong,
                                        mContext, new GeocoderHandler());
                            }
                        }
                );
            } else {
                showToast("Turn on location");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions(PERMISSIONSLOCATION, PERMISSION_REQUEST_CODE_LOCATION);
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

    }

    @SuppressLint("HandlerLeak")
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(@NotNull Message message) {
            String locationAddress;
            if (message.what == 1) {
                Bundle bundle = message.getData();
                locationAddress = bundle.getString("address");
                postcode = bundle.getString("postcode");
                state = bundle.getString("state");
                city = bundle.getString("city");
                country = bundle.getString("country");
                feature = bundle.getString("feature");
            } else {
                locationAddress = null;
            }
            Log.e("Location",locationAddress);
            location = locationAddress;
            setmMap();
        }
    }
}