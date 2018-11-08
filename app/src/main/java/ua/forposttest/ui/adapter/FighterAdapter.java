package ua.forposttest.ui.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import ua.forposttest.R;
import ua.forposttest.data.model.Fighter;

public class FighterAdapter extends RecyclerView.Adapter<FighterAdapter.FighterViewHolder> {
    private List<Fighter> fighters;

    public FighterAdapter(List<Fighter> fighters) {
        this.fighters = fighters;
    }

    public void update(List<Fighter> fighters) {
        this.fighters = fighters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FighterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new FighterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fighter_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FighterViewHolder holder, int position) {
        Fighter fighter = fighters.get(position);
        holder.team.setText(fighter.getTeam());
        holder.health.setText(String.valueOf(fighter.getHealth()));
        holder.listItem.setBackgroundColor(Color.parseColor(fighter.getTeam()));
    }

    @Override
    public int getItemCount() {
        return fighters.size();
    }

    class FighterViewHolder extends RecyclerView.ViewHolder {
        FrameLayout listItem;
        TextView team;
        TextView health;

        FighterViewHolder(@NonNull View itemView) {
            super(itemView);
            listItem = itemView.findViewById(R.id.fl_fighter_list_item);
            team = itemView.findViewById(R.id.tv_team);
            health = itemView.findViewById(R.id.tv_health);
        }
    }
}
