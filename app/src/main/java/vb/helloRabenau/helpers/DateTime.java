package vb.helloRabenau.helpers;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Seb on 02.01.2016.
 */
public class DateTime {

    private static DateTime dt = null;

    private ArrayList<Integer[]> holidays = new ArrayList<Integer[]>();

    private DateTime(){}

    private void init(){
        // Feste Feiertage hinzufügen
        Integer[] date = new Integer[2];
        date[0] = 1;
        date[1] = 1;
        holidays.add(date);
        date = new Integer[2];
        date[0] = 5;
        date[1] = 1;
        holidays.add(date);
        date = new Integer[2];
        date[0] = 10;
        date[1] = 3;
        holidays.add(date);
        date = new Integer[2];
        date[0] = 12;
        date[1] = 25;
        holidays.add(date);
        date = new Integer[2];
        date[0] = 12;
        date[1] = 26;
        holidays.add(date);

    }

    public Boolean isHoliday(){
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int mon = cal.get(Calendar.MONTH);

        Integer[] hd;
        for(int h = 0; h < holidays.size(); h++){
            hd = holidays.get(h);
            if(hd[0] == mon+1 && hd[1] == day){
                return true;
            }
        }
        return false;
    }


    public static DateTime getInstance(){
        if(dt == null) {
            dt = new DateTime();
            dt.init();
        }
        return dt;
    }




}
