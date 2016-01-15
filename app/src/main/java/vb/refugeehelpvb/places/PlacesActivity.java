package vb.refugeehelpvb.places;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.HashMap;

import vb.refugeehelpvb.R;
import vb.refugeehelpvb.helpers.DataContainer;
import vb.refugeehelpvb.helpers.Observer;

import static vb.refugeehelpvb.helpers.CitySpinner.createCitySpinner;

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
