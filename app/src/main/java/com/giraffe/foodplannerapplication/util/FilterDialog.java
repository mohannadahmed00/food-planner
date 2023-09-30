package com.giraffe.foodplannerapplication.util;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.features.home.view.HomeFragment;
import com.giraffe.foodplannerapplication.features.home.view.OnFilterClick;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class FilterDialog extends DialogFragment {

    private static FilterDialog instance;

    private final FragmentManager fragmentManager;

    private ChipGroup cgCategory, cgCountry, cgIngredient;

    Button btnContinue;

    String category,country,ingredient;

    List<String> categories,countries,ingredients;

    OnFilterClick onFilterClick;

    public static FilterDialog getInstance(FragmentManager fragmentManager,OnFilterClick onFilterClick) {
        if (instance == null) {
            instance = new FilterDialog(fragmentManager,onFilterClick);
        }
        return instance;
    }

    public void showFilter() {
        instance.show(fragmentManager, "filter");
    }

    public void dismissFilter() {
        instance.dismiss();
    }

    private FilterDialog(FragmentManager fragmentManager, OnFilterClick onFilterClick) {
        this.fragmentManager = fragmentManager;
        this.onFilterClick = onFilterClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        category = null;
        country = null;
        ingredient = null;


        categories=new ArrayList<>();
        countries=new ArrayList<>();
        ingredients=new ArrayList<>();

        categories.add("seafood");
        categories.add("breakfast");
        categories.add("meat");
        categories.add("chicken");
        categories.add("fish");
        categories.add("cake");
        categories.add("desert");

        countries.add("United States");
        countries.add("Canada");
        countries.add("Mexico");
        countries.add("United Kingdom");
        countries.add("France");
        countries.add("Germany");
        countries.add("Italy");
        countries.add("Spain");
        countries.add("Russia");
        countries.add("China");
        countries.add("Japan");
        countries.add("India");
        countries.add("Brazil");
        countries.add("Australia");
        countries.add("South Africa");

        ingredients.add("Salt");
        ingredients.add("Pepper");
        ingredients.add("Sugar");
        ingredients.add("Flour");
        ingredients.add("Eggs");
        ingredients.add("Milk");
        ingredients.add("Butter");
        ingredients.add("Oil");





        cgCategory = view.findViewById(R.id.cg_category);
        cgCountry = view.findViewById(R.id.cg_country);
        cgIngredient = view.findViewById(R.id.cg_ingredient);
        btnContinue = view.findViewById(R.id.btn_continue);


        for (String title : categories) {
            cgCategory.addView(createChip(title));
        }

        for (String title : countries) {
            cgCountry.addView(createChip(title));
        }

        for (String title : ingredients) {
            cgIngredient.addView(createChip(title));
        }

        cgCategory.setOnCheckedChangeListener((group, checkedId) ->{
            Chip chip = group.findViewById(checkedId);
            if (chip!=null){
                category = chip.getText().toString().trim();
            }
        });
        cgCountry.setOnCheckedChangeListener((group, checkedId) ->{
            Chip chip = group.findViewById(checkedId);
            if (chip!=null){
                country = chip.getText().toString().trim();
            }
        });
        cgIngredient.setOnCheckedChangeListener((group, checkedId) ->{
            Chip chip = group.findViewById(checkedId);
            if (chip!=null){
                ingredient = chip.getText().toString().trim();
            }
        });

        btnContinue.setOnClickListener(v->{
            onFilterClick.onFilterClick(category,country,ingredient);
        });




    }

    private Chip createChip(String title){
        Chip chip = (Chip) LayoutInflater.from(requireContext()).inflate(R.layout.chip_layout,null);
        chip.setId(ViewCompat.generateViewId());
        chip.setText(title);
        return chip;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }

}
