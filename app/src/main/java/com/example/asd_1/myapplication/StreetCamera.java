package com.example.asd_1.myapplication;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

/**
 * Created by asd-1 on 2/28/2017.
 */

public class StreetCamera {

    Place place;
    MainActivity main;

    public StreetCamera(Place place, MainActivity main) {
        this.main = main;
        this.place = place;
    }

}
