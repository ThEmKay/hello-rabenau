package vb.helloRabenau.places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vb.helloRabenau.R;

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
            holder.plaCost = (ImageView) convertView.findViewById(R.id.imgFree);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.plaTitle.setText(datalist.get(position).title);
        holder.plaAddress.setText(datalist.get(position).address);
        holder.plaCity.setText(datalist.get(position).city);

        switch(datalist.get(position).price){
            case 0: holder.plaCost.setImageResource(R.drawable.ic_pic_free); break;
            case 1: holder.plaCost.setImageResource(R.drawable.ic_pic_price1); break;
            case 2: holder.plaCost.setImageResource(R.drawable.ic_pic_price2); break;
            case 3: holder.plaCost.setImageResource(R.drawable.ic_pic_price3); break;
        }

        switch(datalist.get(position).logo){
            case "bakery": holder.plaLogo.setImageResource(R.drawable.ic_bread); break;
            case "butcher": holder.plaLogo.setImageResource(R.drawable.ic_meat); break;
            case "liquor": holder.plaLogo.setImageResource(R.drawable.ic_liquor); break;
            case "market": holder.plaLogo.setImageResource(R.drawable.ic_store); break;
            default: holder.plaLogo.setImageResource(R.drawable.ic_store); break;
        }

        return convertView;
    }

    static class Holder{
        TextView plaTitle;
        TextView docId;
        TextView plaAddress;
        TextView plaCity;
        ImageView plaLogo;
        ImageView plaCost;
        ImageView plaAttr2;
        ImageView docLang3;
    }
}
