package vb.helloRabenau.helpers;

/**
 * Created by Seb on 01.01.2016.
 */
public class Observer {

    public static String PlacesCategory;
    public static String PlacesCategoryLabel;

    // Wird gesetzt, wenn die Detailansicht eines Doktors geoeffnet wird
    // Kehrt man zurueck, bleibt die Auswahl auf der zuvor gewaehlten Ortschaft
    // Danach wird der String wieder geleert
    public static String previousSelectedCity = "";

    public static boolean dataLoaded(){
        /*if(DataContainer.getInstance(getApp).getDoctors("alsfeld") != null){
            return true;
        }*/
        return true;
    }

}
