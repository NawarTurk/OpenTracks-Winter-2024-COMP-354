package de.dennisguse.opentracks.ui.aggregatedStatistics;

import android.os.Bundle;
import android.view.View;

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

        //Test data
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
    }
}