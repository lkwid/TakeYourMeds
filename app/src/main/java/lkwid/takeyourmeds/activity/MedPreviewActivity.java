package lkwid.takeyourmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lkwid.takeyourmeds.NotificationPlanner;
import lkwid.takeyourmeds.R;
import lkwid.takeyourmeds.database.MedDatabase;
import lkwid.takeyourmeds.database.SqliteMedDatabase;
import lkwid.takeyourmeds.model.Medicine;

public class MedPreviewActivity extends AppCompatActivity {
    private MedDatabase medDatabase;
    private ArrayList<Integer> mReceivedList;
    private ArrayAdapter<String> mAdapter;

    @BindView(R.id.preview_list)
    ListView mMedPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_preview);
        ButterKnife.bind(this);

        medDatabase = new SqliteMedDatabase(this);

        if (getIntent().hasExtra(String.valueOf(NotificationPlanner.MORNING))) {
            mReceivedList = getIntent().getIntegerArrayListExtra(String.valueOf(NotificationPlanner.MORNING));
        } else if (getIntent().hasExtra(String.valueOf(NotificationPlanner.NOON))) {
            mReceivedList = getIntent().getIntegerArrayListExtra(String.valueOf(NotificationPlanner.NOON));
        } else if (getIntent().hasExtra(String.valueOf(NotificationPlanner.EVENING))) {
            mReceivedList = getIntent().getIntegerArrayListExtra(String.valueOf(NotificationPlanner.EVENING));
        } else {
            return;
        }

        ArrayList<String> previewList = new ArrayList<>();
        List<Medicine> medicines = medDatabase.getMeds();

        for (Integer id : mReceivedList) {
            for (int i=0; i < medicines.size(); i++) {
                if (medicines.get(i).getId() == id) {
                    previewList.add(medicines.get(i).getName() + "\n-- dawkowanie: "
                            + medicines.get(i).getDosage() + medicines.get(i).printUnit());
                    medicines.remove(i);
                }
            }
        }

        mAdapter = new ArrayAdapter<String>(this, R.layout.preview_item, previewList);
        mMedPreview.setAdapter(mAdapter);
    }
}
