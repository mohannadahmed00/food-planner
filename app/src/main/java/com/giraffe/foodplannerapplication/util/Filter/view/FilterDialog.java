package com.giraffe.foodplannerapplication.util.Filter.view;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.home.view.OnFilterClick;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.giraffe.foodplannerapplication.util.Filter.presenter.FilterPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterDialog extends DialogFragment implements FilterView {

    private static FilterDialog instance;

    private final FragmentManager fragmentManager;

    private ChipGroup cgCategory, cgCountry, cgIngredient;

    Button btnContinue;

    String category, country, ingredient;

    List<Chip> categories, countries, ingredients;

    OnFilterClick onFilterClick;

    FilterPresenter presenter;

    Handler handler;

    public static FilterDialog getInstance(FragmentManager fragmentManager, OnFilterClick onFilterClick, String category, String country, String ingredient) {
        if (instance == null) {
            instance = new FilterDialog(fragmentManager, onFilterClick, category, country, ingredient);
        }
        return instance;
    }


    private FilterDialog(FragmentManager fragmentManager, OnFilterClick onFilterClick, String category, String country, String ingredient) {
        this.fragmentManager = fragmentManager;
        this.onFilterClick = onFilterClick;
        this.category = category;
        this.country = country;
        this.ingredient = ingredient;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Filter", "onCreate");
        presenter = new FilterPresenter(this, Repo.getInstance(
                ApiClient.getInstance(),
                ConcreteLocalSource.getInstance(getContext())));
        categories = new ArrayList<>();
        countries = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_layout, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("Filter", "onSave");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cgCategory = view.findViewById(R.id.cg_category);
        cgCountry = view.findViewById(R.id.cg_country);
        cgIngredient = view.findViewById(R.id.cg_ingredient);
        btnContinue = view.findViewById(R.id.btn_continue);

        presenter.getCategories();
        presenter.getCountries();
        //presenter.getIngredients();

        cgCategory.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip != null) {
                category = chip.getText().toString().trim();
            } else {
                category = null;
            }
        });
        cgCountry.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip != null) {
                country = chip.getText().toString().trim();
            } else {
                country = null;
            }
        });
        cgIngredient.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip != null) {
                ingredient = chip.getText().toString().trim();
            } else {
                ingredient = null;
            }
        });
        btnContinue.setOnClickListener(v -> onFilterClick.onFilterClick(category, country, ingredient));

        /*handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i("Filter",msg.arg1+"");
                if (msg.arg1==0){
                    for (Chip chip : categories) {
                        cgCategory.addView(chip);
                    }
                }else if (msg.arg1==1){
                    for (Chip chip : countries) {
                        cgCountry.addView(chip);
                    }
                }else {
                    for (Chip chip : ingredients) {
                        cgIngredient.addView(chip);
                    }
                }
            }
        };*/
    }

    private Chip createChip(String title) {
        Chip chip = (Chip) getLayoutInflater().inflate(R.layout.chip_layout, null);
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

    @Override
    public void onGetCategories(List<Category> categories) {
        List<Chip> chips = categories.stream().map(e -> createChip(e.getStrCategory())).collect(Collectors.toList());
        for (Chip chip : chips) {
            if (category != null && chip.getText().toString().trim().equals(category)) {
                chip.setChecked(true);
            }
            cgCategory.addView(chip);
        }
        /*new Thread(()->{
            this.categories = categories.stream().map(e->createChip(e.getStrCategory())).collect(Collectors.toList());
            handler.sendEmptyMessage(0);
        }).start();*/
    }

    @Override
    public void onGetCountries(List<Country> countries) {
        List<Chip> chips = countries.stream().map(e -> createChip(e.getStrArea())).collect(Collectors.toList());
        for (Chip chip : chips) {
            if (country != null && chip.getText().toString().trim().equals(country)) {
                chip.setChecked(true);
            }
            cgCountry.addView(chip);
        }
        /*new Thread(()->{
            this.countries = countries.stream().map(e->createChip(e.getStrArea())).collect(Collectors.toList());
            handler.sendEmptyMessage(1);
        }).start();*/
    }

    @Override
    public void onGetIngredient(List<Ingredient> ingredients) {
        List<Chip> chips = ingredients.stream().map(e -> createChip(e.getStrIngredient())).collect(Collectors.toList());
        for (Chip chip : chips) {
            cgIngredient.addView(chip);
        }
        /*new Thread(()->{
            this.ingredients = ingredients.stream().map(e->createChip(e.getStrIngredient())).collect(Collectors.toList());
            handler.sendEmptyMessage(2);
        }).start();*/
    }

    public void showFilter() {
        if (!instance.isVisible()) {
            instance.show(fragmentManager, "filter");
        }
    }

    public void dismissFilter() {
        instance.dismiss();
    }

}
