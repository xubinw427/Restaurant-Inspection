package ca.cmpt276.restaurantinspection.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.cmpt276.restaurantinspection.Model.TestViolation;
import ca.cmpt276.restaurantinspection.R;

public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder> {
    private ArrayList<TestViolation> ViolationList;
    private OnViolationListener myOnViolationListener;

    public static class ViolationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView violationIcon;
        private TextView violationDesc;

        OnViolationListener myOnViolationListener;

        private ViolationViewHolder(@NonNull View itemView, OnViolationListener onViolationListener) {
            super(itemView);
            violationIcon = itemView.findViewById(R.id.violIcon);
            violationDesc = itemView.findViewById(R.id.violDesc);

            myOnViolationListener = onViolationListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myOnViolationListener.onViolationClick(getAdapterPosition());

        }
    }

    public interface OnViolationListener {
        void onViolationClick(int position);
    }

    public ViolationAdapter(ArrayList<TestViolation> Violations, OnViolationListener onViolationListener) {
        ViolationList = Violations;
        myOnViolationListener = onViolationListener;
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.violation_layout, parent, false);
        ViolationViewHolder rvh = new ViolationViewHolder(view, myOnViolationListener);

        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {
        /** Set information below for current violation **/
        TestViolation currViolation = ViolationList.get(position);

        /** Check violation type & crit/non-crit of rest, then switch statements to set icon **/
        switch(currViolation.getType()) {
            case "Employee":
                switch(currViolation.getLevel()) {
                    case "Not Critical":
                        holder.violationIcon.setImageResource(R.drawable.employee_noncrit);
                        break;
                    case "Critical":
                        holder.violationIcon.setImageResource(R.drawable.employee_crit);
                        break;
                    default:
                        break;
                }
                break;
            case "Equipment":
                switch(currViolation.getLevel()) {
                    case "Not Critical":
                        holder.violationIcon.setImageResource(R.drawable.equipment_noncrit);
                        break;
                    case "Critical":
                        holder.violationIcon.setImageResource(R.drawable.equipment_crit);
                        break;
                    default:
                        break;
                }
                break;
            case "Food":
                switch(currViolation.getLevel()) {
                    case "Not Critical":
                        holder.violationIcon.setImageResource(R.drawable.food_noncrit);
                        break;
                    case "Critical":
                        holder.violationIcon.setImageResource(R.drawable.food_crit);
                        break;
                    default:
                        break;
                }
                break;
            case "Operations":
                holder.violationIcon.setImageResource(R.drawable.operations_noncrit);
                break;
            case "Pests":
                holder.violationIcon.setImageResource(R.drawable.pest_noncrit);
                break;
            default:
                break;
        }

        holder.violationDesc.setText(currViolation.getsDesc());
    }

    @Override
    public int getItemCount() {
        return ViolationList.size();
    }
}
