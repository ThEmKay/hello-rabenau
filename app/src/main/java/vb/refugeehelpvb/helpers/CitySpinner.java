package vb.refugeehelpvb.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import vb.refugeehelpvb.R;

/**
 * Created by Seb on 19.12.2015.
 */
public class CitySpinner {


    public static Spinner createCitySpinner(Activity a){

        SharedPreferences settings = a.getSharedPreferences("Settings", 0);
        String city = settings.getString("city", "");

        Spinner selCity = (Spinner) a.findViewById(R.id.selCity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(a.getApplicationContext(), R.array.cities, R.layout.spinner_item);

        selCity.setAdapter(adapter);

        // Wenn im Activity-Context (z.B. Aerzte) eine bestimmte Stadt gewahelt wurde, soll beim zurueck-
        // navigieren diese wieder ausgewahelt sein. Falls nicht wird die Heimatstadt aus den Settings genommen
        String preSelectCity = city;
        if(!Observer.previousSelectedCity.equals("")) {
            preSelectCity = Observer.previousSelectedCity;
        }

        for(int i = 0; i < adapter.getCount(); i++){
            if(preSelectCity.toLowerCase().equals(adapter.getItem(i).toString().toLowerCase())){
                selCity.setSelection(i);
                break;
            }
        }

        return selCity;

    }




}
