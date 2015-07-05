package vb.refugeehelpvb;

import android.content.Context;

/**
 * Created by Seb on 27.06.2015.
 */
class ResourceProvider{

    private static ResourceProvider rp = null;

    private static Context con = null;

    private ResourceProvider(Context ac){
        con = ac;
    }

    public static ResourceProvider getInstance(Context ac){
        if(rp == null){
            rp = new ResourceProvider(ac);
        }
        return rp;
    }

    public static String translate(int res){
        return con.getString(res);
    }

}
