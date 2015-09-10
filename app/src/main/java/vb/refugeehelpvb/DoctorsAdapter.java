package vb.refugeehelpvb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Felix on 10.09.2015.
 */
public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    private ArrayList<DoctorsContent> items;
    private static MyClickListener myClickListener;

    public static class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView doctorPhoto;
        TextView doctorName;
        TextView doctorAddress;
        TextView doctorLocation;
        ImageView doctorLanguage1;
        ImageView doctorLanguage2;
        ImageView doctorLanguage3;

        DoctorViewHolder(View itemView){
            super(itemView);
            doctorPhoto = (ImageView) itemView.findViewById(R.id.doctorPhoto);
            doctorName = (TextView) itemView.findViewById(R.id.doctorName);
            doctorAddress = (TextView) itemView.findViewById(R.id.doctorAddress);
            doctorLocation = (TextView) itemView.findViewById(R.id.doctorLocation);
            doctorLanguage1 = (ImageView) itemView.findViewById(R.id.doctorLanguage1);
            doctorLanguage2 = (ImageView) itemView.findViewById(R.id.doctorLanguage2);
            doctorLanguage3 = (ImageView) itemView.findViewById(R.id.doctorLanguage3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener){
        this.myClickListener = myClickListener;
    }
    public DoctorsAdapter(ArrayList<DoctorsContent> myItems){
        items = myItems;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_doctors, viewGroup, false);
        DoctorViewHolder doctorViewHolder = new DoctorViewHolder(view);
        return doctorViewHolder;
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder doctorViewHolder, int i) {
        doctorViewHolder.doctorPhoto.setImageResource(items.get(i).getPhotoId());
        doctorViewHolder.doctorName.setText(items.get(i).getName());
        doctorViewHolder.doctorAddress.setText(items.get(i).getAddress());
        doctorViewHolder.doctorLocation.setText(items.get(i).getLocation());
        doctorViewHolder.doctorLanguage1.setImageResource(items.get(i).getLanguage1());
        doctorViewHolder.doctorLanguage2.setImageResource(items.get(i).getLanguage2());
        doctorViewHolder.doctorLanguage3.setImageResource(items.get(i).getLanguage3());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}
