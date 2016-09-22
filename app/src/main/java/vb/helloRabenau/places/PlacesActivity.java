package vb.helloRabenau.places;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import vb.helloRabenau.R;
import vb.helloRabenau.helpers.DataContainer;
import vb.helloRabenau.helpers.Observer;

public class PlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        int icons[] = DataContainer.getInstance(getApplicationContext()).getPlacesCategoriesIcons();
        final String labels[] = DataContainer.getInstance(getApplicationContext()).getPlacesCategoriesLabels();


        if(labels.length == icons.length){

            GridView gridView = (GridView) findViewById(R.id.gridPlaces);

            gridView.setAdapter(new PlacesAdapter(this, icons, labels));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent cat = new Intent(getApplicationContext(), PlacesCategoryActivity.class);
                    //cat.putExtra("categoryId", position+1);
                    Observer.PlacesCategory = Integer.toString(position+1);
                    Observer.PlacesCategoryLabel = labels[position];

                    startActivity(cat);
                }
            });
        }





    }

}
