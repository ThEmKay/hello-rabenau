package vb.refugeehelpvb.places;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vb.refugeehelpvb.R;

/**
 * Created by Seb on 27.12.2015.
 */
public class PlacesAdapter extends BaseAdapter {

    Context c;
    int[] icons;
    String[] labels;

    public PlacesAdapter(Context c, int[] icons, String[] labels){
        this.c = c;
        this.icons = icons;
        this.labels = labels;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.activity_places_grid_item, null);

            // set value into textview
            TextView label = (TextView) gridView.findViewById(R.id.grid_item_label);
            label.setText(labels[position]);

            ImageView icon = (ImageView) gridView.findViewById(R.id.places_item_icon);
            icon.setImageResource(icons[position]);




            // set image based on selected text
            //ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);


            /*
            String mobile = mobileValues[position];

            if (mobile.equals("Windows")) {
                imageView.setImageResource(R.drawable.windows_logo);
            } else if (mobile.equals("iOS")) {
                imageView.setImageResource(R.drawable.ios_logo);
            } else if (mobile.equals("Blackberry")) {
                imageView.setImageResource(R.drawable.blackberry_logo);
            } else {
                imageView.setImageResource(R.drawable.android_logo);
            }*/

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }


    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
