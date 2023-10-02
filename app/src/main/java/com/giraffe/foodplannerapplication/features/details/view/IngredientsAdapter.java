package com.giraffe.foodplannerapplication.features.details.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.giraffe.foodplannerapplication.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientVH> {

    List<String> ingredients;

    public IngredientsAdapter(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setList(List<String> ingredients) {
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new IngredientVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientVH holder, int position) {
        String ingredient = ingredients.get(position);
        holder.getTvItem().setText(ingredient);
        Glide.with(holder.getView().getContext()).load("https://www.themealdb.com/images/ingredients/" + ingredient + ".png").into(holder.getIvItem());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientVH extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView ivItem;
        private final TextView tvItem;

        public IngredientVH(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ivItem = itemView.findViewById(R.id.iv_item);
            tvItem = itemView.findViewById(R.id.tv_item);
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
}
