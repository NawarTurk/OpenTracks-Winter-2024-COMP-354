package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.appbar.MaterialToolbar;
import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.AbstractActivity;
import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.data.TrackSelection;
import de.dennisguse.opentracks.data.models.Track;
import de.dennisguse.opentracks.databinding.AggregatedStatsBinding;

import de.dennisguse.opentracks.ui.aggregatedStatistics.dailyStats.DailyStatsActivity;
import de.dennisguse.opentracks.util.IntentUtils;
import de.dennisguse.opentracks.util.IntentDashboardUtils;

public class AggregatedStatisticsActivity extends AbstractActivity implements FilterDialogFragment.FilterDialogListener, AggregatedDayStatisticsAdapter.OnButtonClickListener {
    public static final String EXTRA_TRACK_IDS = "track_ids";

    static final String STATE_ARE_FILTERS_APPLIED = "areFiltersApplied";

    private AggregatedStatsBinding viewBinding;

    private AggregatedStatisticsAdapter adapter;
    private AggregatedDayStatisticsAdapter dayAdapter;

    private AggregatedStatisticsModel viewModel;
    private final TrackSelection selection = new TrackSelection();

    private boolean areFiltersApplied;
    private MenuItem filterItem;
    private MenuItem clearFilterItem;

    private boolean isDailyView = false;
    Button specificStatsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        areFiltersApplied = savedInstanceState != null && savedInstanceState.getBoolean(STATE_ARE_FILTERS_APPLIED);

        List<Track.Id> trackIds = getIntent().getParcelableArrayListExtra(EXTRA_TRACK_IDS);
        if (trackIds != null && !trackIds.isEmpty()) {
            trackIds.stream().forEach(selection::addTrackId);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new AggregatedStatisticsAdapter(this, null);
        dayAdapter = new AggregatedDayStatisticsAdapter(this, null, this::onButtonClicked);
        viewBinding.aggregatedStatsList.setLayoutManager(layoutManager);

        viewModel = new ViewModelProvider(this).get(AggregatedStatisticsModel.class);

        // Find the toolbar from the layout and set it as the action bar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Switch dailySwitch = findViewById(R.id.aggregated_stats_daily_switch);
        dailySwitch.setOnCheckedChangeListener((compoundButton, switchState) -> {
            isDailyView = switchState;
            // Change the toolbar title based on the state of the switch
            if (switchState) {
                // Daily view is active
                toolbar.setTitle(R.string.title_daily); // Set title for daily view
            } else {
                // Aggregated view is active
                toolbar.setTitle(R.string.menu_aggregated_statistics); // Set title for aggregated view
            }
            toggleAdapter();
        });
        toggleAdapter();

        setSupportActionBar(viewBinding.bottomAppBarLayout.bottomAppBar);
    }

    private void toggleAdapter() {
        if (isDailyView) {
            viewBinding.aggregatedStatsList.setAdapter(dayAdapter);
            viewModel.getAggregatedDailyStats(selection).observe(this, aggregatedStats -> {
                if (aggregatedStats != null) {
                    dayAdapter.swapData(aggregatedStats);
                }
                checkListEmpty();
            });
        } else {
            viewBinding.aggregatedStatsList.setAdapter(adapter);
            viewModel.getAggregatedStats(selection).observe(this, aggregatedStats -> {
                if (aggregatedStats != null) {
                    adapter.swapData(aggregatedStats);
                }
                checkListEmpty();
            });
        }
    }

    private void checkListEmpty() {
        if (adapter.getItemCount() == 0) {
            viewBinding.aggregatedStatsList.setVisibility(View.GONE);
            viewBinding.aggregatedStatsEmptyView.setVisibility(View.VISIBLE);
        } else {
            viewBinding.aggregatedStatsList.setVisibility(View.VISIBLE);
            viewBinding.aggregatedStatsEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_ARE_FILTERS_APPLIED, areFiltersApplied);
    }

    @Override
    protected View getRootView() {
        viewBinding = AggregatedStatsBinding.inflate(getLayoutInflater());
        return viewBinding.getRoot();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aggregated_statistics, menu);
        clearFilterItem = menu.findItem(R.id.aggregated_statistics_clear_filter);
        filterItem = menu.findItem(R.id.aggregated_statistics_filter);
        setMenuVisibility(areFiltersApplied);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.aggregated_statistics_filter) {
            ArrayList<FilterDialogFragment.FilterItem> filterItems = new ArrayList<>();
            if(isDailyView) {
                dayAdapter.getDays().stream().forEach(day -> filterItems.add(new FilterDialogFragment.FilterItem(day, day, true)));
            } else {
                adapter.getCategories().stream().forEach(activityType -> filterItems.add(new FilterDialogFragment.FilterItem(activityType, activityType, true)));
            }
            FilterDialogFragment.showDialog(getSupportFragmentManager(), filterItems);
            return true;
        }

        if (item.getItemId() == R.id.aggregated_statistics_clear_filter) {
            setMenuVisibility(false);
            viewModel.clearSelection();
            return true;
        }

        if (item.getItemId() == R.id.visualize_daily_stats) {
            startActivity(IntentUtils.newIntent(this, DailyStatsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMenuVisibility(boolean areFiltersApplied) {
        this.areFiltersApplied = areFiltersApplied;
        if (clearFilterItem != null && filterItem != null) {
            clearFilterItem.setVisible(this.areFiltersApplied);
            filterItem.setVisible(!this.areFiltersApplied);
        }
    }

    @Override
    public void onFilterDone(ArrayList<FilterDialogFragment.FilterItem> filterItems, LocalDateTime from, LocalDateTime to) {
        setMenuVisibility(true);
        selection.addDateRange(from.atZone(ZoneId.systemDefault()).toInstant(), to.atZone(ZoneId.systemDefault()).toInstant());
        filterItems.stream().filter(fi -> fi.isChecked).forEach(fi -> selection.addActivityType(fi.value));
        viewModel.updateSelection(selection);
    }

    @Override
    public void onButtonClicked(int position) {
        Intent intent = new Intent(AggregatedStatisticsActivity.this, LiftStatsActivity.class);
        startActivity(intent);
    }
}
