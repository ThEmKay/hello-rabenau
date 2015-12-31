package vb.refugeehelpvb.doctors;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.identity.intents.AddressConstants;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.wearable.Asset;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.MapTileProviderArray;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.TilesOverlay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import vb.refugeehelpvb.R;
import vb.refugeehelpvb.helpers.DataContainer;

public class DoctorsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        //AssetManager as = getAssets();
        /*
        File folder = new File(Environment.getExternalStorageDirectory() + "/osmdroid");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
            Log.i("MAPDATEN", "Ordner wird erstellt");
        }
        if (success) {

            Log.i("MAPDATEN", "Werden kopiert");
            try {
                InputStream in = as.open("MapquestOSM.zip");
                OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/osmdroid/MapquestOSM.zip");

                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;

                Log.i("MAPDATEN", "Fertig kopiert!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    */


        setContentView(R.layout.activity_doctors_details);


        MapView mapView = (MapView) findViewById(R.id.mapview);

        mapView.setClickable(true);
         //displaying the MapView
        mapView.setMaxZoomLevel(16);
        mapView.setMinZoomLevel(16);
        mapView.setTileSource(new XYTileSource("MapquestOSM", 0, 18, 256, ".png", new String[]{}));

        mapView.getController().setZoom(16); //set initial zoom-level, depends on your need
        mapView.getController().setCenter(new GeoPoint(50.7507624,9.265958));



        mapView.setUseDataConnection(false);
        mapView.setMultiTouchControls(true);
        //setContentView(mapView);


        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        // Put overlay icon a little way from map centre
        OverlayItem flag = new OverlayItem("Here", "SampleDescription", new GeoPoint(50.7507624,9.265958));
        flag.setMarker(getResources().getDrawable(R.drawable.ic_destination));
        items.add(flag);

        final ResourceProxy mResourceProxy = new DefaultResourceProxyImpl(getApplicationContext());



        ItemizedIconOverlay mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index,
                                                     final OverlayItem item) {
                        return true; // We 'handled' this event.
                    }
                    @Override
                    public boolean onItemLongPress(final int index,
                                                   final OverlayItem item) {
                        return false;
                    }
                }, mResourceProxy);


        mapView.getOverlays().add(mMyLocationOverlay);
        mapView.invalidate();




        int docId = getIntent().getIntExtra("id", 1);

        ArrayList<DoctorsContent> docs = DataContainer.getInstance().getDoctors(getIntent().getStringExtra("city"));

        DoctorsContent doc = docs.get(docId - 1);

        System.out.println(doc.openHours);

        if(doc.openHours != null){
            for(int i = 0; i < doc.openHours.length(); i++){
                try {
                    System.out.println(doc.openHours.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }

        getSupportActionBar().setTitle(doc.getName());




        System.out.println(doc.getName());

    }

}
