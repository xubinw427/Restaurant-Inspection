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

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private RestaurantManager restaurantManager;
    private OnRestaurantListener onRestaurantListener;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView btnBackground;
        private ImageView hazardIcon;
        private ImageView restaurantLogoIcon;
        private TextView restaurantName;
        private TextView inspectionDate;
        private TextView numIssues;

        OnRestaurantListener onRestaurantListener;

        private RestaurantViewHolder(@NonNull View itemView, OnRestaurantListener onRestaurantListener) {
            super(itemView);
            btnBackground = itemView.findViewById((R.id.restaurant_button));
            hazardIcon = itemView.findViewById(R.id.haz_icon);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            inspectionDate = itemView.findViewById(R.id.inspection_date);
            numIssues = itemView.findViewById(R.id.num_issues);
            restaurantLogoIcon = itemView.findViewById(R.id.food_icon);
            this.onRestaurantListener = onRestaurantListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRestaurantListener.onRestaurantClick(getAdapterPosition());
        }
    }

    public interface OnRestaurantListener {
        void onRestaurantClick(int position);
    }

    public RestaurantAdapter(RestaurantManager restaurants, OnRestaurantListener onRestaurantListener) {
        restaurantManager = restaurants;
        this.onRestaurantListener = onRestaurantListener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_layout, parent, false);

        return new RestaurantViewHolder(view, onRestaurantListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant currRestaurant = restaurantManager.getRestaurantAt(position);
        /** Check 10 icon of restaurant(5 of them have 4 stores or more), then set corresponding background **/
        String restaurantName = currRestaurant.getName();
        if(restaurantName.contains("7-Eleven")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.seveneleven_logo);
        }else if(restaurantName.contains("Royal Canadian Legion")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.royalcanadianlegion_logo);
        }else if(restaurantName.contains("Boston Pizza")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.bostonpizza_logo);
        }else if(restaurantName.contains("Blenz")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.blenzcoffee_logo);
        }else if(restaurantName.contains("OPA")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.opaofgreece_logo);
        }else if(restaurantName.contains("Papa John's")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.papajohnspizza_logo);
        }else if(restaurantName.contains("Panago")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.panago_logo);
        }else if(restaurantName.contains("Pizza Hut")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.pizzahut_logo);
        }else if(restaurantName.contains("Starbucks")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.starbuckscoffee_logo);
        }else if(restaurantName.contains("Burger King")){
            holder.restaurantLogoIcon.setImageResource(R.drawable.burgerking_logo);
        }


        /** Check hazard level of restaurant, then set corresponding background **/
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
        return restaurantManager.getSize();
    }
}
