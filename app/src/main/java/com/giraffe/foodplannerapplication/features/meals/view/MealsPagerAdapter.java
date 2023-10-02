package com.giraffe.foodplannerapplication.features.meals.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;

import java.util.ArrayList;

public class MealsPagerAdapter<T> extends FragmentStateAdapter {
    private final ArrayList<T> list;

    public MealsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<T> list) {
        super(fragmentManager, lifecycle);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        T item = list.get(position);
        if (item instanceof Category) {
            return new MealsFragment<>((Category) item);

        } else {
            return new MealsFragment<>((Country) item);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
