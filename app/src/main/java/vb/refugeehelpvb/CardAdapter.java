package vb.refugeehelpvb;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static vb.refugeehelpvb.ResourceProvider.translate;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<MainContent> items;

    public CardAdapter(Context app){

        items = new ArrayList<MainContent>();

        MainContent mc = new MainContent();

        mc.setThumbnail(R.drawable.heartbeat);
        mc.setName(translate(R.string.medical));
        items.add(mc);

        mc = new MainContent();
        mc.setName(translate(R.string.transit));
        mc.setThumbnail(R.drawable.train);
        items.add(mc);

        mc = new MainContent();
        mc.setName(translate(R.string.calendar));
        mc.setThumbnail(R.drawable.calendar);
        items.add(mc);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MainContent mc = items.get(i);
        viewHolder.tvTitle.setText(mc.getName());
        viewHolder.imgThumbnail.setImageResource(mc.getThumbnail());
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_name);
        }
    }

}
