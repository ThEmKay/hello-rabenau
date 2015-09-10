package vb.refugeehelpvb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private ArrayList<MainContent> items;
    private static MyClickListener myClickListener;

    public static class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mainPhoto;
        TextView mainCategory;

        MainViewHolder(View itemView) {
            super(itemView);
            mainPhoto = (ImageView) itemView.findViewById(R.id.mainPhoto);
            mainCategory = (TextView) itemView.findViewById(R.id.mainCategory);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MainAdapter(ArrayList<MainContent> myItems) {
        items = myItems;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_main, parent, false);
        MainViewHolder mainViewHolder = new MainViewHolder(view);
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.mainPhoto.setImageResource(items.get(position).getPhotoId());
        holder.mainCategory.setText(items.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}
