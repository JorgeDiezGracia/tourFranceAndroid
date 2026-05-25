package com.svalero.tourfrance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tourfrance.R;
import com.svalero.tourfrance.model.Cyclist;

import java.util.List;

public class CyclistAdapter extends RecyclerView.Adapter<CyclistAdapter.CyclistViewHolder> {

    private List<Cyclist> cyclists;
    private Context context;
    private OnCyclistClickListener listener;

    public interface OnCyclistClickListener {
        void onCyclistClick(Cyclist cyclist);
        void onCyclistLongClick(Cyclist cyclist);
    }

    public CyclistAdapter(Context context, List<Cyclist> cyclists, OnCyclistClickListener listener) {
        this.context = context;
        this.cyclists = cyclists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CyclistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cyclist, parent, false);
        return new CyclistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CyclistViewHolder holder, int position) {
        Cyclist cyclist = cyclists.get(position);

        holder.tvName.setText(cyclist.getName());
        holder.tvSpecialty.setText(cyclist.getSpecialty());
        holder.tvBirthplace.setText(cyclist.getBirthplace());
        holder.tvTitles.setText(cyclist.getTitles() + " títulos");

        holder.itemView.setOnClickListener(v -> listener.onCyclistClick(cyclist));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onCyclistLongClick(cyclist);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return cyclists != null ? cyclists.size() : 0;
    }

    public void setCyclists(List<Cyclist> cyclists) {
        this.cyclists = cyclists;
        notifyDataSetChanged();
    }

    public static class CyclistViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSpecialty, tvBirthplace, tvTitles;

        public CyclistViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_cyclist_name);
            tvSpecialty = itemView.findViewById(R.id.tv_cyclist_specialty);
            tvBirthplace = itemView.findViewById(R.id.tv_cyclist_birthplace);
            tvTitles = itemView.findViewById(R.id.tv_cyclist_titles);
        }
    }
}
