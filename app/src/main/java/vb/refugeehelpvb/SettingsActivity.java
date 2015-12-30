package vb.refugeehelpvb;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import vb.refugeehelpvb.helpers.AppSettings;


public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String PREF_SETTINGS = "Settings";

    private SharedPreferences settings = null;

    private Activity a;

    private String city = "";

    ProgressDialog mProgressDialog;
    AlertDialog alertDialog;
    Button upd;
    TextView off;
    TextView ok;
    TextView serv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = getSharedPreferences(PREF_SETTINGS, 0);
        AppSettings.city = settings.getString("city", "");
        //AppSettings.setAppBackground(this);

        Spinner selCity = (Spinner) findViewById(R.id.selCity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, R.layout.spinner_item);

        selCity.setOnItemSelectedListener(this);
        selCity.setAdapter(adapter);

        for(int i = 0; i < adapter.getCount(); i++){
            if(AppSettings.city.equals(adapter.getItem(i).toString())){
                selCity.setSelection(i);
                break;
            }
        }

        upd = (Button) findViewById(R.id.btnUpdate);
        off = (TextView) findViewById(R.id.lblOffline);
        ok = (TextView) findViewById(R.id.lblDataOk);
        serv = (TextView) findViewById(R.id.lblServerOff);

        if(AppSettings.checkOnline(this)){
            // Wenn eine Internetverbindung besteht
            upd.setVisibility(Button.VISIBLE);
            off.setHeight(0);

            a = this;

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Update");
            alertDialogBuilder
                    .setMessage("Daten wurden erfolgreich aktualisiert.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });

            alertDialog = alertDialogBuilder.create();

            upd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mProgressDialog = new ProgressDialog(a);
                    mProgressDialog.setMessage("A message");
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();

                    upd.setClickable(false);
                    upd.setAlpha(0.4F);

                    String[] url = new String[2];
                    url[0] = "http://heteria.de/rhvb/api/medical/signature";
                    url[1] = "http://heteria.de/rhvb/api/medical/doctors";

                    final UpdateTask update = new UpdateTask(getApplicationContext(), mProgressDialog, serv, ok, upd, alertDialog);
                    update.execute(url);

                    mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            update.cancel(true);
                        }
                    });

                }
            });



        }else{
            // Wenn KEINE Internetverbindung besteht
            off.setVisibility(TextView.VISIBLE);
            upd.setHeight(0);
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object o = parent.getItemAtPosition(position);

        System.out.println("STADT AENDERN - "+o.toString());
        city = o.toString();
        //AppSettings.setAppBackground(this.a.getApplicationContext());
        //AppSettings.appBackgroundChangedFlag = true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    protected void onStop(){
        super.onStop();

        AppSettings.city = city;

        settings = getSharedPreferences(PREF_SETTINGS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("city", city);

        editor.commit();
    }

}
