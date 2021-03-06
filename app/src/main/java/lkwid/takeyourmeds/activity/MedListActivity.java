package lkwid.takeyourmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import lkwid.takeyourmeds.R;
import lkwid.takeyourmeds.adapter.MedListAdapter;
import lkwid.takeyourmeds.database.MedDatabase;
import lkwid.takeyourmeds.database.SqliteMedDatabase;
import lkwid.takeyourmeds.model.Medicine;
import lkwid.takeyourmeds.NotificationPlanner;

public class MedListActivity extends AppCompatActivity implements MedListAdapter.onClickListener {
    @BindView(R.id.activity_meds_list)
    RecyclerView mMedList;

    private MedListAdapter mAdapter;
    private MedDatabase mMedDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds_list);
        ButterKnife.bind(this);

        mMedDatabase = new SqliteMedDatabase(this);

        if (getResources().getConfiguration().orientation == 1) {
            mMedList.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (getResources().getConfiguration().orientation == 2) {
            mMedList.setLayoutManager(new GridLayoutManager(this, 3));
        }
        mAdapter = new MedListAdapter(mMedDatabase.getMeds(), this);
        mMedList.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();

        new NotificationPlanner(mMedDatabase, this).remindToTakeMeds();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meds_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_med_create) {
            Intent createMedReminder = new Intent(this, MedCreateActivity.class);
            startActivity(createMedReminder);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshList() {
        mAdapter.setmMedicines(mMedDatabase.getMeds());
    }

    @Override
    public void onClick(Medicine medicine, int position) {
        Intent editMedReminder= new Intent(this, MedCreateActivity.class);
        editMedReminder.putExtra("pos", position);
        startActivity(editMedReminder);
    }
}
