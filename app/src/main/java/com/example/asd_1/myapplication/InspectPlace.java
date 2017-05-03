package com.example.asd_1.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

public class InspectPlace {

    String placeID;
    String name;
    LatLng latlng;
    float rating;
    Place place;
    MainActivity main;
    View inspectPlaceLayout;
    int screenWidth;
    InspectPlace inspectPlace;

    public InspectPlace(MainActivity main, Place place) {
        this.main = main;
        this.place = place;
        inspectPlace = this;
        //screenWidth = 1;
        createView();
    }

    public void createView(){
        inspectPlaceLayout = main.getLayoutInflater().inflate(R.layout.inspect_place, main.contentMain, false);
        main.contentMain.addView(inspectPlaceLayout);
        TextView titleView = (TextView)inspectPlaceLayout.findViewById(R.id.title);
        titleView.setText(place.getName());

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final ImageView placePhotoView = (ImageView)inspectPlaceLayout.findViewById(R.id.placePhoto);

        new PhotoTask(100, 100, main) {
            @Override
            protected void onPreExecute() {
                // Display a temporary image to show while bitmap is loading.
                //placePhotoView.setImageResource(R.drawable.empty_photo);
            }

            @Override
            protected void onPostExecute(AttributedPhoto attributedPhoto) {
                if (attributedPhoto != null) {
                    // Photo has been loaded, display it.
                    placePhotoView.setImageBitmap(attributedPhoto.bitmap);

                    // Display the attribution as HTML content if set.
                    if (attributedPhoto.attribution == null) {
                        //mText.setVisibility(View.GONE);
                    } else {
                        //mText.setVisibility(View.VISIBLE);
                        //mText.setText(Html.fromHtml(attributedPhoto.attribution.toString()));
                    }
                }
            }
        }.execute(place.getId());

        placePhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VrPanoramaOverlay vrPanoOverlay = new VrPanoramaOverlay(inspectPlace, main);

                Intent startCameraIntent = new Intent(main, FullscreenActivity.class);
                startCameraIntent.putExtra("lat", place.getLatLng().latitude);
                startCameraIntent.putExtra("lng", place.getLatLng().longitude);
                main.startActivity(startCameraIntent);

            }
        });

    }


    public void releaseView(){

        main.contentMain.removeView(inspectPlaceLayout);
        //inspectPlaceLayout.setVisibility(View.GONE);

    }

}
