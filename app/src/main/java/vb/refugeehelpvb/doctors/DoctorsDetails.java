package vb.refugeehelpvb.doctors;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Calendar;

import vb.refugeehelpvb.R;
import vb.refugeehelpvb.calendar.CalendarActivity;
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

        // Datensatz des ausgewaehlten Doktors aus dem Cache holen
        int docId = getIntent().getIntExtra("id", 1);
        ArrayList<DoctorsContent> docs = DataContainer.getInstance().getDoctors(getIntent().getStringExtra("city"));
        DoctorsContent doc = docs.get(docId - 1);



        setContentView(R.layout.activity_doctors_details);


        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setClickable(true);
        mapView.setMaxZoomLevel(16);
        mapView.setMinZoomLevel(16);
        mapView.setTileSource(new XYTileSource("MapquestOSM", 0, 18, 256, ".png", new String[]{}));
        mapView.getController().setZoom(16);

        mapView.setUseDataConnection(false);
        mapView.setMultiTouchControls(true);

        GeoPoint geo = new GeoPoint(50.7507624,9.265958);
        if(doc.geo != null){
            geo = new GeoPoint(doc.geo[0], doc.geo[1]);
        }
        mapView.getController().setCenter(geo);




        // Put overlay icon a little way from map centre
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        OverlayItem flag = new OverlayItem("Here", "SampleDescription", geo);
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




        TextView address = (TextView) findViewById(R.id.txtAddress);
        address.setText(doc.adress+" "+doc.city);

        String uri;
        ImageView tempImg;
        if(doc.lang1 != 0){
            tempImg = (ImageView) findViewById(R.id.imgLanguage1);
            tempImg.setImageResource(doc.lang1);
            tempImg.setVisibility(View.VISIBLE);
        }
        if(doc.lang2 != 0){
            tempImg = (ImageView) findViewById(R.id.imgLanguage2);
            tempImg.setImageResource(doc.lang2);
            tempImg.setVisibility(View.VISIBLE);
        }
        if(doc.lang3 != 0){
            tempImg = (ImageView) findViewById(R.id.imgLanguage3);
            tempImg.setImageResource(doc.lang3);
            tempImg.setVisibility(View.VISIBLE);
        }


        TextView oH[] = new TextView[5];
        TextView temp = (TextView) findViewById(R.id.timeMonday);
        oH[0] = temp;
        temp = (TextView) findViewById(R.id.timeTuesday);
        oH[1] = temp;
        temp = (TextView) findViewById(R.id.timeWednesday);
        oH[2] = temp;
        temp = (TextView) findViewById(R.id.timeThursday);
        oH[3] = temp;
        temp = (TextView) findViewById(R.id.timeFriday);
        oH[4] = temp;

        // Kalendertag ermitteln
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);




        // MOCK FEIERTAGE
        int[][] hd = DataContainer.getInstance().holidays;



        // Ist heute ein Feiertag?
        Boolean holiday = false;
        int mon = cal.get(Calendar.MONTH);
        mon++;
        int da = cal.get(Calendar.DAY_OF_MONTH);
        for(int h = 0; h < hd.length; h++){
            if(hd[h][0] == mon && hd[h][1] == da){
                holiday = true;
                break;
            }
        }

        ImageView img;
        String hrs;
        if(doc.openHours != null){
            for(int i = 0; i < doc.openHours.length(); i++){
                try {
                    hrs = doc.openHours.getString(i);
                    if(hrs.equals("-")){
                        switch(i){
                            case 0: img = (ImageView) findViewById(R.id.imgMonday); break;
                            case 1: img = (ImageView) findViewById(R.id.imgTuesday); break;
                            case 2: img = (ImageView) findViewById(R.id.imgWednesday); break;
                            case 3: img = (ImageView) findViewById(R.id.imgThursday); break;
                            case 4: img = (ImageView) findViewById(R.id.imgFriday); break;
                            default: img = (ImageView) findViewById(R.id.imgMonday); break;
                        }
                        img.setAlpha(0.2f);
                        oH[i].setText(R.string.closed);
                        oH[i].setTextColor(Color.RED);
                    }else{
                        oH[i].setText(doc.openHours.getString(i));
                        // Wenn HEUTE und KEIN Feiertag, fett markieren und GRUEN hervorheben
                        if(day-2 == i){
                            if(!holiday) {
                                oH[i].setTextColor(getResources().getColor(R.color.green));
                                oH[i].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                            }else{
                                // Falls heute ein Feiertag ist, wird automatisch auf GESCHLOSSEN gesetzt und rot markiert
                                switch(i){
                                    case 0: img = (ImageView) findViewById(R.id.imgMonday); break;
                                    case 1: img = (ImageView) findViewById(R.id.imgTuesday); break;
                                    case 2: img = (ImageView) findViewById(R.id.imgWednesday); break;
                                    case 3: img = (ImageView) findViewById(R.id.imgThursday); break;
                                    case 4: img = (ImageView) findViewById(R.id.imgFriday); break;
                                    default: img = (ImageView) findViewById(R.id.imgMonday); break;
                                }
                                img.setAlpha(0.2f);
                                oH[i].setTextColor(Color.RED);
                                oH[i].setText(R.string.closed_holiday);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if(doc.phone != null){
            try{
                TextView phone = (TextView) findViewById(R.id.txtPhone);
                phone.setText(doc.phone);
            }catch(Exception e){}
        }

        getSupportActionBar().setTitle(doc.getName());




        System.out.println(doc.getName());

    }

}
