package vb.refugeehelpvb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<MainContent> items;

    public CardAdapter(){
        super();
        items = new ArrayList<MainContent>();

        MainContent mc = new MainContent();
        mc.setName("Medical");
        //mc.setName(Resources.getSystem().getString(R.string.medical));
        mc.setThumbnail(R.drawable.heartbeat);
        items.add(mc);

        mc = new MainContent();
        mc.setName("Transit");
        //mc.setName(Resources.getSystem().getString(R.string.transit));
        mc.setThumbnail(R.drawable.train);
        items.add(mc);

        mc = new MainContent();
        mc.setName("Calendar");
        //mc.setName(Resources.getSystem().getString(R.string.calendar));
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
