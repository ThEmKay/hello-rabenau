package vb.refugeehelpvb.places;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import vb.refugeehelpvb.R;
import vb.refugeehelpvb.helpers.DataContainer;
import vb.refugeehelpvb.helpers.DateTime;
import vb.refugeehelpvb.helpers.MapBuilder;
import vb.refugeehelpvb.helpers.Observer;

/**
 * Detailansicht der Orte mit allen relevanten Daten inkl. Oeffnungszeiten und Karte
 *
 * @author Seb
 */
public class PlacesDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_details);

        // ##################
        // Datensatz des ausgewaehlten Ortes aus dem Cache holen
        Intent in = getIntent();
        ArrayList<PlacesContent> places = DataContainer.getInstance(getApplicationContext()).getPlacesCache(in.getStringExtra("city"), Observer.PlacesCategory);
        PlacesContent place = places.get(in.getIntExtra("placeId", 1) - 1);

        // ##################
        // Titel der Activity gleich Bezeichnung des Ortes setzen
        setTitle(place.title);

        // ##################
        // Adresse setzen
        TextView plaAddress = (TextView) findViewById(R.id.txtAddress);
        plaAddress.setText(place.address + " " + place.city);

        // ##################
        // Container fuer die Wochentage holen
        TextView oH[] = new TextView[6];
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
        ViewGroup layout = (ViewGroup) findViewById(R.id.layoutPlacesDetails);
        MapBuilder map = new MapBuilder(getApplicationContext());
        map.setGeoPoint(50.7507624,9.265958);
        layout.addView(map.drawMap());

    }
}