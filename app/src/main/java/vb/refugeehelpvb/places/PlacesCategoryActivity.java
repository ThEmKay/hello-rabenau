package vb.refugeehelpvb.places;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import vb.refugeehelpvb.R;
import vb.refugeehelpvb.helpers.CitySpinner;
import vb.refugeehelpvb.helpers.DataContainer;

public class PlacesCategoryActivity extends ActionBarActivity {

    // ArrayList mit Doktor-Objekten
    private ArrayList<PlacesContent> data;

    // ListView Adapter
    private PlacesCategoryAdapter adp;

    // Id der gewaehlten Kategorie
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_category);

        setTitle(getIntent().getStringExtra("categoryLabel"));
        categoryId = getIntent().getIntExtra("categoryId", 0);

        Spinner selCity = CitySpinner.createCitySpinner(this);

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
                data.addAll(DataContainer.getInstance().getPlaces(parent.getSelectedItem().toString().toLowerCase(), categoryId));
                // Adapter aktualisiern
                adp.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
