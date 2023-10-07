package com.giraffe.foodplannerapplication.features.home.Filter.view;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.giraffe.foodplannerapplication.features.home.Filter.presenter.FilterPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class FilterDialog extends DialogFragment implements FilterView {
    private static final String TAG = "FilterDialog";

    private static FilterDialog instance;

    private final FragmentManager fragmentManager;

    private ChipGroup cgCategory, cgCountry, cgIngredient;

    private String category, country, ingredient;

    private final OnFilterClick onFilterClick;

    private FilterPresenter presenter;

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
        Button btnContinue = view.findViewById(R.id.btn_continue);

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
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setLayout(width, height);
            }
        }
    }

    @Override
    public void onGetCategories(Observable<List<Category>> observable) {
        observable.flatMap(categories -> Observable.fromIterable(categories))
                .map(category -> category.getStrCategory())
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> createChip(s))
                .subscribe(chip -> {
                    if (category != null && chip.getText().toString().trim().equals(category)) {
                        chip.setChecked(true);
                    }
                    cgCategory.addView(chip);
                }, throwable -> Log.i(TAG, throwable.getMessage()));
    }

    @Override
    public void onGetCountries(Observable<List<Country>> observable) {
        observable.flatMap(countries -> Observable.fromIterable(countries))
                .map(country -> country.getStrArea())
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> createChip(s))
                .subscribe(chip -> {
                    if (country != null && chip.getText().toString().trim().equals(country)) {
                        chip.setChecked(true);
                    }
                    cgCountry.addView(chip);
                }, throwable -> Log.i(TAG, throwable.getMessage()));
    }

    @Override
    public void onGetIngredient(Observable<List<Ingredient>> observable) {
        observable.flatMap(ingredients -> Observable.fromIterable(ingredients))
                .map(ingredient -> ingredient.getStrIngredient())
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> createChip(s))
                .subscribe(chip -> {
                    if (ingredient != null && chip.getText().toString().trim().equals(ingredient)) {
                        chip.setChecked(true);
                    }
                    cgIngredient.addView(chip);
                }, throwable -> Log.i(TAG, throwable.getMessage()));
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
