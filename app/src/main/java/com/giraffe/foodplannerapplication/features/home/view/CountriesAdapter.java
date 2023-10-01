package com.giraffe.foodplannerapplication.features.home.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryVH> {

    List<Country> countries;
    OnCountryClick onCountryClick;

    public CountriesAdapter(List<Country> countries, OnCountryClick onCountryClick) {
        this.countries = countries;
        this.onCountryClick = onCountryClick;
    }

    public void setList(List<Country> countries){
        this.countries.clear();
        this.countries.addAll(countries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent,false);
        return new CountryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryVH holder, int position) {
        Country country = countries.get(position);
        Log.i(HomeFragment.TAG,"country color: "+country.getStrColor());
        holder.getTvItem().setText(country.getStrArea());
        int color = Color.parseColor(country.getStrColor());
        holder.getIvItem().setColorFilter(color);
        //Glide.with(holder.getView().getContext()).load(category.getStrCategoryThumb()).into(holder.getIvItem());
        holder.getView().setOnClickListener(v-> onCountryClick.onClick(country));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class CountryVH extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView ivItem;
        private final TextView tvItem;

        public CountryVH(@NonNull View itemView) {
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

    interface OnCountryClick {
        void onClick(Country country);
    }
}
