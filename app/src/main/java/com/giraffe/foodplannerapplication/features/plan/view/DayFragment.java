package com.giraffe.foodplannerapplication.features.plan.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.plan.presenter.PlanPresenter;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.PlannedMeal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import kotlin.Pair;

public class DayFragment extends Fragment implements DayView, DayAdapter.OnPlannedMealClick {
    private static final String TAG = "DayFragment";
    private TextView tvDay;
    private Pair<String, Integer> day;

    private PlanPresenter presenter;

    private DayAdapter adapter;

    private RecyclerView recyclerView;

    private ArrayList<PlannedMeal> meals;
    private int deletePosition;


    public DayFragment() {
        Log.i(TAG, "DayFragment empty constructor");
    }

    public DayFragment(Pair<String, Integer> day) {
        Log.i(TAG, "DayFragment constructor");
        this.day = day;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        if (savedInstanceState != null) {
            String dayStr = savedInstanceState.getString("dayStr");
            int dayInt = savedInstanceState.getInt("dayInt");
            this.day = new Pair<>(dayStr, dayInt);
        }
        presenter = new PlanPresenter(this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                ));
        meals = new ArrayList<>();
        adapter = new DayAdapter(meals, this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_day, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");
        //tvDay = view.findViewById(R.id.tv_day);
        recyclerView = view.findViewById(R.id.rv_planned_meals);
        recyclerView.setAdapter(adapter);
        presenter.getPlannedMeals(day.getSecond());
        //tvDay.setText(day.getFirst());
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
        outState.putString("dayStr", day.getFirst());
        outState.putInt("dayInt", day.getSecond());
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
    public void onGetPlannedMeals(Observable<List<PlannedMeal>> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(plannedMeals -> {
                    adapter.setList(plannedMeals);
                    //tvDay.setText(plannedMeals.size() + "");
                }, throwable -> Log.i(TAG, "error: " + throwable.getMessage()));
        /*observable.map(
                        plannedMeals -> plannedMeals.stream().filter(meal -> {
                            Log.i(TAG, "meal at: " + meal.getDay());
                            if (meal.getDay() == day.getSecond()) {
                                return true;
                            } else {
                                return false;
                            }
                        }).collect(Collectors.toList())
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribe(plannedMeals -> {
                    Log.i(TAG,"plan size: "+plannedMeals.size());
                    tvDay.setText(plannedMeals.size()+"");
                }, throwable -> Log.i(TAG, "error: "+throwable.getMessage()));*/
    }

    @Override
    public void onPlannedMealDeleted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
            adapter.getPlannedMeals().remove(deletePosition);
            adapter.notifyDataSetChanged();
            //adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onDeleteClick(PlannedMeal plannedMeal,int position) {
        deletePosition = position;
        presenter.deletePlannedMeal(plannedMeal);
    }

    @Override
    public void onClick(Meal meal) {

    }
}