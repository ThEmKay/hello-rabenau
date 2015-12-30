package vb.refugeehelpvb.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import java.util.Locale;

import vb.refugeehelpvb.R;

/**
 * Created by Seb on 21.12.2015.
 */
public class AppSettings {

    public static int sourceSignature = 0;

    public static String city = "";

    //public static int appBackground = R.drawable.bg_alsfeld1;
    //public static boolean appBackgroundChangedFlag = false;

    public static boolean checkOnline(Activity c){
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null){
            return netInfo.isConnected();
        }
        return false;
    }

    public static void setLanguage(Context c, String l){
        if(l.length() == 2){
            Locale myLocale = new Locale(l);
            Resources res = c.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
    }

    public static void loadCitySetting(Context c){
        SharedPreferences settings = null;
        settings = c.getSharedPreferences("Settings", 0);
        city = settings.getString("city", "");
    }

    /*
    public static void setAppBackground(Context c){

        if(city.equals(c.getResources().getString(R.string.lauterbach))){
            appBackground = R.drawable.bg_lauterbach1;
        }else if(city.equals(c.getResources().getString(R.string.alsfeld))){
            appBackground = R.drawable.bg_alsfeld1;
        }
    }
    */


}
