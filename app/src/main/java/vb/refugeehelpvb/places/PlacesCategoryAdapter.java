package vb.refugeehelpvb.places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import vb.refugeehelpvb.R;

/**
 * Created by Seb on 18.12.2015.
 */
public class PlacesCategoryAdapter extends BaseAdapter {

    private ArrayList<PlacesContent> datalist;

    private LayoutInflater inflater;

    public PlacesCategoryAdapter(Context context, ArrayList<PlacesContent> data){
        this.datalist = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.activity_places_category_list_item, null);
            holder = new Holder();
            holder.plaTitle = (TextView) convertView.findViewById(R.id.plaTitle);
            holder.plaAddress = (TextView) convertView.findViewById(R.id.plaAddress);
            holder.plaCity = (TextView) convertView.findViewById(R.id.plaCity);
            holder.plaLogo = (ImageView) convertView.findViewById(R.id.plaLogo);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.plaTitle.setText(datalist.get(position).title);
        holder.plaAddress.setText(datalist.get(position).address);
        holder.plaCity.setText(datalist.get(position).city);

        switch(datalist.get(position).logo){
            case "tafel": holder.plaLogo.setImageResource(R.drawable.logo_tafel_alsfeld); break;
            case "aldi_nord": holder.plaLogo.setImageResource(R.drawable.logo_aldinord); break;
            case "aldi_sued": holder.plaLogo.setImageResource(R.drawable.logo_aldisued); break;
            case "herkules": holder.plaLogo.setImageResource(R.drawable.logo_herkules); break;
            case "lidl": holder.plaLogo.setImageResource(R.drawable.logo_lidl); break;
            case "penny": holder.plaLogo.setImageResource(R.drawable.logo_penny); break;
            case "tegut": holder.plaLogo.setImageResource(R.drawable.logo_tegut); break;
            default: holder.plaLogo.setImageResource(R.drawable.logo_aldisued); break;
        }

        return convertView;
    }

    static class Holder{
        TextView plaTitle;
        TextView docId;
        TextView plaAddress;
        TextView plaCity;
        ImageView plaLogo;
        ImageView plaAttr1;
        ImageView plaAttr2;
        ImageView docLang3;
    }
}
