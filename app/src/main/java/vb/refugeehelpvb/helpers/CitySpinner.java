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

        for(int i = 0; i < adapter.getCount(); i++){
            if(city.equals(adapter.getItem(i).toString())){
                selCity.setSelection(i);
                break;
            }
        }

        return selCity;

    }




}
