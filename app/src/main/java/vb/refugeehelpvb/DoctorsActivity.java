package vb.refugeehelpvb;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import static vb.refugeehelpvb.ResourceProvider.translate;

/**
 * Created by Felix on 10.09.2015.
 */
public class DoctorsActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
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
        mAdapter = new DoctorsAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((DoctorsAdapter) mAdapter).setOnItemClickListener(new DoctorsAdapter.MyClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, "Clicked doctor " + position);

            }
        });
    }

    private ArrayList<DoctorsContent> getDataSet() {
        ArrayList results = new ArrayList<DoctorsContent>();

        DoctorsContent dc = new DoctorsContent();
        dc.setPhotoId(R.drawable.hospital);
        dc.setName(translate(R.string.doctor1));
        dc.setAddress(translate(R.string.doctor1_address));
        dc.setLocation(translate(R.string.doctor1_location));
        dc.setLanguage1(R.drawable.flag_rus);
        dc.setLanguage2(R.drawable.flag_fra);
        dc.setLanguage3(R.drawable.flag_gbr);
        results.add(dc);

        dc = new DoctorsContent();
        dc.setPhotoId(R.drawable.hospital);
        dc.setName(translate(R.string.doctor2));
        dc.setAddress(translate(R.string.doctor2_address));
        dc.setLocation(translate(R.string.doctor2_location));
        dc.setLanguage1(R.drawable.flag_gbr);
        results.add(dc);

        return results;
    }
}

