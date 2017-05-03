package com.example.asd_1.myapplication;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.android.gms.location.places.Place;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by asd-1 on 12/4/2016.
 */
public class StreetViewPhotoTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
    Place place;
    ArrayList<Bitmap> bmArray = new ArrayList<>();
    VrPanoramaOverlay parent;

    String panoID;


    public StreetViewPhotoTask(ArrayList<Bitmap> bmArray, VrPanoramaOverlay parent, String panoID){
        this.bmArray = bmArray;
        this.parent = parent;
        this.panoID = panoID;
    }
    @Override
    protected ArrayList<Bitmap> doInBackground(String... params) {

        panoID = params[0];
        for (int i = 0; i <= 12; i += 1) {
            for (int j = 0; j <= 25; j += 1) {
                Bitmap bitmap;
                try {
                    URL url = new URL("http://cbk0.google.com/cbk?output=tile&panoid=" + panoID + "&zoom=5&x=" + j + "&y=" + i);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        options.inSampleSize = 4;
                        bitmap = BitmapFactory.decodeStream(in, null, options);
                        in.close();
                        bmArray.add(bitmap);
                    } finally {

                        urlConnection.disconnect();
                    }
                } catch (Exception e) {

                }
            }

        }


        return bmArray;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
        super.onPostExecute(bitmaps);
        parent.combineBitmaps(bitmaps);
    }
}
