package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.dennisguse.opentracks.R;

public class LiftStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.lift_run_stats);

        //Test purpose only, will use actual data once confirmed with Group 7
        List<LiftStats> testData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            testData.add(new LiftStats("Lift " + i, i * 10.0));
        }

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);

        LiftStatsActivityAdapter adapter = new LiftStatsActivityAdapter(LiftStatsActivity.this, testData);
        recyclerView.setAdapter(adapter);

        //Need to implement the toggle to display run specific stats...
        Switch liftRunSwitch = findViewById(R.id.lift_run_switch);
        liftRunSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Change the content view to run_details_stats layout
                setContentView(R.layout.run_detail_stats);
                // Note: This will remove all the existing views including the switch itself.
                // You'll need to re-initialize all the views you want to interact with in the new layout.
            } else {
                // If you want to go back to the original layout when the switch is off,
                // you would set the content view back to the original layout.
                setContentView(R.layout.lift_run_stats);
                // Remember to re-initialize all the views after setting the content view.
            }
        });
    }
}