package vb.refugeehelpvb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import static vb.refugeehelpvb.ResourceProvider.translate;


public class MainActivity extends ActionBarActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        ResourceProvider.getInstance(getApplicationContext());
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MainAdapter) mAdapter).setOnItemClickListener(new MainAdapter.MyClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, "Clicked Category " + position);
                switch (position) {
                    case 0:
                        startActivity(new Intent(v.getContext(), DoctorsActivity.class));
                        break;
                }

            }
        });
    }

    private ArrayList<MainContent> getDataSet() {
        ArrayList results = new ArrayList<MainContent>();

        MainContent mc = new MainContent();
        mc.setPhotoId(R.drawable.heartbeat);
        mc.setCategory(translate(R.string.medical));
        results.add(mc);

        mc = new MainContent();
        mc.setPhotoId(R.drawable.train);
        mc.setCategory(translate(R.string.transit));
        results.add(mc);

        mc = new MainContent();
        mc.setPhotoId(R.drawable.calendar);
        mc.setCategory(translate(R.string.calendar));
        results.add(mc);

        return results;
    }

    /*
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}