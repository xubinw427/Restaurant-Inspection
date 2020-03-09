package ca.cmpt276.restaurantinspection.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.cmpt276.restaurantinspection.Model.TestRestaurant;
import ca.cmpt276.restaurantinspection.R;

/** CHANGE ALL INSTANCES OF TestRestaurant to Restaurant after testing!!!!! **/

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private ArrayList<TestRestaurant> restaurantList;
    private OnRestaurantListener myOnRestaurantListener;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView btnBackground;
        private ImageView icon;
        private TextView restaurantName;
        private TextView inspectionDate;
        private TextView numIssues;
        private TextView issues;

        OnRestaurantListener myOnRestaurantListener;

        private RestaurantViewHolder(@NonNull View itemView, OnRestaurantListener onRestaurantListener) {
            super(itemView);
            btnBackground = itemView.findViewById((R.id.restaurantButton));
//            icon = itemView.findViewById(R.id.foodIcon);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            inspectionDate = itemView.findViewById(R.id.inspectionDate);
            numIssues = itemView.findViewById(R.id.numIssues);
//            issues = itemView.findViewById(R.id.issues);

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

    public RestaurantAdapter(ArrayList<TestRestaurant> restaurants, OnRestaurantListener onRestaurantListener) {
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
        TestRestaurant currRestaurant = restaurantList.get(position);

        /** Check hazard level of rest, then switch statements to set background **/
        switch(currRestaurant.getHazard()) {
            case "high":
                holder.btnBackground.setImageResource(R.drawable.button_red);
                break;
            case "mod":
                holder.btnBackground.setImageResource(R.drawable.button_yellow);
                break;
            case "low":
                holder.btnBackground.setImageResource(R.drawable.button_teal);
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
        return restaurantList.size();
    }
}