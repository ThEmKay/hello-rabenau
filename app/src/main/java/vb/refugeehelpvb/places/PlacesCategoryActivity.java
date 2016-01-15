package vb.refugeehelpvb.places;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vb.refugeehelpvb.R;
import vb.refugeehelpvb.doctors.DoctorsDetails;
import vb.refugeehelpvb.helpers.CitySpinner;
import vb.refugeehelpvb.helpers.DataContainer;
import vb.refugeehelpvb.helpers.Observer;

public class PlacesCategoryActivity extends ActionBarActivity {

    // ArrayList mit Doktor-Objekten
    private ArrayList<PlacesContent> data;

    // ListView Adapter
    private PlacesCategoryAdapter adp;

    // Id der gewaehlten Kategorie
    private String categoryId;

    // Hilfe-Dialog
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_category);

        setTitle(Observer.PlacesCategoryLabel);
        categoryId = Observer.PlacesCategory;

        final Spinner selCity = CitySpinner.createCitySpinner(this);

        // ListView Element wird aus dem Layout geholt
        final ListView list = (ListView) findViewById(R.id.list);

        // Befuellen des Doktoren-Adapters
        data = new ArrayList<PlacesContent>();
        adp = new PlacesCategoryAdapter(this, data);
        // Adapter wird der ListView zugewiesen
        list.setAdapter(adp);

        // OnSelected Listener des Staedte Dropdown
         selCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Wenn ein neuer Ort ausgewaehlt wird, muessen neue Daten in die ListView geladen werden
                // Liste saeubern
                data.clear();
                // Neue Daten gemaess der Auswahl zur Liste hinzufuegen
                //data.addAll(DataContainer.getInstance().getPlaces(parent.getSelectedItem().toString().toLowerCase(), categoryId));
                try {
                    data.addAll(DataContainer.getInstance(getApplicationContext()).getPlacesCache(parent.getSelectedItem().toString().toLowerCase(), categoryId));
                }catch(NullPointerException e){
                    Toast.makeText(parent.getContext(), "Keine Daten vorhanden", Toast.LENGTH_LONG).show();
                }
                // Adapter aktualisiern
                adp.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Neues Intent erzeugen (Doktoren Details)
                Intent details = new Intent(getApplicationContext(), PlacesDetailsActivity.class);
                //TextView hidden = (TextView) view.findViewById(R.id.plaId);
                // Parameter an neues Intent haengen
                details.putExtra("placeId", position+1);
                details.putExtra("city", selCity.getSelectedItem().toString().toLowerCase());
                // Starten
                startActivity(details);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places_category, menu);

        builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.dialog_prices, null));
        builder.setIcon(R.drawable.ic_help);
        builder.setTitle("Hinweis");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.help){
            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }
}
