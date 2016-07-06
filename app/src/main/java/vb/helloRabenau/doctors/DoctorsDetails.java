package vb.helloRabenau.doctors;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import vb.helloRabenau.R;
import vb.helloRabenau.helpers.DataContainer;
import vb.helloRabenau.helpers.DateTime;
import vb.helloRabenau.helpers.MapBuilder;

/**
 * Detailansicht der Doktoren. Enthaelt alle relevanten Daten inkl. Oeffnungszeiten
 * und Offline-Faehige Karte mit der markierten Position der Praxis.
 *
 * @author Seb
 *
 */
public class DoctorsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_details);



        // ##################
        // Datensatz des ausgewaehlten Doktors aus dem Cache holen
        int docId = getIntent().getIntExtra("id", 1);
        ArrayList<DoctorsContent> docs = DataContainer.getInstance(getApplicationContext()).getDoctors(getIntent().getStringExtra("city"));
        DoctorsContent doc = docs.get(docId - 1);

        // ##################
        // Titel der Activity = Name der Arztpraxis
        getSupportActionBar().setTitle(doc.getName());

        // ##################
        // Adresse setzen
        TextView address = (TextView) findViewById(R.id.txtAddress);
        address.setText(doc.adress+" "+doc.city);

        // ##################
        // Icons fuer die gesprochenen Sprachen hinzufügen
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

        // ##################
        // Container fuer die Wochentage holen
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
        // Ist der heutige Tag ein Feiertag?
        Boolean holiday = DateTime.getInstance().isHoliday();

        ImageView img;
        String hrs;
        // Wenn Oeffnungszeiten angegeben sind
        if(doc.openHours != null){
            // Alle durchlaufen (i.d.R. 5 =] )
            for(int i = 0; i < doc.openHours.length(); i++){
                try {
                    hrs = doc.openHours.getString(i);
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
                        oH[i].setText(doc.openHours.getString(i));
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
        // Telefonnummer setzen
        if(doc.phone != null){
            try{
                TextView phone = (TextView) findViewById(R.id.txtPhone);
                phone.setText(doc.phone);
            }catch(Exception e){}
        }

        // ##################
        // Map ins Layout zaubern :)
        ViewGroup layout = (ViewGroup) findViewById(R.id.layoutDoctorsDetails);
        MapBuilder map = new MapBuilder(getApplicationContext());
        if(doc.geo != null){
            map.setGeoPoint(doc.geo[0], doc.geo[1]);
            map.centerMap(doc.geo[0], doc.geo[1]);
        }
        layout.addView(map.drawMap());

    }
}