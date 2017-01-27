package lkwid.takeyourmeds.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lkwid.takeyourmeds.R;
import lkwid.takeyourmeds.model.Medicine;

public class MedListAdapter extends RecyclerView.Adapter<MedListAdapter.MedsViewHolder> {
    private List<Medicine> mMedicines;
    private onClickListener onClickListener;

    public MedListAdapter(List<Medicine> medicines, onClickListener onClickListener) {
        mMedicines = medicines;
        this.onClickListener = onClickListener;
        notifyDataSetChanged();
    }

    public void setmMedicines(List<Medicine> mMedicines) {
        this.mMedicines = mMedicines;
        notifyDataSetChanged();
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
        holder.mRegularity.setText(medicine.printRegularity()
                +"\n Dawkowanie: "+medicine.getDosage()+" "+medicine.printUnit());
        holder.mCurrentMed = medicine;
        holder.mCurrentPosition = medicine.getId();
        holder.mBlockListeners = false;
    }

    @Override
    public int getItemCount() {
        return mMedicines.size();
    }

    public class MedsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.med_name)
        TextView mName;
        @BindView(R.id.med_footer)
        TextView mRegularity;
        Medicine mCurrentMed;
        int mCurrentPosition;
        boolean mBlockListeners = true;


        public MedsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onItemClick() {
            if (onClickListener != null && !mBlockListeners) {
                onClickListener.onClick(mCurrentMed, mCurrentPosition);
            }
        }
    }

    public interface onClickListener {
         void onClick(Medicine medicine, int position);
    }
}
