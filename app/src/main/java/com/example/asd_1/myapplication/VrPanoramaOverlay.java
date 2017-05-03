package com.example.asd_1.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;


import java.util.ArrayList;

/**
 * Created by asd-1 on 12/3/2016.
 */
public class VrPanoramaOverlay {

    Place place;
    //MainActivity main;
    FullscreenActivity fullscreenActivity;
    String panoID;
    InspectPlace inspectPlaceParent;
    Bitmap panoramaBitmap;
    VrPanoramaView vrPanoView;

    public VrPanoramaOverlay(FullscreenActivity fullscreenActivity, String panoID){
        this.fullscreenActivity = fullscreenActivity;
        this.panoID = panoID;

        getStreetViewImages(panoID);

    }
    public void getStreetViewImages(String panoID){
        ArrayList<Bitmap> bmArray = new ArrayList<>();
        StreetViewPhotoTask photoTask = new StreetViewPhotoTask(bmArray, this, panoID);
        photoTask.execute(panoID);
        //TODO: Get images from place latlng or from place metadata

    }
    public void combineBitmaps(ArrayList<Bitmap> bmArray) {
        if (bmArray.size() > 0) {

            int width = 0;
            int height = 0;

                width = bmArray.get(0).getWidth() * 26;


            height = (bmArray.get(0).getHeight()) * 13;

            Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas comboImage = new Canvas(bm);
            int offsetX = 0;
            int offsetY = 0;
            int j = 0;
            for (int i = 0; i < bmArray.size(); i++) {

                comboImage.drawBitmap(bmArray.get(i), offsetX, offsetY, null);
                offsetX += bmArray.get(i).getWidth();

                if (j == 25) {
                    offsetY += bmArray.get(i).getHeight();
                    offsetX = 0;
                    j = 0;
                } else {
                    j++;
                }


            }
            panoramaBitmap = bm;
            createPanoramaOverlay();
        }
    }
    public void createPanoramaOverlay(){


         fullscreenActivity.createPanoramaOverlay(panoramaBitmap);


    }
}
