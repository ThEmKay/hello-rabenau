package vb.refugeehelpvb.doctors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import vb.refugeehelpvb.R;
import vb.refugeehelpvb.helpers.DataContainer;

import static vb.refugeehelpvb.helpers.CitySpinner.createCitySpinner;

/**
 *  Activity-Klasse der Doktoren Uebersicht. Je nach eingestelltem Heimatort werden
 *  alle Doktoren in der unmittelbaren Umgebung in einer Liste angezeigt. Innerhalb
 *  der Aktivity gibt es ebenfalls ein Dropdown-Menu zur Auswahl eines anderen Ortes.
 *
 *  @author Seb
 *  @date 20151228
 *
 */
public class DoctorsActivity extends AppCompatActivity {

    // ArrayList mit Doktor-Objekten
    private ArrayList<DoctorsContent> data;

    // ListView Adapter
    private DoctorsAdapter adp;

    /*
     * Einstiegspunkt dieser Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        // ListView Element wird aus dem Layout geholt
        final ListView list = (ListView) findViewById(R.id.list);

        // Befuellen des Doktoren-Adapters
        data = new ArrayList<DoctorsContent>();
        adp = new DoctorsAdapter(this, data);
        // Adapter wird der ListView zugewiesen
        list.setAdapter(adp);

        // Staedte Dropdown Menu laden
        final Spinner selCity = createCitySpinner(this);

        // OnSelected Listener des Staedte Dropdown
        selCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Wenn ein neuer Ort ausgewaehlt wird, muessen neue Daten in die ListView geladen werden
                // Liste saeubern
                data.clear();
                // Neue Daten gemaess der Auswahl zur Liste hinzufuegen
                data.addAll(DataContainer.getInstance().getDoctors(parent.getSelectedItem().toString().toLowerCase()));
                // Adapter aktualisiern
                adp.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // OnClick auf einen Eintrag aus der Doktoren-Liste
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Neues Intent erzeugen (Doktoren Details)
                Intent details = new Intent(getApplicationContext(), DoctorsDetails.class);
                TextView hidden = (TextView) view.findViewById(R.id.doctorId);
                // Parameter an neues Intent haengen
                details.putExtra("id", Integer.parseInt(hidden.getText().toString()));
                details.putExtra("city", selCity.getSelectedItem().toString().toLowerCase());
                // Starten
                startActivity(details);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doctors, menu);
        return true;
    }

}