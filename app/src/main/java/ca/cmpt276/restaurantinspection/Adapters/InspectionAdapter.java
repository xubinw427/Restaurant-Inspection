package ca.cmpt276.restaurantinspection.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.cmpt276.restaurantinspection.Model.TestInspection;
import ca.cmpt276.restaurantinspection.R;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.InspectionViewHolder> {
    private ArrayList<TestInspection> inspectionList;
    private OnInspectionListener myOnInspectionListener;

    public static class InspectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView inspBackground;
        private TextView inspDate;
        private TextView numCritIssues;
        private TextView numNonCritIssues;

        OnInspectionListener myOnInspectionListener;

        private InspectionViewHolder(@NonNull View itemView, OnInspectionListener onInspectionListener) {
            super(itemView);
            inspBackground = itemView.findViewById(R.id.inspection_button_background);
            inspDate = itemView.findViewById(R.id.inspection_date);
            numCritIssues = itemView.findViewById(R.id.num_crit_issues);
            numNonCritIssues = itemView.findViewById(R.id.num_non_crit_issues);

            myOnInspectionListener = onInspectionListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myOnInspectionListener.onInspectionClick(getAdapterPosition());
        }
    }

    public interface OnInspectionListener {
        void onInspectionClick(int position);
    }

    public InspectionAdapter(ArrayList<TestInspection> inspections, OnInspectionListener onInspectionListener) {
        inspectionList = inspections;
        myOnInspectionListener = onInspectionListener;
    }

    @NonNull
    @Override
    public InspectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inspection_layout, parent, false);
        InspectionViewHolder ivh = new InspectionViewHolder(view, myOnInspectionListener);

        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull InspectionViewHolder holder, int position) {
        TestInspection currInspection = inspectionList.get(position);

        switch(currInspection.getHazard()) {
            case "high":
                holder.inspBackground.setImageResource(R.drawable.inspection_high);
                break;
            case "mod":
                holder.inspBackground.setImageResource(R.drawable.inspection_mod);
                break;
            case "low":
                holder.inspBackground.setImageResource(R.drawable.inspection_low);
                break;
            default:
                break;
        }

        holder.inspDate.setText(currInspection.getDate());
        holder.numCritIssues.setText(currInspection.getNumCrit());
        holder.numNonCritIssues.setText(currInspection.getNumNonCrit());

    }

    @Override
    public int getItemCount() {
        return inspectionList.size();
    }
}