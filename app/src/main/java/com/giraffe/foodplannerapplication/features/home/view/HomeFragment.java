package com.giraffe.foodplannerapplication.features.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.home.presenter.HomePresenter;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.giraffe.foodplannerapplication.util.LoadingDialog;

public class HomeFragment extends Fragment implements HomeView {
    private final static String TAG = "HomeFragment";

    LoadingDialog loading;

    HomePresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(this, Repo.getInstance(
                ApiClient.getInstance(),
                ConcreteLocalSource.getInstance(getContext())
        ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getRandomMeal();
        showDialog();
    }


    @Override
    public void onGetRandomMeal(Meal meal) {
        dismissDialog();
        Log.i(TAG, meal.getStrMeal());
    }

    @Override
    public void onFailGetRandomMeal(String errorMsg) {
        dismissDialog();
        Log.i(TAG, errorMsg);
    }

    public void showDialog() {
        FragmentManager fragmentManager = getParentFragmentManager();
        loading = new LoadingDialog();
        loading.show(fragmentManager, "dialog");

        //just for try
        //Handler handler = new Handler();
        //handler.postDelayed(newFragment::dismiss, 4000);
    }

    public void dismissDialog() {
        if (loading != null) {
            loading.dismiss();
        }
    }
}