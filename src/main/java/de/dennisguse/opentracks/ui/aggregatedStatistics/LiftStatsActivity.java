package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.AbstractActivity;
import de.dennisguse.opentracks.R;
import de.dennisguse.opentracks.databinding.AggregatedStatsBinding;
import de.dennisguse.opentracks.databinding.FriendsPageBinding;
import de.dennisguse.opentracks.databinding.LiftRunStatsBinding;
import de.dennisguse.opentracks.databinding.LiftStatsBinding;

public class LiftStatsActivity extends AbstractActivity {
    private boolean isRun = false;
    private LiftStatsActivityAdapter liftAdapter;
    private RunDetailStatsAdapter runAdapter;
    private RecyclerView recyclerView;
    private MaterialToolbar title;

    private LiftStatsBinding viewBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.lift_run_stats);

        //Test purpose only, will use actual data once confirmed with Group 7
        List<LiftStats> testLiftData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            testLiftData.add(new LiftStats("Lift " + i, i * 10.0));
        }
        //Test purpose only, will use actual data once confirmed with Group 7
        List<RunDetailStats> testRunData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            testRunData.add(new RunDetailStats("500km", i * 10.0));
        }

        // Makes the back button work correctly
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar_layout).findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);

        liftAdapter = new LiftStatsActivityAdapter(this, testLiftData);
        runAdapter = new RunDetailStatsAdapter(this, testRunData);

        title = findViewById(R.id.lift_run_statistics_title);
        recyclerView = findViewById(R.id.lift_run_statistics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.setAdapter(liftAdapter);

        //Need to implement the toggle to display run specific stats...
        Switch liftRunSwitch = findViewById(R.id.lift_run_switch);
        liftRunSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isRun = isChecked;
            toggleAdapter();
        });
        toggleAdapter();
    }

    private void toggleAdapter() {
        if (isRun) {
            recyclerView.setAdapter(runAdapter);
            title.setTitle(R.string.run_statistics);
        } else {
            recyclerView.setAdapter(liftAdapter);
            title.setTitle(R.string.lift_statistics);
        }
    }

    @Override
    protected View getRootView() {
        viewBinding = LiftStatsBinding.inflate(getLayoutInflater());
        return viewBinding.getRoot();
    }
}