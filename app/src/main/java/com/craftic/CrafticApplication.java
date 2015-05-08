package com.craftic;

import android.app.Application;
import android.location.Location;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import timber.log.Timber;

/**
 * Created by froger_mcs on 05.11.14.
 */
public class CrafticApplication extends Application {

    private static Location userLocation = null;
    public  static CrafticApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        app = this;
    }

    public  static void getLocation()
    {
        SmartLocation.with(app).location()
                .config(LocationParams.BEST_EFFORT)
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        Location lastKnownLocation = SmartLocation.with(app).location().getLastLocation();

                        if (location == null) {
                            userLocation = lastKnownLocation;
                        } else {
                            userLocation = location;
                        }

                    }
                });
    }
}


