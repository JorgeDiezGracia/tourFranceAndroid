package com.svalero.tourfrance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tourfrance.R;
import com.svalero.tourfrance.model.Stage;

import java.util.List;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.StageViewHolder> {

    private List<Stage> stages;
    private Context context;

    public StageAdapter(Context context, List<Stage> stages) {
        this.context = context;
        this.stages = stages;
    }

    @NonNull
    @Override
    public StageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stage, parent, false);
        return new StageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StageViewHolder holder, int position) {
        Stage stage = stages.get(position);
        holder.tvRoute.setText(stage.getDeparture() + " → " + stage.getArrival());
        holder.tvType.setText(stage.getType());
        holder.tvKm.setText(stage.getKilometers() + " km");
        holder.tvDate.setText(stage.getStageDate());
        holder.tvElevation.setText(stage.getElevation() + " m");
    }

    @Override
    public int getItemCount() {
        return stages != null ? stages.size() : 0;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
        notifyDataSetChanged();
    }

    public static class StageViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoute, tvType, tvKm, tvDate, tvElevation;

        public StageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoute = itemView.findViewById(R.id.tv_stage_route);
            tvType = itemView.findViewById(R.id.tv_stage_type);
            tvKm = itemView.findViewById(R.id.tv_stage_km);
            tvDate = itemView.findViewById(R.id.tv_stage_date);
            tvElevation = itemView.findViewById(R.id.tv_stage_elevation);
        }
    }
}