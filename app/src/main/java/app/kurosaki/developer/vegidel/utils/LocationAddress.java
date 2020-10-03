package app.kurosaki.developer.vegidel.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {
    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                String postcode = null;
                String city = null;
                String country = null;
                String state = null;
                String feature = null;

                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        Log.e(TAG, address.toString());
                        StringBuilder sb = new StringBuilder();
                        sb.append(address.getAddressLine(0));
                        postcode = address.getPostalCode();
                        city=address.getLocality();
                        state=address.getAdminArea();
                        feature=address.getFeatureName();
                        country=address.getCountryName();
                        result = sb.toString();
                        Log.e(TAG, "Successfully Connected");
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        bundle.putString("postcode", postcode);
                        bundle.putString("city", city);
                        bundle.putString("feature", feature);
                        bundle.putString("state", state);
                        bundle.putString("country", country);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}