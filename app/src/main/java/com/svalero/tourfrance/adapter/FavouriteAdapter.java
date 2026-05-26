package com.svalero.tourfrance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tourfrance.R;
import com.svalero.tourfrance.database.FavouriteCyclist;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private List<FavouriteCyclist> favourites;
    private Context context;
    private OnFavouriteClickListener listener;

    public interface OnFavouriteClickListener {
        void onFavouriteClick(FavouriteCyclist favourite);
        void onFavouriteLongClick(FavouriteCyclist favourite);
    }

    public FavouriteAdapter(Context context, List<FavouriteCyclist> favourites, OnFavouriteClickListener listener) {
        this.context = context;
        this.favourites = favourites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        FavouriteCyclist fav = favourites.get(position);
        holder.tvName.setText(fav.getName());
        holder.tvSpecialty.setText(fav.getSpecialty());
        holder.tvNotes.setText(fav.getNotes());

        holder.itemView.setOnClickListener(v -> listener.onFavouriteClick(fav));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onFavouriteLongClick(fav);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return favourites != null ? favourites.size() : 0;
    }

    public void setFavourites(List<FavouriteCyclist> favourites) {
        this.favourites = favourites;
        notifyDataSetChanged();
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSpecialty, tvNotes;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_fav_name);
            tvSpecialty = itemView.findViewById(R.id.tv_fav_specialty);
            tvNotes = itemView.findViewById(R.id.tv_fav_notes);
        }
    }
}