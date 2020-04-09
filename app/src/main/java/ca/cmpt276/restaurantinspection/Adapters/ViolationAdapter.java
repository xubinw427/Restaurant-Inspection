package ca.cmpt276.restaurantinspection.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import ca.cmpt276.restaurantinspection.Model.Violation;
import ca.cmpt276.restaurantinspection.R;

/** RecyclerView Adapter for List of Violations for an Inspection Record **/
public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder> {
    private ArrayList<Violation> violationList;
    private OnViolationListener violationListener;

    public static class ViolationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView violationIcon;
        private ImageView criticalIcon;
        private TextView violationDesc;

        OnViolationListener onViolationListener;

        private ViolationViewHolder(@NonNull View itemView, OnViolationListener onViolationListener) {
            super(itemView);
            violationIcon = itemView.findViewById(R.id.violation_icon);
            criticalIcon = itemView.findViewById(R.id.hazard_level_icon);
            violationDesc = itemView.findViewById(R.id.restaurant_name);

            this.onViolationListener = onViolationListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onViolationListener.onViolationClick(getAdapterPosition());
        }
    }

    public interface OnViolationListener {
        void onViolationClick(int position);
    }

    public ViolationAdapter(ArrayList<Violation> Violations, OnViolationListener onViolationListener) {
        violationList = Violations;
        violationListener = onViolationListener;
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.violation_layout, parent, false);
        return new ViolationViewHolder(view, violationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {
        Violation currViolation = violationList.get(position);

        /** Check violation type & crit/non-crit of rest, then switch statements to set icon **/
        switch(currViolation.getType()) {
            case "Employees":
                switch(currViolation.getSeverity()) {
                    case "Not Critical":
                        holder.violationIcon.setImageResource(R.drawable.employee_noncrit);
                        holder.criticalIcon.setImageResource(R.drawable.violation_noncrit);
                        break;
                    case "Critical":
                        holder.violationIcon.setImageResource(R.drawable.employee_crit);
                        holder.criticalIcon.setImageResource(R.drawable.violation_crit);
                        break;
                    default:
                        break;
                }
                break;
            case "Equipment":
                switch(currViolation.getSeverity()) {
                    case "Not Critical":
                        holder.violationIcon.setImageResource(R.drawable.equipment_noncrit);
                        holder.criticalIcon.setImageResource(R.drawable.violation_noncrit);
                        break;
                    case "Critical":
                        holder.violationIcon.setImageResource(R.drawable.equipment_crit);
                        holder.criticalIcon.setImageResource(R.drawable.violation_crit);
                        break;
                    default:
                        break;
                }
                break;
            case "Food":
                switch(currViolation.getSeverity()) {
                    case "Not Critical":
                        holder.violationIcon.setImageResource(R.drawable.food_noncrit);
                        holder.criticalIcon.setImageResource(R.drawable.violation_noncrit);
                        break;
                    case "Critical":
                        holder.violationIcon.setImageResource(R.drawable.food_crit);
                        holder.criticalIcon.setImageResource(R.drawable.violation_crit);
                        break;
                    default:
                        break;
                }
                break;
            case "Operations":
                holder.violationIcon.setImageResource(R.drawable.operations_noncrit);
                holder.criticalIcon.setImageResource(R.drawable.violation_noncrit);
                break;
            case "Pests":
                holder.violationIcon.setImageResource(R.drawable.pest_noncrit);
                holder.criticalIcon.setImageResource(R.drawable.violation_noncrit);
                break;
            default:
                break;
        }

        holder.violationDesc.setText(currViolation.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return violationList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}