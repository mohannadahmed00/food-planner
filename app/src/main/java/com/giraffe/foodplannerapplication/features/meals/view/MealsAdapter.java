package com.giraffe.foodplannerapplication.features.meals.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealVH> {

    List<Meal> meals;
    OnMealClick mealClick;

    public MealsAdapter(List<Meal> meals, OnMealClick mealClick) {
        this.meals = meals;
        this.mealClick = mealClick;
    }

    public void setList(List<Meal> meals) {
        this.meals.clear();
        this.meals.addAll(meals);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MealVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealVH holder, int position) {
        Meal meal = meals.get(position);
        holder.getTvItem().setText(meal.getStrMeal());
        Glide.with(holder.getView().getContext()).load(meal.getStrMealThumb()).into(holder.getIvItem());
        holder.getView().setOnClickListener(v -> mealClick.onClick(meal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealVH extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView ivItem;
        private final TextView tvItem;

        public MealVH(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ivItem = itemView.findViewById(R.id.iv_meal);
            tvItem = itemView.findViewById(R.id.tv_meal);
        }

        public View getView() {
            return view;
        }

        public ImageView getIvItem() {
            return ivItem;
        }

        public TextView getTvItem() {
            return tvItem;
        }
    }

    interface OnMealClick {
        void onClick(Meal meal);
    }
}
