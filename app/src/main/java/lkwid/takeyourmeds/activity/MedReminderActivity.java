package lkwid.takeyourmeds.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lkwid.takeyourmeds.R;
import lkwid.takeyourmeds.database.MedDatabase;
import lkwid.takeyourmeds.database.SqliteMedDatabase;
import lkwid.takeyourmeds.model.Medicine;

public class MedReminderActivity extends AppCompatActivity {
    private MedDatabase mMedDatabase;
    private int mPosition = -1;
    private Medicine mMedicine;

    @BindView(R.id.med_name)
    EditText mName;
    @BindViews({R.id.regularity_morning, R.id.regularity_noon, R.id.regularity_evening})
    List<CheckBox> mRegularity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds_reminder);
        ButterKnife.bind(this);

        mMedDatabase = new SqliteMedDatabase(this);

        if (getIntent().hasExtra("pos")) {
            mPosition = getIntent().getIntExtra("pos", -1);
            mMedicine = mMedDatabase.getMed(mPosition);

            mName.setText(mMedicine.getName());
            for (int i = 0; i < 3; i++) {
                if (mMedicine.getRegularity().charAt(i) == '1')
                    mRegularity.get(i).setChecked(true);
                else
                    mRegularity.get(i).setChecked(false);
            }
        }
    }

    @OnClick(R.id.btn_save)
    void onSaveClick() {
        Medicine medicine = mMedicine != null ? mMedicine : new Medicine();
        medicine.setName(mName.getText().toString());

        StringBuilder regularityBuilder = new StringBuilder();
        for (CheckBox time : mRegularity) {

            if (time.isChecked()) {
                regularityBuilder.append(1);
            } else {
                regularityBuilder.append(0);
            }
        }
        medicine.setRegularity(regularityBuilder.toString());

        if (mPosition == -1)
            mMedDatabase.addMed(medicine);
        else
            mMedDatabase.updateMed(medicine, mPosition);

        finish();
    }

}
