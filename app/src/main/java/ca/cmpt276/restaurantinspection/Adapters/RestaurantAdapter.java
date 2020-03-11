package ca.cmpt276.restaurantinspection.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.cmpt276.restaurantinspection.Model.Restaurant;
import ca.cmpt276.restaurantinspection.Model.RestaurantManager;
import ca.cmpt276.restaurantinspection.R;

/** CHANGE ALL INSTANCES OF TestRestaurant to Restaurant after testing!!!!! **/

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private RestaurantManager restaurantList;
    private OnRestaurantListener myOnRestaurantListener;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView btnBackground;
        private ImageView hazardIcon;
        private TextView restaurantName;
        private TextView inspectionDate;
        private TextView numIssues;

        OnRestaurantListener myOnRestaurantListener;

        private RestaurantViewHolder(@NonNull View itemView, OnRestaurantListener onRestaurantListener) {
            super(itemView);
            btnBackground = itemView.findViewById((R.id.restaurant_button));
            hazardIcon = itemView.findViewById(R.id.haz_icon);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            inspectionDate = itemView.findViewById(R.id.inspection_date);
            numIssues = itemView.findViewById(R.id.num_issues);

            myOnRestaurantListener = onRestaurantListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myOnRestaurantListener.onRestaurantClick(getAdapterPosition());

        }
    }

    public interface OnRestaurantListener {
        void onRestaurantClick(int position);
    }

    public RestaurantAdapter(RestaurantManager restaurants, OnRestaurantListener onRestaurantListener) {
        restaurantList = restaurants;
        myOnRestaurantListener = onRestaurantListener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_layout, parent, false);
        RestaurantViewHolder rvh = new RestaurantViewHolder(view, myOnRestaurantListener);

        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant currRestaurant = restaurantList.getTheOneAt(position);

        /** Check hazard level of rest, then switch statements to set background **/
        switch(currRestaurant.getHazard()) {
            case "High":
                holder.btnBackground.setImageResource(R.drawable.button_red);
                holder.hazardIcon.setImageResource(R.drawable.restlist_high);
                break;
            case "Moderate":
                holder.btnBackground.setImageResource(R.drawable.button_yellow);
                holder.hazardIcon.setImageResource(R.drawable.restlist_mod);
                break;
            case "Low":
                holder.btnBackground.setImageResource(R.drawable.button_teal);
                holder.hazardIcon.setImageResource(R.drawable.restlist_low);
                break;
            default:
                break;
        }

        holder.inspectionDate.setText(currRestaurant.getDate());
        holder.restaurantName.setText(currRestaurant.getName());
        holder.numIssues.setText(currRestaurant.getNumIssues());

        if (position == 0) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            lp.setMargins(0, 100, 0, 0);
            holder.itemView.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return restaurantList.getSize();
    }
}
