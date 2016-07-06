package vb.helloRabenau;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import vb.helloRabenau.helpers.AppSettings;
import vb.helloRabenau.helpers.DataContainer;

/**
 * Created by Seb on 21.12.2015.
 */
public class UpdateTask extends AsyncTask<String, Integer, String> {

    Context c = null;

    ProgressDialog d;

    Boolean updateResultFlag = true;

    private TextView serv;
    private TextView ok;
    private Button upd;
    private AlertDialog ad;

    private int progress = 0;

    private int updateStatus = 0;

    public UpdateTask(Context c, ProgressDialog d, TextView serv, TextView ok, Button upd, AlertDialog ad){
        this.c = c;
        this.d = d;
        this.serv = serv;
        this.ok = ok;
        this.upd = upd;
        this.ad = ad;
    }

    @Override
    protected String doInBackground(String[] url) {

        try{
            URL u = new URL(url[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();

            if(urlConnection.getResponseCode() == 200){

                InputStreamReader is = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(is);
                int updateSignature = Integer.parseInt(br.readLine());

                if(updateSignature <= AppSettings.sourceSignature){
                    updateStatus = 1;
                }else{

                    updateStatus = 2;

                    URL uDocs = new URL(url[1]);
                    HttpURLConnection updateDocs = (HttpURLConnection) uDocs.openConnection();

                    InputStreamReader id = new InputStreamReader(updateDocs.getInputStream());
                    BufferedReader brd = new BufferedReader(id);
                    int fileLength = updateDocs.getContentLength();

                    String line;
                    StringBuilder fString = new StringBuilder();
                    while((line = brd.readLine()) != null){

                        progress = progress+line.getBytes().length;
                        publishProgress((int) (progress * 100 / fileLength));
                        fString.append(line);

                    }
                    updateDocs.disconnect();
                    Log.i("UPDATE", "Download Doktoren-Daten erfolgreich.");

                    JSONObject testOb;
                    // Pruefen des Doktoren-JSON
                    try{
                        Log.i("UPDATE", "Erzeuge Test JSON");
                        testOb = new JSONObject(fString.toString());
                        testOb.has("alsfeld");
                    }catch(Exception e){
                        Log.i("F-UPDATE Doktoren", "JSON malformed.");
                        updateResultFlag = false;
                    }

                    if(updateResultFlag){
                        // JSON Daten sind OK. Datei kann ersetzt werden
                        Log.i("UPDATE", "Doktoren starten");
                        FileOutputStream fos = c.openFileOutput(DataContainer.FILE_INTERN_DOCTORS, Context.MODE_PRIVATE);
                        fos.write(fString.toString().getBytes());
                        fos.close();

                        AppSettings.sourceSignature = updateSignature;
                        fos = c.openFileOutput(DataContainer.FILE_INTERN_SIGNATURE, Context.MODE_PRIVATE);
                        fos.write(Integer.toString(updateSignature).getBytes());
                        fos.close();

                        Log.i("UPDATE", "DataContainer erneuern");
                        DataContainer.refresh();
                    }

                }

            }else{

                updateStatus = 0;
            }


        }catch(Exception e){
            System.out.println(e.toString());
        }

        return "";

    }


    @Override
    protected void onProgressUpdate(Integer... p){
        super.onProgressUpdate(p);
        d.setIndeterminate(false);
        d.setMax(100);
        d.setProgress(p[0]);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        switch(updateStatus){
            // Keine Serververbindung moeglich
            case 0: serv.setVisibility(View.VISIBLE);
                    Toast.makeText(c, "Bitte versuchen Sie es zu einem spaeteren Zeitpunkt noch einmal", Toast.LENGTH_LONG).show();
                    break;
            // Geraet ist auf dem neusten Stand!
            case 1: ok.setVisibility(View.VISIBLE);
                    ad.setMessage("Die Daten sind bereits auf dem neuesten Stand.");
                    ad.show();
                    serv.setHeight(0);
                    break;
            case 2: if(updateResultFlag){
                        ad.show();
                        serv.setHeight(0);
                        ok.setVisibility(View.VISIBLE);
                    }else{
                        ad.setMessage("Update konnte nicht durchgeuehrt werden. Bitte versuchen Sie es spaeter noch einmal.");
                        ad.show();
                    }
                    break;
        }

        d.dismiss();


    }
}
