package com.example.asd_1.myapplication;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;

/**
 * Created by asd-1 on 8/11/2016.
 */
abstract class PhotoTask extends AsyncTask<String, Void, PhotoTask.AttributedPhoto> {

    private int mHeight;

    private int mWidth;

    private MainActivity main;

    public PhotoTask(int width, int height, MainActivity main) {
        mHeight = height;
        mWidth = width;
        this.main = main;
    }

    /**
     * Loads the first photo for a place id from the Geo Data API.
     * The place id must be the first (and only) parameter.
     */
    @Override
    protected AttributedPhoto doInBackground(String... params) {
        if (params.length != 1) {
            return null;
        }
        final String placeId = params[0];
        AttributedPhoto attributedPhoto = null;

        PlacePhotoMetadataResult result = Places.GeoDataApi
                .getPlacePhotos(main.googleApiClient, placeId).await();

        if (result.getStatus().isSuccess()) {
            PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
            if (photoMetadataBuffer.getCount() > 0 && !isCancelled()) {
                // Get the first bitmap and its attributions.
                PlacePhotoMetadata photo = photoMetadataBuffer.get(0);
                CharSequence attribution = photo.getAttributions();
                // Load a scaled bitmap for this photo.
                Bitmap image = photo.getScaledPhoto(main.googleApiClient, mWidth, mHeight).await()
                        .getBitmap();

                attributedPhoto = new AttributedPhoto(attribution, image);
            }
            // Release the PlacePhotoMetadataBuffer.
            photoMetadataBuffer.release();
        }
        return attributedPhoto;
    }

    /**
     * Holder for an image and its attribution.
     */
    class AttributedPhoto {

        public final CharSequence attribution;

        public final Bitmap bitmap;

        public AttributedPhoto(CharSequence attribution, Bitmap bitmap) {
            this.attribution = attribution;
            this.bitmap = bitmap;
        }
    }
}
