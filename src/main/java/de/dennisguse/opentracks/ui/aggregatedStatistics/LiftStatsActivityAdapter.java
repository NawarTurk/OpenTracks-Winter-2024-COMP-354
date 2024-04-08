package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.databinding.LiftStatsBinding;

public class LiftStatsActivityAdapter extends RecyclerView.Adapter<LiftStatsActivityAdapter.ViewHolder> {
    List<LiftStats> liftStatsList;
    LiftStatsBinding testBinding;
    final Context context;

    public LiftStatsActivityAdapter(Context context, List<LiftStats> statsList) {
        this.context = context;
        liftStatsList = new ArrayList<>();
        for(LiftStats stats: statsList){
            liftStatsList.add(new LiftStats(stats));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LiftStatsBinding binding = LiftStatsBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.liftName.setText(liftStatsList.get(position).getLiftName());

//        holder.binding.liftSpecificAvgSpeed.setText(String.valueOf(liftStatsList.get(position).getAvgSpeed()));
        holder.binding.liftSpecificAvgSpeedUnit.setText(context.getString(R.string.lift_specific_avg_speed_unit));
        holder.binding.liftSpecificAvgSpeedLabel.setText(context.getString(R.string.lift_specific_avg_speed_label));

        holder.binding.liftSpecificElevationGain.setText(String.valueOf(liftStatsList.get(position).getAltitudeGain()));
        holder.binding.liftSpecificElevationGainUnit.setText(context.getString(R.string.lift_specific_elevation_gain_unit));
        holder.binding.liftSpecificElevationGainLabel.setText(context.getString(R.string.lift_specific_elevation_gain_label));

//        holder.binding.liftSpecificTotalTime.setText(String.valueOf(liftStatsList.get(position).getTotalTime()));
        holder.binding.liftSpecificTotalTimeUnit.setText(context.getString(R.string.lift_specific_total_time_unit));
        holder.binding.liftSpecificTotalTimeLabel.setText(context.getString(R.string.lift_specific_total_time_label));

//        holder.binding.liftSpecificMovingTime.setText(String.valueOf(liftStatsList.get(position).getMovingTime()));
        holder.binding.liftSpecificMovingTimeUnit.setText(context.getString(R.string.lift_specific_moving_time_unit));
        holder.binding.liftSpecificMovingTimeLabel.setText(context.getString(R.string.lift_specific_moving_time_label));
    }

    @Override
    public int getItemCount() {
        return liftStatsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final LiftStatsBinding binding;
        Button specificStatButton;
        public ViewHolder(LiftStatsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
