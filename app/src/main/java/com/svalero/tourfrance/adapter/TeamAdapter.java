package com.svalero.tourfrance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.tourfrance.R;
import com.svalero.tourfrance.model.Team;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private List<Team> teams;
    private Context context;
    private OnTeamClickListener listener;

    public interface OnTeamClickListener {
        void onTeamClick(Team team);
        void onTeamLongClick(Team team);
    }

    public TeamAdapter(Context context, List<Team> teams, OnTeamClickListener listener) {
        this.context = context;
        this.teams = teams;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_team, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teams.get(position);
        holder.tvName.setText(team.getName());
        holder.tvCountry.setText(team.getCountry());
        holder.tvEmail.setText(team.getEmail());

        holder.itemView.setOnClickListener(v -> listener.onTeamClick(team));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onTeamLongClick(team);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return teams != null ? teams.size() : 0;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCountry, tvEmail;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_team_name);
            tvCountry = itemView.findViewById(R.id.tv_team_country);
            tvEmail = itemView.findViewById(R.id.tv_team_email);
        }
    }
}