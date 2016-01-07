package vb.refugeehelpvb;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;

import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import vb.refugeehelpvb.helpers.AppSettings;
import vb.refugeehelpvb.helpers.DataContainer;
import vb.refugeehelpvb.helpers.DateTime;

public class StartupActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        progressBar = (ProgressBar) findViewById(R.id.startupProgress);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        progressBar.getLayoutParams().width = size.x-50;
        progressBar.invalidate();
        new Startup().execute();
    }

    private class Startup extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params) {

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            AppSettings.setLanguage(getApplicationContext(), Locale.getDefault().getDisplayLanguage());
            AppSettings.loadCitySetting(getApplicationContext());
            publishProgress((int) 20);

            AssetManager as = getAssets();
            File folder = new File(Environment.getExternalStorageDirectory() + "/osmdroid");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
                Log.i("MAPDATEN", "Ordner wird erstellt");
            }
            if (success) {

                Log.i("MAPDATEN", "Werden kopiert");
                try {
                    InputStream in = as.open("MapquestOSM.zip");
                    OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/osmdroid/MapquestOSM.zip");

                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }

                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;

                    Log.i("MAPDATEN", "Fertig kopiert!");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            publishProgress((int) 40);

            OpenStreetMapTileProviderConstants.setOfflineMapsPath(Environment.getExternalStorageDirectory() + "/osmdroid");
            OpenStreetMapTileProviderConstants.setCachePath(Environment.getExternalStorageDirectory() + "/osmdroid");
            publishProgress((int) 60);

            DataContainer.init(getApplicationContext());
            publishProgress((int) 80);

            DateTime.getInstance();
            publishProgress((int) 100);

            return "Loaded";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

}
