package com.svalero.tourfrance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tourfrance.R;
import com.svalero.tourfrance.model.Climb;

import java.util.List;

public class ClimbAdapter extends RecyclerView.Adapter<ClimbAdapter.ClimbViewHolder> {

    private List<Climb> climbs;
    private Context context;

    public ClimbAdapter(Context context, List<Climb> climbs) {
        this.context = context;
        this.climbs = climbs;
    }

    @NonNull
    @Override
    public ClimbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_climb, parent, false);
        return new ClimbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClimbViewHolder holder, int position) {
        Climb climb = climbs.get(position);
        holder.tvName.setText(climb.getName());
        holder.tvCategory.setText("Categoría: " + climb.getCategory());
        holder.tvAltitude.setText(climb.getAltitude() + " m");
        holder.tvRegion.setText(climb.getRegion());
        holder.tvSlope.setText(climb.getSlope() + "% pendiente");
    }

    @Override
    public int getItemCount() {
        return climbs != null ? climbs.size() : 0;
    }

    public void setClimbs(List<Climb> climbs) {
        this.climbs = climbs;
        notifyDataSetChanged();
    }

    public static class ClimbViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCategory, tvAltitude, tvRegion, tvSlope;

        public ClimbViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_climb_name);
            tvCategory = itemView.findViewById(R.id.tv_climb_category);
            tvAltitude = itemView.findViewById(R.id.tv_climb_altitude);
            tvRegion = itemView.findViewById(R.id.tv_climb_region);
            tvSlope = itemView.findViewById(R.id.tv_climb_slope);
        }
    }
}
