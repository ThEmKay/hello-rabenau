package vb.helloRabenau.doctors;

import org.json.JSONArray;

/**
 * Created by Seb on 18.12.2015.
 */
public class DoctorsContent {

    public String name;
    public String adress;
    public String city;
    public String id;
    public JSONArray openHours = null;
    public String phone = null;
    public int lang1 = 0;
    public int lang2 = 0;
    public int lang3 = 0;
    public double geo[] = null;


    public String getName(){
        return name;
    }

    public String getAdress(){
        return adress;
    }

    public String getCity(){
        return city;
    }

    public String getId(){
        return id;
    }

    public int getLang1(){
        return lang1;
    }

    public int getLang2(){
        return lang2;
    }

    public int getLang3(){
        return lang3;
    }
}
