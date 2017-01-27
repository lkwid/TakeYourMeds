package lkwid.takeyourmeds.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class MedCreateActivity extends AppCompatActivity {
    private MedDatabase mMedDatabase;
    private int mPosition = -1;
    private Medicine mMedicine;

    @BindView(R.id.med_name)
    EditText mName;
    @BindView(R.id.med_dosage)
    EditText mDosage;
    @BindView(R.id.med_unit)
    RadioGroup mUnit;
    @BindViews({R.id.unit_pcs, R.id.unit_mls})
    List<RadioButton> mUnitButtons;
    @BindViews({R.id.regularity_morning, R.id.regularity_noon, R.id.regularity_evening})
    List<CheckBox> mRegularity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds_create);
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
            mDosage.setText(String.valueOf(mMedicine.getDosage()));
            if (mMedicine.getUnit() == 0) {
                mUnitButtons.get(0).setChecked(true);
            } else if (mMedicine.getUnit() == 1) {
                mUnitButtons.get(1).setChecked(true);
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
        medicine.setDosage(Integer.parseInt(mDosage.getText().toString()));
        medicine.setUnit(mUnit.getCheckedRadioButtonId());

        if (mPosition == -1)
            mMedDatabase.addMed(medicine);
        else
            mMedDatabase.updateMed(medicine, mPosition);

        finish();
    }

}
