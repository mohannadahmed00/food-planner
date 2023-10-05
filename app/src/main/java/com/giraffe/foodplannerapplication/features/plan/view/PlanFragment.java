package com.giraffe.foodplannerapplication.features.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.util.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import kotlin.Pair;

public class PlanFragment extends Fragment {
    private final static String TAG = "PlanFragment";


    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private PlanPagerAdapter adapter;

    private ArrayList<Pair<String, Integer>> days;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        days = new ArrayList<>();
        days.add(new Pair<>("Saturday", Constants.DAYS.SATURDAY));
        days.add(new Pair<>("Sunday", Constants.DAYS.SUNDAY));
        days.add(new Pair<>("Monday", Constants.DAYS.MONDAY));
        days.add(new Pair<>("Tuesday", Constants.DAYS.TUESDAY));
        days.add(new Pair<>("Wednesday", Constants.DAYS.WEDNESDAY));
        days.add(new Pair<>("Thursday", Constants.DAYS.THURSDAY));
        days.add(new Pair<>("Friday", Constants.DAYS.FRIDAY));
        adapter = new PlanPagerAdapter(getChildFragmentManager(), getLifecycle(), days);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tl_days);
        viewPager = view.findViewById(R.id.vp_days);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(days.get(position).getFirst())
        ).attach();

    }
}