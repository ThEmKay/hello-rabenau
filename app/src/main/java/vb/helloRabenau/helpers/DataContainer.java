package vb.helloRabenau.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import vb.helloRabenau.R;
import vb.helloRabenau.doctors.DoctorsContent;
import vb.helloRabenau.places.PlacesContent;

/**
 * Created by Seb on 20.12.2015.
 */
public class DataContainer extends Cache{

    public static String FILE_INTERN_DOCTORS = "doctors.json";
    public static String FILE_INTERN_PLACES = "places.json";
    public static String FILE_INTERN_SIGNATURE = "src_signature.txt";

    private static DataContainer d = null;

    private static Context co = null;

    // Icons und Bezeichnungen der Kategorien fuer die Uebersicht der Orte (Places)
    private static int placesCategoriesIcons[];
    private static String placesCategoriesLabels[];

    private static String cities[];

    private static boolean dataInStorage = true;

    private DataContainer(){}

    private static void init(Context c){
        if(d == null){
            co = c;
            FileOutputStream fo;

            // Pruefen, ob die Daten bereits auf dem Geraet gespeichert sind (FALSE normalerweise nur beim ersten Start der App)
            File file = new File(co.getFilesDir().getAbsolutePath() + "/" + FILE_INTERN_SIGNATURE);

            if(!file.exists()){
                // Falls die Datensignatur intern noch nicht existiert, wird sie gleich der Signatur des Auslieferungszustandes gesetzt (ISO Datum YYYYMMDD)
                try {
                    saveInternal(FILE_INTERN_SIGNATURE);
                    saveInternal(FILE_INTERN_DOCTORS);
                    saveInternal(FILE_INTERN_PLACES);
                }catch(Exception e){}
            }

            int i = 0;
            placesCategoriesIcons = new int[13];
            placesCategoriesIcons[i] = R.drawable.ic_clothes;
            placesCategoriesIcons[++i] = R.drawable.ic_education;
            placesCategoriesIcons[++i] = R.drawable.ic_dining;
            placesCategoriesIcons[++i] = R.drawable.ic_pharmacy;
            placesCategoriesIcons[++i] = R.drawable.ic_playground;
            placesCategoriesIcons[++i] = R.drawable.ic_sports;
            placesCategoriesIcons[++i] = R.drawable.ic_internet;
            placesCategoriesIcons[++i] = R.drawable.ic_home;
            placesCategoriesIcons[++i] = R.drawable.ic_haircut;
            placesCategoriesIcons[++i] = R.drawable.ic_bible;
            placesCategoriesIcons[++i] = R.drawable.ic_internet;
            placesCategoriesIcons[++i] = R.drawable.ic_finance;
            placesCategoriesIcons[++i] = R.drawable.ic_internet;

            placesCategoriesLabels = c.getResources().getStringArray(R.array.places);

            cities = c.getResources().getStringArray(R.array.cities);

            readSrcSignature();
            readDoctorsJson();
            readPlacesJson();

        }
    }

    public static void refresh(){
        d.readDoctorsJson();
        d.readPlacesJson();

        Log.i("UPDATE", "CONTAINER aktualisiert");
    }

    public static DataContainer getInstance(Context c){
        if(d == null){
            DataContainer dtmp = new DataContainer();
            dtmp.init(c);
            d = dtmp;
        }
        return d;
    }

    private static void readSrcSignature(){
        AppSettings.sourceSignature = Integer.parseInt(loadFromInternal(FILE_INTERN_SIGNATURE));
    }

    /**
     * Auslesen der Doktoren - JSON Daten
     */
    private static void readDoctorsJson(){
        JSONObject doctorsJson;
        try{
            if(dataInStorage){
                // Falls die Daten bereits im Geraete-Storage gespeichert sind, direkt aus dieser Quelle laden
                //doctorsJson = new JSONObject(loadFromInternal(FILE_INTERN_DOCTORS));
                doctorsJson = new JSONObject(saveInternal(FILE_INTERN_DOCTORS));
                Log.i("DOCTORS JSON", "Aus internem Speicher laden");

            }else{
                // Falls die Daten nicht im Storage sind, werden diese jetzt gespeichert (nur beim erstmaligen Ausfuehren)
                // Dabei kommen die Anfangsdaten aus dem bei Installation enthaltenen Datenbestand
                doctorsJson = new JSONObject(saveInternal(FILE_INTERN_DOCTORS));
            }
            // Caching der Doktoren-Daten (JSON wird so nur einmalig gelesen)

            System.out.println("++++++++++++++++++++++++++++++++++++++");
            System.out.print(doctorsJson);
            System.out.println("++++++++++++++++++++++++++++++++++++++");

            cacheDoctors(doctorsJson, cities, co);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("JSON READ DOCTORS", "FAIL!");
            Toast.makeText(co, R.string.error_read_json, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Auslesen der Orte - JSON Daten
     */
    private static void readPlacesJson(){

        try{

            JSONObject placesJson = new JSONObject(saveInternal(FILE_INTERN_PLACES));

            String mockCities[] = new String[1];
            mockCities[0] = "londorf";

            int cats = 12;
            String mockCategories[] = new String[cats+1];
            for(int i = 0; i <= cats; i++){

                mockCategories[i] = Integer.toString(i+1);

            }

            // Caching der Orte-Daten (JSON wird so nur einmalig gelesen)
            cachePlaces(placesJson, mockCities, mockCategories, co);


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

    /**
     * Rückgabe der Doktoren einer gewahelten Stadt/Gemeinde aus dem Cache
     *
     * @param city
     * @return ArrayList
     */
    public ArrayList<DoctorsContent> getDoctors(String city) {
        return doctorsCache.get(city);
    }

    /**
     * Rückgabe der Orte einer gewahelten Stadt/Gemeinde aus dem Cache
     *
     * @param city
     * @param category
     * @return ArrayList
     */
    public ArrayList<PlacesContent> getPlacesCache(String city, String category){
        //return placesCache.get("alsfeld").get("1");
        return placesCache.get(city).get(category);
    }

    public int[] getPlacesCategoriesIcons(){
        return placesCategoriesIcons;
    }

    public String[] getPlacesCategoriesLabels(){
        return placesCategoriesLabels;
    }


}
