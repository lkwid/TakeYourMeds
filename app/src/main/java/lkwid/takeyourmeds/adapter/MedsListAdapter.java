package lkwid.takeyourmeds.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lkwid.takeyourmeds.R;
import lkwid.takeyourmeds.model.Medicine;

public class MedsListAdapter extends RecyclerView.Adapter<MedsListAdapter.MedsViewHolder> {
    List<Medicine> mMedicines;

    public MedsListAdapter(List<Medicine> medicines) {
        mMedicines = medicines;
    }

    @Override
    public MedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View gridView = inflater.inflate(R.layout.meds_item, parent, false);
        return new MedsViewHolder(gridView);
    }

    @Override
    public void onBindViewHolder(MedsViewHolder holder, int position) {
        Medicine medicine = mMedicines.get(position);

        holder.mName.setText(medicine.getName());
        holder.mDosage.setText(medicine.getWhenToTake());
    }

    @Override
    public int getItemCount() {
        return mMedicines.size();
    }

    public static class MedsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.med_name)
        TextView mName;
        @BindView(R.id.med_dosage)
        TextView mDosage;

        public MedsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
