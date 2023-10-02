package com.giraffe.foodplannerapplication.features.meals.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import java.util.ArrayList;

public class TabsFragment extends Fragment {
    private final static String TAG = "TabsFragment";

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MealsPagerAdapter adapter;

    private TabsFragmentArgs args;

    private ArrayList<Category> categories;
    private ArrayList<Country> countries;
    private int selected;
    private String type;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = TabsFragmentArgs.fromBundle(getArguments());
        type = args.getType();
        selected = TabsFragmentArgs.fromBundle(getArguments()).getSelected();
        if (type.equals("category")) {
            categories = args.getList();
            adapter = new MealsPagerAdapter<>(getParentFragmentManager(), getLifecycle(), categories);

        } else {
            countries = args.getList();
            adapter = new MealsPagerAdapter<>(getParentFragmentManager(), getLifecycle(), countries);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tl_meals);
        viewPager = view.findViewById(R.id.vp_meals);
        viewPager.setAdapter(adapter);
        viewPager.postDelayed(() -> viewPager.setCurrentItem(selected), 0);
        if (type.equals("category")) {
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(categories.get(position).getStrCategory())
            ).attach();
        } else {
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(countries.get(position).getStrArea())
            ).attach();
        }
    }
}