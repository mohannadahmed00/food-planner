package com.giraffe.foodplannerapplication.features.favorites.view;

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

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteVH> {

    private List<Meal> favorites;
    private OnFavClick onFavClick;

    public FavoritesAdapter(List<Meal> favorites,OnFavClick onFavClick) {
        this.favorites = favorites;
        this.onFavClick = onFavClick;
    }

    public void setList(List<Meal> favorites) {
        this.favorites.clear();
        this.favorites.addAll(favorites);
        notifyDataSetChanged();
    }

    public List<Meal> getFavorites() {
        return favorites;
    }

    @NonNull
    @Override
    public FavoriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false);
        return new FavoriteVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteVH holder, int position) {
        Meal meal = favorites.get(position);
        Glide.with(holder.getView().getContext()).load(meal.getStrMealThumb()).into(holder.getIvMeal());
        holder.getTvMeal().setText(meal.getStrMeal());
        holder.getTvCountry().setText(meal.getStrArea());
        holder.getTvCategory().setText(meal.getStrCategory());
        holder.getView().setOnClickListener(v->{
            onFavClick.onClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    static class FavoriteVH extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView ivMeal;
        private final TextView tvMeal, tvCountry, tvCategory;

        public FavoriteVH(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ivMeal = itemView.findViewById(R.id.iv_meal);
            tvMeal = itemView.findViewById(R.id.tv_meal);
            tvCountry = itemView.findViewById(R.id.tv_country);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }

        public View getView() {
            return view;
        }

        public ImageView getIvMeal() {
            return ivMeal;
        }

        public TextView getTvMeal() {
            return tvMeal;
        }

        public TextView getTvCountry() {
            return tvCountry;
        }

        public TextView getTvCategory() {
            return tvCategory;
        }
    }

    interface OnFavClick{
        void onClick(Meal meal);
    }
}
