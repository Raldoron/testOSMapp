package com.example.raldoron.testosmapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.overlays.FolderOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.bonuspack.overlays.Marker;

import java.util.ArrayList;

import oauth.signpost.OAuth;

/**
 * Created by Raldoron on 05.11.15.
 */
public class MapActivity extends ActionBarActivity implements  MapEventsReceiver {

    private static MyLocationNewOverlay myLocationNewOverlay;
    RadiusMarkerClusterer radiusMarkerClusterer;
    MapView mapView;
    AutoCompleteTextView poiTagText;

    private Drawer.Result drawerResult = null;

    final String TAG = getClass().getName();
    public static final int mRequestCode = 913;

    public GeoPoint testPoint;
    private static OSMApi _osmApi = OSMApi.getOsmApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mapView = (MapView) findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.setBuiltInZoomControls(true);

        myLocationNewOverlay = new MyLocationNewOverlay(this.getApplicationContext(), mapView);
        mapView.getOverlayManager().add(myLocationNewOverlay);

        IMapController mapController = mapView.getController();
        mapController.setZoom(10);
        GeoPoint startPoint = new GeoPoint(55.75, 37.61);
        mapController.setCenter(startPoint);


        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, this);
        mapView.getOverlays().add(mapEventsOverlay);

        FolderOverlay poisMarkers = new FolderOverlay(this);
        mapView.getOverlays().add(poisMarkers);

        radiusMarkerClusterer = new RadiusMarkerClusterer(this);
        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_poi_default);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();
        radiusMarkerClusterer.setIcon(clusterIcon);
        radiusMarkerClusterer.mAnchorV = Marker.ANCHOR_BOTTOM;
        radiusMarkerClusterer.mTextAnchorU = 0.70f;
        radiusMarkerClusterer.mTextAnchorV = 0.27f;
        radiusMarkerClusterer.getTextPaint().setTextSize(12.0f);
        mapView.getOverlays().add(radiusMarkerClusterer);


        String[] poiTags = getResources().getStringArray(R.array.poi_tags);
        poiTagText = (AutoCompleteTextView) findViewById(R.id.poiTag);
        ArrayAdapter<String> poiAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.hint_item, poiTags);
        poiTagText.setAdapter(poiAdapter);

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(poiTagText.getWindowToken(), 0);
                String feature = poiTagText.getText().toString();
                Toast.makeText(getApplicationContext(), "Searching:\n" + feature, Toast.LENGTH_LONG).show();
                getPOIAsync(feature);
            }
        });

        Button testButton = (Button) findViewById(R.id.button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Ontology.class);
                startActivity(i);
            }
        });
        //check user authorisation
        CheckAuthorisation checkAuthorisation = new CheckAuthorisation(this);
        //checkAuthorisation.check();
        // Инициализируем Navigation Drawer
        NavigationDrawerInit navigationDrawerInit = new NavigationDrawerInit(this, toolbar, this);
        if (checkAuthorisation.check()) {
            drawerResult = navigationDrawerInit.init();
        }else {
            drawerResult = navigationDrawerInit.initGuest();
        }

    }

    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
        Toast.makeText(this, "Tapped", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint geoPoint) {
        testPoint = new GeoPoint(geoPoint.getLatitudeE6(), geoPoint.getLongitudeE6());
        Toast.makeText(this, "Tap on ("+geoPoint.getLatitude()+","+geoPoint.getLongitude()+")", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if(requestCode == mRequestCode){
                Log.i(TAG, "OAuth - Success login!");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GpsMyLocationProvider gpsMyLocationProvider = new GpsMyLocationProvider(this);
        gpsMyLocationProvider.setLocationUpdateMinTime(3000);
        gpsMyLocationProvider.setLocationUpdateMinDistance(15);
        myLocationNewOverlay.enableMyLocation(gpsMyLocationProvider);
    }

    void getPOIAsync(String tag){
        radiusMarkerClusterer.getItems().clear();
        new POILoadingTask(this.getApplicationContext(), mapView, radiusMarkerClusterer).execute(tag);
    }
}
