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
            case "barbershop": holder.plaLogo.setImageResource(R.drawable.ic_barbershop); break;
            case "boccia": holder.plaLogo.setImageResource(R.drawable.ic_boccia); break;
            case "bowling": holder.plaLogo.setImageResource(R.drawable.ic_bowling); break;
            case "busstop": holder.plaLogo.setImageResource(R.drawable.ic_busstop); break;
            case "cross": holder.plaLogo.setImageResource(R.drawable.ic_cross); break;
            case "golf": holder.plaLogo.setImageResource(R.drawable.ic_golf); break;
            case "gu": holder.plaLogo.setImageResource(R.drawable.ic_gu); break;
            case "kindergarden": holder.plaLogo.setImageResource(R.drawable.ic_kindergarden); break;
            case "office": holder.plaLogo.setImageResource(R.drawable.ic_office); break;
            case "playground": holder.plaLogo.setImageResource(R.drawable.ic_playground); break;
            case "postbox": holder.plaLogo.setImageResource(R.drawable.ic_postbox); break;
            case "school": holder.plaLogo.setImageResource(R.drawable.ic_school); break;
            case "shirtshop": holder.plaLogo.setImageResource(R.drawable.ic_shirtshop); break;
            case "soccer": holder.plaLogo.setImageResource(R.drawable.ic_soccer); break;
            case "tennis": holder.plaLogo.setImageResource(R.drawable.ic_tennis); break;
            case "pharmacy": holder.plaLogo.setImageResource(R.drawable.ic_pharmacy); break;
            case "hall": holder.plaLogo.setImageResource(R.drawable.ic_hall); break;
            case "museum": holder.plaLogo.setImageResource(R.drawable.ic_museum); break;
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
