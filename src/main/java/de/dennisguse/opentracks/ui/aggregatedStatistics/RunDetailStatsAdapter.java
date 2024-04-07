package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.dennisguse.opentracks.databinding.RunDetailStatsBinding;
import java.util.List;

public class RunDetailStatsAdapter extends RecyclerView.Adapter<RunDetailStatsAdapter.ViewHolder> {
    private List<RunDetailStats> runDetailsListStats;
    private final LayoutInflater layoutInflater;

    public RunDetailStatsAdapter(Context context, List<RunDetailStats> runDetailsListStats) {
        this.runDetailsListStats = runDetailsListStats;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RunDetailStatsBinding binding = RunDetailStatsBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RunDetailStats runDetailStats = runDetailsListStats.get(position);
        holder.bind(runDetailStats);
    }

    @Override
    public int getItemCount() {
        return runDetailsListStats.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final RunDetailStatsBinding binding;

        ViewHolder(RunDetailStatsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(RunDetailStats runDetailStats) {
            binding.runName.setText(runDetailStats.getRunName());
            binding.maximumSpeedValue.setText(runDetailStats.getMaximumSpeed());
            binding.averageSpeedValue.setText(runDetailStats.getAverageSpeed());
            binding.runDurationValue.setText(runDetailStats.getRunDuration());
            binding.totalDistanceValue.setText(String.valueOf(runDetailStats.getTotalDistance()));
            binding.elevationValue.setText(String.valueOf(runDetailStats.getElevation()));
            binding.slopeValue.setText(String.valueOf(runDetailStats.getSlope()));
            // ... Do we need other info? Team discuss
        }
    }
}