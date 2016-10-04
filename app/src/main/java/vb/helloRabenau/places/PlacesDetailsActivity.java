package vb.helloRabenau.places;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import vb.helloRabenau.R;
import vb.helloRabenau.helpers.DataContainer;
import vb.helloRabenau.helpers.DateTime;
import vb.helloRabenau.helpers.MapBuilder;
import vb.helloRabenau.helpers.Observer;

/**
 * Detailansicht der Orte mit allen relevanten Daten inkl. Oeffnungszeiten und Karte
 *
 * @author Seb
 */
public class PlacesDetailsActivity extends AppCompatActivity {

    // Info-Dialog
    private AlertDialog.Builder builder;

    private PlacesContent place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_details);

        // ##################
        // Datensatz des ausgewaehlten Ortes aus dem Cache holen
        Intent in = getIntent();
        ArrayList<PlacesContent> places = DataContainer.getInstance(getApplicationContext()).getPlacesCache(in.getStringExtra("city"), Observer.PlacesCategory);
        PlacesContent place = places.get(in.getIntExtra("placeId", 1) - 1);
        this.place = place;

        // ##################
        // Titel der Activity gleich Bezeichnung des Ortes setzen
        setTitle(place.title);

        // ##################
        // Adresse setzen
        TextView plaAddress = (TextView) findViewById(R.id.txtAddress);
        plaAddress.setText(place.address + " " + place.city);

        System.out.println(place.geo[0]);

        // ##################
        // Container fuer die Wochentage holen
        TextView oH[] = new TextView[7];
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
        temp = (TextView) findViewById(R.id.timeSaturday);
        oH[5] = temp;
        temp = (TextView) findViewById(R.id.timeSunday);
        oH[6] = temp;

        // Kalendertag ermitteln
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // Ist der heutige Tag ein Feiertag?
        Boolean holiday = DateTime.getInstance().isHoliday();

        ImageView img;
        String hrs;
        // Wenn Oeffnungszeiten angegeben sind
        if(place.openHours != null){
            // Alle durchlaufen (i.d.R. 5 =] )
            for(int i = 0; i < place.openHours.length(); i++){
                try {
                    hrs = place.openHours.getString(i);
                    // Ist ein Tag mit geschlossen markiert ODER
                    // ist der Tag HEUTE
                    if(hrs.equals("-") || (day-2 == i && holiday)){
                        switch(i){
                            case 0: img = (ImageView) findViewById(R.id.imgMonday); break;
                            case 1: img = (ImageView) findViewById(R.id.imgTuesday); break;
                            case 2: img = (ImageView) findViewById(R.id.imgWednesday); break;
                            case 3: img = (ImageView) findViewById(R.id.imgThursday); break;
                            case 4: img = (ImageView) findViewById(R.id.imgFriday); break;
                            case 5: img = (ImageView) findViewById(R.id.imgSaturday); break;
                            case 6: img = (ImageView) findViewById(R.id.imgSunday); break;
                            default: img = (ImageView) findViewById(R.id.imgMonday); break;
                        }
                        img.setAlpha(0.2f);
                        oH[i].setTextColor(Color.RED);
                        if(day-2 == i && holiday){
                            oH[i].setText(R.string.closed_holiday);
                        }else{
                            oH[i].setText(R.string.closed);
                        }
                    }else{
                        oH[i].setText(place.openHours.getString(i));
                        // Wenn HEUTE und KEIN Feiertag sowie NICHT geschlossen, fett markieren und GRUEN hervorheben
                        if(day-2 == i){
                            oH[i].setTextColor(getResources().getColor(R.color.green));
                            oH[i].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // ##################
        // Map einzeichnen
        final PlacesContent placeMap = this.place;
        MapView mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                CameraPosition pos = new CameraPosition.Builder().target(new LatLng(placeMap.geo[0], placeMap.geo[1])).zoom(15).build();
                mapboxMap.setCameraPosition(pos);
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(placeMap.geo[0], placeMap.geo[1]))
                        .title(placeMap.title)
                        .snippet(placeMap.address));


            }
        });

    }

    // Menubar Info-Icon Funktionalität
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(place.info.length() > 1){
            getMenuInflater().inflate(R.menu.menu_places_details, menu);
        }

        return true;
    }

    // Bei Click auf das Info-Icon Diaog öffnen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_place_info, null);
        TextView infoText = (TextView) customView.findViewById(R.id.placeInfo);
        infoText.setText(this.place.info);

        builder = new AlertDialog.Builder(this);
        builder.setView(customView);
        builder.setIcon(R.drawable.ic_info_white_48dp);
        builder.setTitle("Info");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        if(item.getItemId() == R.id.placeInfoDialog){

            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }
}