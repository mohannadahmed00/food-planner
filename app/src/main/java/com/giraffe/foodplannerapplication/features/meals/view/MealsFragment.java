package com.giraffe.foodplannerapplication.features.meals.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.giraffe.foodplannerapplication.util.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class MealsFragment<T> extends Fragment implements MealsView, MealsAdapter.OnMealClick {
    public static final String TAG = "MealsFragment";

    private Category category;
    private Country country;
    private MealsPresenter presenter;

    private final MealsAdapter adapter;

    private final List<Meal> meals, favMeals;
    private RecyclerView rvMeals;

    private TabsView tabsView;
    private Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    MealsFragment(T item, TabsView tabsView) {
        this.tabsView = tabsView;
        if (item instanceof Category) {
            category = (Category) item;
        } else {
            country = (Country) item;
        }
        meals = new ArrayList<>();
        favMeals = new ArrayList<>();
        adapter = new MealsAdapter(meals, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealsPresenter(this, Repo.getInstance(
                ApiClient.getInstance(),
                ConcreteLocalSource.getInstance(getContext())
        ));
        presenter.getFavMeals();
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
        //presenter.getFavMeals();
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
    public void onGetMeal(Observable<Meal> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    dismissDialog();
                    tabsView.onMealClick(meal);
                });
    }

    @Override
    public void onClick(Meal meal) {
        Meal temp = null;
        for (Meal fav : favMeals) {
            if (fav.getIdMeal().equals(meal.getIdMeal())) {
                temp = fav;
                break;
            }
        }
        if (temp != null) {
            Log.i(TAG,"old meal");

            tabsView.onMealClick(temp);
            //return;
        }else {
            Log.i(TAG,"new meal");
            showDialog();
            presenter.getMealById(meal.getIdMeal());
        }
        //Toast.makeText(requireContext(), meal.getStrMeal()+":"+meal.getIdMeal(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavClick(Meal meal, Boolean isSelect) {
        if (isSelect) {
            presenter.insertMeal(meal);
        } else {
            presenter.deleteMeal(meal);
        }
    }

    @Override
    public void onMealInserted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Toast.makeText(context, "meal inserted", Toast.LENGTH_SHORT).show(),
                        throwable -> Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    public void onMealDeleted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Toast.makeText(context, "meal deleted", Toast.LENGTH_SHORT).show(),
                        throwable -> Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    public void showDialog() {
        LoadingDialog.getInstance(getChildFragmentManager()).showLoading();
    }

    public void dismissDialog() {
        LoadingDialog.getInstance(getChildFragmentManager()).dismissLoading();
    }

    @Override
    public void onGetFavMeals(Observable<List<Meal>> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favMeals -> this.favMeals.addAll(favMeals),
                        throwable -> Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    public void onGetMeals(Observable<List<Meal>> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            adapter.setList(meals.stream().map(m -> {
                                favMeals.stream().forEach(f -> {
                                    if (f.getIdMeal().equals(m.getIdMeal())) {
                                        m.setSelected(true);
                                    }
                                });
                                return m;
                            }).collect(Collectors.toList()));
                            //adapter.setList(meals);
                        },
                        throwable -> Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}