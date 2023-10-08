package com.giraffe.foodplannerapplication.features.meals.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    //public static final String TAG = "MealsFragment";
    public static final String CALLBACK = "callbacks";
    public String TAG = "MealsFragment";

    private Category category;
    private Country country;
    private MealsPresenter presenter;

    private MealsAdapter adapter;

    private List<Meal> meals, favMeals;
    private RecyclerView rvMeals;

    private TabsView tabsView;
    private Context context;
    private boolean isLoggedIn;


    MealsFragment(T item, TabsView tabsView) {
        this.tabsView = tabsView;
        if (item instanceof Category) {
            category = (Category) item;
            TAG = "MealsFragment: " + category.getStrCategory();
        } else {
            country = (Country) item;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        isLoggedIn = false;
        meals = new ArrayList<>();
        favMeals = new ArrayList<>();
        adapter = new MealsAdapter(meals, this, false);
        presenter = new MealsPresenter(this, Repo.getInstance(
                ApiClient.getInstance(),
                ConcreteLocalSource.getInstance(getContext())
        ));
        presenter.isLoggedIn();
        if (category != null) {
            presenter.getCategoryMeals(category.getStrCategory());
        } else {
            presenter.getCountryMeals(country.getStrArea());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        inflateViews(view);
        rvMeals.setAdapter(adapter);
        presenter.getFavMeals();
    }


    @Override
    public void inflateViews(View view) {
        rvMeals = view.findViewById(R.id.rv_meals);
    }

    @Override
    public void initClicks() {

    }

    @Override
    public void onGetLoggedInFlag(Observable<Boolean> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isLoggedIn -> {
                            Log.i(TAG, "onGetLoggedInFlag");
                            this.isLoggedIn = isLoggedIn;
                        }, throwable -> {

                            Log.e(TAG, throwable.getMessage());
                        }
                );
    }
    @Override
    public void onGetFavMeals(Observable<List<Meal>> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favMeals -> {
                            Log.i(TAG, "onGetFavMeals: " + favMeals.size());
                            this.favMeals.addAll(favMeals);
                            adapter.updateFavorites(favMeals);
                        },
                        throwable -> Log.e(TAG, throwable.getMessage())
                );
    }
    @Override
    public void onGetMeals(Observable<List<Meal>> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            Log.i(TAG, "onGetMeals: " + meals.size());
                            this.meals.addAll(meals);
                            adapter.updateList(meals,isLoggedIn);
                            adapter.updateFavorites(favMeals);
                        }, throwable -> Log.e(TAG, throwable.getMessage())
                );
    }











    @Override
    public void onGetMeal(Observable<Meal> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    Log.i(TAG, "onGetMeal: " + meal.getStrMeal());
                    dismissDialog();
                    tabsView.onMealClick(meal);
                }, throwable -> Log.i(TAG, throwable.getMessage()));
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
            Log.i(TAG, "old meal");
            tabsView.onMealClick(temp);
            //return;
        } else {
            Log.i(TAG, "new meal");
            showDialog();
            presenter.getMealById(meal.getIdMeal());
        }
        //Toast.makeText(requireContext(), meal.getStrMeal()+":"+meal.getIdMeal(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavClick(Meal meal, Boolean isSelect) {
        if (isSelect) {
            //showDialog();
            presenter.insertMeal(meal);
        } else {
            presenter.deleteMeal(meal);
        }
    }

    @Override
    public void onMealInserted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.i(TAG, "onMealInserted");

                            //dismissDialog();
                            presenter.getFavMeals();
                        },
                        throwable -> {
                            //dismissDialog();
                            Log.e(TAG, throwable.getMessage());
                        }
                );
    }

    @Override
    public void onMealDeleted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.i(TAG, "onMealDeleted");
                        },
                        throwable -> Log.e(TAG, throwable.getMessage())
                );
    }



    public void showDialog() {
        LoadingDialog.getInstance(getChildFragmentManager()).showLoading();
    }

    public void dismissDialog() {
        LoadingDialog.getInstance(getChildFragmentManager()).dismissLoading();
    }



    private void handleMeals(List<Meal> list) {
        //adapter.updateList(list, isLoggedIn);
        adapter.updateList(list.stream().map(
                meal -> {
                    favMeals.forEach(
                            fav -> {
                                meal.setSelected(fav.getIdMeal().equals(meal.getIdMeal()));
                            }
                    );
                    return meal;
                }
        ).collect(Collectors.toList()),isLoggedIn);








        /*if (!favMeals.isEmpty()){
            meals.forEach(meal -> {
                favMeals.forEach(fav -> {
                    meal.setSelected(meal.getIdMeal().equals(fav.getIdMeal()));
                });
            });
            Log.e(TAG,"meals size after filter: "+meals.size());
        }*/
    }




    //===========================life cycle===========================
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");

    }

}