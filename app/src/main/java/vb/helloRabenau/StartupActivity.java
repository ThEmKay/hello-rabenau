package vb.helloRabenau;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.ProgressBar;

//import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;

import com.mapbox.mapboxsdk.MapboxAccountManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import vb.helloRabenau.helpers.AppSettings;
import vb.helloRabenau.helpers.DataContainer;
import vb.helloRabenau.helpers.DateTime;

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

            publishProgress((int) 40);

            MapboxAccountManager.start(getApplicationContext(), "pk.eyJ1IjoibWtheTg5IiwiYSI6ImNpdG92MDM0YjAwMWIyem55dmd0Mms0eGIifQ.ZIZ0Cq_lzvB6yMDjK-XOdQ");
            publishProgress((int) 60);

            DataContainer.getInstance(getApplicationContext());
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
