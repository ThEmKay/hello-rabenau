package vb.refugeehelpvb.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;

import vb.refugeehelpvb.R;
import vb.refugeehelpvb.doctors.DoctorsContent;
import vb.refugeehelpvb.places.PlacesContent;

/**
 * Created by Seb on 20.12.2015.
 */
public class DataContainer{

    public static String FILE_INTERN_DOCTORS = "doctors.json";
    public static String FILE_INTERN_PLACES = "places.json";
    public static String FILE_INTERN_SIGNATURE = "src_signature.txt";

    private static DataContainer d = null;

    private static Context co = null;

    // Icons und Bezeichnungen der Kategorien fuer die Uebersicht der Orte (Places)
    private static int placesCategoriesIcons[];
    private static String placesCategoriesLabels[];

    private static JSONObject doctorsJson = null;

    private static JSONObject placesJson = null;

    private static boolean dataInStorage = true;

    private DataContainer(){}

    public static void init(Context c){
        if(d == null){
            co = c;
            FileOutputStream fo;

            // Pruefen, ob die Daten bereits auf dem Geraet gespeichert sind (FALSE Normalerweise nur beim ersten Start der App)
            File file = new File(co.getFilesDir().getAbsolutePath() + "/" + FILE_INTERN_SIGNATURE);

            if(!file.exists()){
                // Falls die Datensignatur intern noch nicht existiert, wird sie gleich der Signatur des Auslieferungszustandes gesetzt (ISO Datum YYYYMMDD)
                try {
                    saveInternal(FILE_INTERN_SIGNATURE);
                    saveInternal(FILE_INTERN_DOCTORS);
                    saveInternal(FILE_INTERN_PLACES);
                }catch(Exception e){}
            }

            readSrcSignature();
            readDoctorsJson();
            readPlacesJson();

            int i = 0;
            placesCategoriesIcons = new int[7];
            placesCategoriesIcons[i] = R.drawable.ic_dining;
            placesCategoriesIcons[++i] = R.drawable.ic_clothes;
            placesCategoriesIcons[++i] = R.drawable.ic_local_pharmacy_black_48dp;
            placesCategoriesIcons[++i] = R.drawable.ic_education;
            placesCategoriesIcons[++i] = R.drawable.ic_sports;
            placesCategoriesIcons[++i] = R.drawable.ic_playground;
            placesCategoriesIcons[++i] = R.drawable.ic_internet;

            placesCategoriesLabels = c.getResources().getStringArray(R.array.places);


        }
    }

    public static void refresh(){
        d.readDoctorsJson();
        Log.i("UPDATE", "CONTAINER aktualisiert");
        System.out.println(dataInStorage);
    }

    public static DataContainer getInstance(){
        if(d == null){
            d = new DataContainer();
        }
        return d;
    }

    private static void readSrcSignature(){
        AppSettings.sourceSignature = Integer.parseInt(loadFromInternal(FILE_INTERN_SIGNATURE));
    }

    private static void readDoctorsJson(){

        try{
            if(dataInStorage){
                // Falls die Daten bereits im Geraete-Storage gespeichert sind, direkt aus dieser Quelle laden

                doctorsJson = new JSONObject(loadFromInternal(FILE_INTERN_DOCTORS));

                Log.i("DOCTORS JSON", "Aus internem Speicher laden");

                System.out.println(doctorsJson);

            }else{
                // Falls die Daten nicht im Storage sind, werden diese jetzt gespeichert (nur beim erstmaligen Ausfuehren)
                // Dabei kommen die Anfangsdaten aus dem bei Installation enthaltenen Datenbestand
                doctorsJson = new JSONObject(saveInternal(FILE_INTERN_DOCTORS));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("JSON READ DOCTORS", "FAIL!");
            Toast.makeText(co, R.string.error_read_json, Toast.LENGTH_LONG).show();
        }
    }

    private static void readPlacesJson(){

        try{

            placesJson = new JSONObject(saveInternal(FILE_INTERN_PLACES));
            /*
            if(dataInStorage){
                // Falls die Daten bereits im Geraete-Storage gespeichert sind, direkt aus dieser Quelle laden

                placesJson = new JSONObject(loadFromInternal(FILE_INTERN_PLACES));

                Log.i("PLACES JSON", "Aus internem Speicher laden");
                System.out.println(placesJson);

            }else{
                // Falls die Daten nicht im Storage sind, werden diese jetzt gespeichert (nur beim erstmaligen Ausfuehren)
                // Dabei kommen die Anfangsdaten aus dem bei Installation enthaltenen Datenbestand
                placesJson = new JSONObject(saveInternal(FILE_INTERN_PLACES));
            }*/

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("JSON READ PLACES", "FAIL!");
            Toast.makeText(co, R.string.error_read_json, Toast.LENGTH_LONG).show();
        }
    }

    private static String saveInternal(String src){

        String finalString = "";
        try{
            InputStream is = co.getAssets().open(src);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // In den internen Speicher uebernehmen
            finalString = new String(buffer, "UTF-8");
            FileOutputStream fos = co.openFileOutput(src, Context.MODE_PRIVATE);
            fos.write(finalString.getBytes());
            fos.close();

        }catch(Exception e){
            Toast.makeText(co, R.string.error_read_json, Toast.LENGTH_LONG).show();
        }

        return finalString.toString();
    }

    private static String loadFromInternal(String src){

        Log.i("LOAD INTERNAL", src);

        StringBuilder finalString = new StringBuilder();
        try {
            FileInputStream fis = co.openFileInput(src);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                finalString.append(line);
            }

        }catch(Exception e){
            Toast.makeText(co, R.string.error_read_json, Toast.LENGTH_LONG).show();
        }

        return finalString.toString();
    }

    public ArrayList<DoctorsContent> getDoctors(String city){

        ArrayList<DoctorsContent> data = new ArrayList<DoctorsContent>();

        try {
            JSONArray docs = doctorsJson.getJSONArray(city.toLowerCase());

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
                }else if(lang.length() == 3){
                    uri = "@drawable/"+lang.getString(0);
                    doc.lang1 = co.getResources().getIdentifier(uri, null, co.getPackageName());
                    uri = "@drawable/"+lang.getString(1);
                    doc.lang2 = co.getResources().getIdentifier(uri, null, co.getPackageName());
                    uri = "@drawable/"+lang.getString(2);
                    doc.lang3 = co.getResources().getIdentifier(uri, null, co.getPackageName());
                }

                data.add(doc);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(co, R.string.error_read_json, Toast.LENGTH_LONG).show();
        }

        return data;

    }


    public ArrayList<PlacesContent> getPlaces(String city, int category){

        ArrayList<PlacesContent> data = new ArrayList<PlacesContent>();

        System.out.println(placesJson);

        try {
            JSONArray places = placesJson.getJSONObject(city.toLowerCase()).getJSONArray(Integer.toString(category));

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

        return data;

    }


    public int[] getPlacesCategoriesIcons(){
        return placesCategoriesIcons;
    }

    public String[] getPlacesCategoriesLabels(){
        return placesCategoriesLabels;
    }


}
