package com.giraffe.foodplannerapplication.features.plan.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.giraffe.foodplannerapplication.features.meals.view.TabsView;

import java.util.ArrayList;

import kotlin.Pair;

public class PlanPagerAdapter extends FragmentStateAdapter {
    private final ArrayList<Pair<String, Integer>> days;
    private TabsView tabsView;

    public PlanPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<Pair<String, Integer>> days) {
        super(fragmentManager, lifecycle);
        this.days = days;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Pair<String, Integer> day = days.get(position);
        return new DayFragment(day);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }
}
