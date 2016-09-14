package vb.helloRabenau.helpers;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import vb.helloRabenau.doctors.DoctorsContent;
import vb.helloRabenau.places.PlacesContent;

/**
 * Created by Seb on 31.12.2015.
 */
public class Cache {

    protected static HashMap<String, ArrayList<DoctorsContent>> doctorsCache = new HashMap<String, ArrayList<DoctorsContent>>();

    protected static HashMap<String, HashMap<String, ArrayList<PlacesContent>>> placesCache = new HashMap<String, HashMap<String, ArrayList<PlacesContent>>>();
    private static   HashMap<String, ArrayList<PlacesContent>> placesInner = null;

    protected static void cacheDoctors(JSONObject o, String[] s, Context co){

        Log.i("CACHE", "DOKTOREN");
        ArrayList<DoctorsContent> data;
        for(int j = 0; j < s.length; j++){

            data = new ArrayList<DoctorsContent>();
            try {
                JSONArray docs = o.getJSONArray(s[j].toLowerCase());
                // Alle Doktoren auslesen
                DoctorsContent doc;
                JSONObject d;
                for(int i = 0; i < docs.length(); i++){
                    d = docs.getJSONObject(i);

                    doc = new DoctorsContent();
                    doc.name = d.getString("title");
                    doc.adress = d.getString("address");
                    doc.city = d.getString("location");
                    doc.id = d.getString("id");

                    if(!d.isNull("openhours")){
                        doc.openHours = d.getJSONArray("openhours");
                    }
                    if(!d.isNull("phone")){
                        doc.phone = d.getString("phone");
                    }
                    if(!d.isNull("geo")){
                        doc.geo = new double[2];
                        JSONArray a = d.getJSONArray("geo");
                        doc.geo[0] = a.getDouble(0);
                        doc.geo[1] = a.getDouble(1);
                    }

                    String uri;
                    JSONArray lang = d.getJSONArray("languages");
                    if(lang.length() == 1){
                        uri = "@drawable/"+lang.getString(0);
                        doc.lang1 = co.getResources().getIdentifier(uri, null, co.getPackageName());
                    }else if(lang.length() == 2){
                        uri = "@drawable/"+lang.getString(0);
                        doc.lang1 = co.getResources().getIdentifier(uri, null, co.getPackageName());
                        uri = "@drawable/"+lang.getString(1);
                        doc.lang2 = co.getResources().getIdentifier(uri, null, co.getPackageName());
                    }else if(lang.length() == 3) {
                        uri = "@drawable/" + lang.getString(0);
                        doc.lang1 = co.getResources().getIdentifier(uri, null, co.getPackageName());
                        uri = "@drawable/" + lang.getString(1);
                        doc.lang2 = co.getResources().getIdentifier(uri, null, co.getPackageName());
                        uri = "@drawable/" + lang.getString(2);
                        doc.lang3 = co.getResources().getIdentifier(uri, null, co.getPackageName());
                    }
                    data.add(doc);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("CACHE", "DOKTOR JSON VERARBEITEN");
            }
            doctorsCache.put(s[j].toLowerCase(), data);
        }
        Log.i("CACHE", "DOKTOREN BEENDET");
    }



    protected static void cachePlaces(JSONObject o, String[] city, String[] category, Context co){

        Log.i("CACHE", "PLACES");
        ArrayList<PlacesContent> data;

        // Aeusseres Element der Map (Stadt/Gemeinde)
        for(int i = 0; i < city.length; i++){

            placesInner = new HashMap<String, ArrayList<PlacesContent>>();

            Log.i("CACHE", "PLACES: "+city[i]);
            for(int j = 0; j < category.length; j++){
                try {
                    Log.i("CACHE", "PLACES KATEGORIE: "+category[j]);

                    data = new ArrayList<PlacesContent>();
                    JSONArray places = o.getJSONObject(city[i].toLowerCase()).getJSONArray(category[j]);

                    PlacesContent pla;
                    JSONObject p;
                    for(int k = 0; k < places.length(); k++){
                        p = places.getJSONObject(k);

                        pla = new PlacesContent();
                        pla.title = p.getString("title");
                        pla.address = p.getString("address");
                        pla.city = p.getString("city");
                        pla.logo = p.getString("logo");

                        pla.price = p.getInt("free");

                        if(!p.isNull("openhours")){
                            pla.openHours = p.getJSONArray("openhours");
                        }

                        data.add(pla);
                    }

                    placesInner.put(category[j], data);


                }catch(Exception e){

                }
            }

            placesCache.put(city[i], placesInner);
        }

        Log.i("CACHE", "PLACES BEENDET");

/*
        try {
            JSONArray places = o.getJSONObject(city.toLowerCase()).getJSONArray(Integer.toString(category));

            // Alle Adressen auslesen
            PlacesContent pla;
            JSONObject p;
            for(int i = 0; i < places.length(); i++){
                p = places.getJSONObject(i);

                pla = new PlacesContent();
                pla.title = p.getString("title");
                pla.address = p.getString("address");
                pla.city = p.getString("city");
                pla.logo = p.getString("logo");

                data.add(pla);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(co, R.string.error_read_json, Toast.LENGTH_LONG).show();
        }

        return data;*/
    }


}
