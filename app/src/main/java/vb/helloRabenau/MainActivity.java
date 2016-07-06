package vb.helloRabenau;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import vb.helloRabenau.calendar.CalendarActivity;
import vb.helloRabenau.doctors.DoctorsActivity;
import vb.helloRabenau.emergency.EmergencyActivity;
import vb.helloRabenau.helpers.ResourceProvider;
import vb.helloRabenau.places.PlacesActivity;
import vb.helloRabenau.publictransport.PublicTransportActivity;

import static vb.helloRabenau.helpers.ResourceProvider.translate;


public class MainActivity extends ActionBarActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // App-Settings laden: Sprache und Hintergrundbild setzen
        //AppSettings.setAppBackground(getApplicationContext());


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        ResourceProvider.getInstance(getApplicationContext());
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        ((MainAdapter) mAdapter).setOnItemClickListener(new MainAdapter.MyClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, "Clicked Category " + position);
                switch (position) {
                    case 0:
                        startActivity(new Intent(v.getContext(), DoctorsActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(v.getContext(), PlacesActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(v.getContext(), PublicTransportActivity.class));
                        break;/*
                    case 3:
                        startActivity(new Intent(v.getContext(), CalendarActivity.class));
                        break;*/
                    case 3:
                        startActivity(new Intent(v.getContext(), EmergencyActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }

    private ArrayList<MainContent> getDataSet() {
        ArrayList results = new ArrayList<MainContent>();

        MainContent mc = new MainContent();
        mc.setPhotoId(R.drawable.ic_medical);
        mc.setCategory(translate(R.string.title_activity_doctors));
        mc.setBaselineColor(Color.parseColor("#AA66CC"));
        results.add(mc);

        mc = new MainContent();
        mc.setPhotoId(R.drawable.ic_places);
        mc.setCategory(translate(R.string.title_activity_places));
        mc.setBaselineColor(Color.parseColor("#FFBB33"));
        results.add(mc);

        mc = new MainContent();
        mc.setPhotoId(R.drawable.ic_transit);
        mc.setCategory(translate(R.string.title_activity_public_transport));
        mc.setBaselineColor(Color.parseColor("#99CC00"));
        results.add(mc);

        /*
        mc = new MainContent();
        mc.setPhotoId(R.drawable.ic_events);
        mc.setCategory(translate(R.string.title_activity_calendar));
        mc.setBaselineColor(getResources().getColor(R.color.CalendarPrimary));
        results.add(mc);
        */

        mc = new MainContent();
        mc.setPhotoId(R.drawable.ic_emergency);
        mc.setCategory(translate(R.string.title_activity_emergency));
        mc.setBaselineColor(Color.parseColor("#CC0000"));
        results.add(mc);

        return results;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}