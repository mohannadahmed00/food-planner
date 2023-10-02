package com.giraffe.foodplannerapplication.features.meals.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.meals.presenter.MealsPresenter;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class MealsFragment<T> extends Fragment implements MealsView, MealsAdapter.OnMealClick {
    public static final String TAG = "MealsFragment";

    private Category category;
    private Country country;
    private MealsPresenter presenter;

    private final MealsAdapter adapter;

    private final List<Meal> meals;
    private RecyclerView rvMeals;

    MealsFragment(T item) {
        if (item instanceof Category) {
            category = (Category) item;
        } else {
            country = (Country) item;
        }
        meals = new ArrayList<>();
        adapter = new MealsAdapter(meals, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealsPresenter(this, Repo.getInstance(
                ApiClient.getInstance(),
                ConcreteLocalSource.getInstance(getContext())
        ));
        if (category != null) {
            presenter.getCategoryMeals(category.getStrCategory());
        } else {
            presenter.getCountryMeals(country.getStrArea());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inflateViews(view);
        rvMeals.setAdapter(adapter);
    }

    @Override
    public void inflateViews(View view) {
        rvMeals = view.findViewById(R.id.rv_meals);
    }

    @Override
    public void initClicks() {

    }

    @Override
    public void onGetMeals(List<Meal> meals) {
        adapter.setList(meals);
    }

    @Override
    public void onClick(Meal meal) {
        Toast.makeText(requireContext(), meal.getStrMeal(), Toast.LENGTH_SHORT).show();
    }
}