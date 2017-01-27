package lkwid.takeyourmeds.activity;

import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import lkwid.takeyourmeds.R;

public class MedPreviewActivity extends MedCreateActivity {
    @BindView(R.id.btn_save)
    Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mName.setEnabled(false);

    }
}
