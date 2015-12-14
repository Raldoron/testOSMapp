package com.example.raldoron.testosmapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.location.OverpassAPIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.utils.BonusPackHelper;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Raldoron on 14.11.15.
 */
public class POILoadingTask extends AsyncTask<Object, Void, ArrayList<POI>> {
    private Context mContext;

    RadiusMarkerClusterer mRadiusMarker;
    private MapView mMapView;
    private BoundingBoxE6 boundingBoxE6;
    public static ArrayList<POI> mPOIs;

    String mFeatureTag;
    String message;

    public POILoadingTask(Context context, MapView mapView, RadiusMarkerClusterer radiusMarkerClusterer){
        mContext = context;
        mMapView = mapView;
        boundingBoxE6 = mapView.getBoundingBox();
        mRadiusMarker = radiusMarkerClusterer;
    }

    /**
     * Convert human readable feature to an OSM tag.
     * @param humanReadableFeature
     * @return OSM tag string: "k=v"
     */
    String getOSMTag(String humanReadableFeature){
        HashMap<String,String> map = BonusPackHelper.parseStringMapResource(mContext, R.array.osm_poi_tags);
        return map.get(humanReadableFeature.toLowerCase(Locale.getDefault()));
    }
    @Override
    protected ArrayList<POI> doInBackground(Object... params) {
        mFeatureTag = (String) params[0];
        OverpassAPIProvider overpassAPIProvider = new OverpassAPIProvider();
        String osmTag = getOSMTag(mFeatureTag);
        if (osmTag == null){
            message = mFeatureTag + " is not a valid feature.";
            return null;
        }
        String oUrl = overpassAPIProvider.urlForPOISearch(osmTag, boundingBoxE6, 100, 10);
        ArrayList<POI> pois = overpassAPIProvider.getPOIsFromUrl(oUrl);
        return pois;
    }

    @Override
    protected void onPostExecute(ArrayList<POI> pois) {
        mPOIs = pois;
        if (mFeatureTag == null || mFeatureTag.equals("")){
            //no search, no message
        } else if (mPOIs == null){
            if (message != null)
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(mContext, "Technical issue when getting "+ mFeatureTag + " POI.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, mFeatureTag + " found:"+mPOIs.size(), Toast.LENGTH_LONG).show();
        }
        updateUIWithPOI(mPOIs, mFeatureTag);
    }

    void updateUIWithPOI(ArrayList<POI> pois, String featureTag){
        if (pois != null) {
            for (POI poi : pois) {
                if (poi != null) {
                    Marker poiMarker = new Marker(mMapView);
                    poiMarker.setTitle(poi.mType);
                    poiMarker.setSnippet(poi.mDescription);
                    poiMarker.setPosition(poi.mLocation);
                    Drawable icon = mContext.getResources().getDrawable(R.drawable.marker_poi_default);
                    poiMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
                    poiMarker.setIcon(icon);
                    poiMarker.setRelatedObject(poi);
                    mRadiusMarker.add(poiMarker);
                }
            }
        }
        mRadiusMarker.setName(featureTag);
        mRadiusMarker.invalidate();
        mMapView.invalidate();
        //Log.e("App", "updateUIWithPOI map invalidate");

    }

}
