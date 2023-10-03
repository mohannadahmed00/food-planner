package com.giraffe.foodplannerapplication.features.meals.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Meal;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


import java.util.ArrayList;

public class TabsFragment extends Fragment implements TabsView {
    private final static String TAG = "TabsFragment";

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private MealsPagerAdapter adapter;

    private TabsFragmentArgs args;

    private ArrayList<Category> categories;
    private ArrayList<Country> countries;
    private int selected;
    private String type;

    private ImageView ivBack;
    private TextView tvBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        selected = -1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        args = TabsFragmentArgs.fromBundle(getArguments());
        type = args.getType();
        if (selected == -1) {
            selected = TabsFragmentArgs.fromBundle(getArguments()).getSelected();
        }
        if (type.equals("category")) {
            categories = args.getList();
            adapter = new MealsPagerAdapter<>(getChildFragmentManager(), getLifecycle(), categories, this);

        } else {
            countries = args.getList();
            adapter = new MealsPagerAdapter<>(getChildFragmentManager(), getLifecycle(), countries, this);
        }
        ivBack = view.findViewById(R.id.iv_back);
        tvBar = view.findViewById(R.id.tv_bar);
        tabLayout = view.findViewById(R.id.tl_meals);
        viewPager = view.findViewById(R.id.vp_meals);
        viewPager.setAdapter(adapter);
        viewPager.postDelayed(() -> viewPager.setCurrentItem(selected), 0);
        if (type.equals("category")) {
            tvBar.setText(R.string.meals_by_category);
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(categories.get(position).getStrCategory())
            ).attach();
        } else {
            tvBar.setText(R.string.meals_by_country);
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(countries.get(position).getStrArea())
            ).attach();
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selected = tab.getPosition();
                /*if (type.equals("category")) {
                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).getStrCategory().equals(tab.getText())) {
                            selected = i;
                            break;
                        }
                    }
                }
                else {
                    for (int i = 0; i < countries.size(); i++) {
                        if (countries.get(i).getStrArea().equals(tab.getText())) {
                            selected = i;
                            break;
                        }
                    }
                }*/
                Log.i(TAG, "onTabSelected index: " + selected);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ivBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }

    @Override
    public void onMealClick(Meal meal) {
        TabsFragmentDirections.ActionMealsFragmentToDetailsFragment action = TabsFragmentDirections.actionMealsFragmentToDetailsFragment(meal);
        Navigation.findNavController(requireView()).navigate(action);
    }
}