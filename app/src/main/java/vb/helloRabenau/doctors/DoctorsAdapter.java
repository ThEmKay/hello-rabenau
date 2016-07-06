package vb.helloRabenau.doctors;

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
public class DoctorsAdapter extends BaseAdapter {

    private ArrayList<DoctorsContent> datalist;

    private LayoutInflater inflater;

    public DoctorsAdapter(Context context, ArrayList<DoctorsContent> data){
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
            convertView = inflater.inflate(R.layout.activity_doctors_list_item, null);
            holder = new Holder();
            holder.docName = (TextView) convertView.findViewById(R.id.doctorName);
            holder.docId = (TextView) convertView.findViewById(R.id.doctorId);
            holder.docAdress = (TextView) convertView.findViewById(R.id.doctorAdress);
            holder.docCity = (TextView) convertView.findViewById(R.id.doctorCity);
            holder.docLang1 = (ImageView) convertView.findViewById(R.id.imgLanguage1);
            holder.docLang2 = (ImageView) convertView.findViewById(R.id.imgLanguage2);
            holder.docLang3 = (ImageView) convertView.findViewById(R.id.imgLanguage3);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.docName.setText(datalist.get(position).getName());
        holder.docId.setText(datalist.get(position).getId());
        holder.docAdress.setText(datalist.get(position).getAdress());
        holder.docCity.setText(datalist.get(position).getCity());
        holder.docLang1.setImageResource(datalist.get(position).getLang1());
        holder.docLang2.setImageResource(datalist.get(position).getLang2());
        holder.docLang3.setImageResource(datalist.get(position).getLang3());

        return convertView;
    }

    static class Holder{
        TextView docName;
        TextView docId;
        TextView docAdress;
        TextView docCity;
        ImageView docLang1;
        ImageView docLang2;
        ImageView docLang3;
    }
}
