package com.giraffe.foodplannerapplication.features.settings.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.settings.presenter.SettingsPresenter;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class SettingsFragment extends Fragment implements SettingsView {
    public static final String TAG = "SettingsFragment";
    private Button btnLogout, btnBackup;
    private SettingsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SettingsPresenter(this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        //initClicks();
        presenter.isLoggedIn();
    }

    @Override
    public void initViews(View view) {
        btnLogout = view.findViewById(R.id.btn_logout);
        btnBackup = view.findViewById(R.id.btn_backup);

    }

    @Override
    public void initClicks() {
        //btnLogout.setOnClickListener(v -> presenter.logout());
    }

    @Override
    public void onLogout(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> presenter.deleteFavoriteMeals(), throwable -> Log.i(TAG, throwable.getMessage())
                );
    }

    @Override
    public void onGetLoggedInFlag(Observable<Boolean> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(isLoggedIn -> {
                    if (isLoggedIn) {
                        btnBackup.setVisibility(View.VISIBLE);
                        btnLogout.setText(R.string.logout);
                        btnBackup.setOnClickListener(v -> presenter.backup());
                        btnLogout.setOnClickListener(v -> presenter.logout());
                    } else {
                        btnBackup.setVisibility(View.INVISIBLE);
                        btnLogout.setText(R.string.login);
                        btnLogout.setOnClickListener(v -> Navigation.findNavController(v).setGraph(R.navigation.auth_graph));
                    }
                }, throwable -> {
                    btnBackup.setVisibility(View.INVISIBLE);
                    btnLogout.setText(R.string.login);
                    btnLogout.setOnClickListener(v -> Navigation.findNavController(v).setGraph(R.navigation.auth_graph));
                    Log.i(TAG, throwable.getMessage());

                });
    }

    @Override
    public void onFavoritesDeleted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> presenter.deletePlannedMeals(), throwable -> Log.i(TAG, throwable.getMessage())
                );
    }

    @Override
    public void onPlannedDeleted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Navigation.findNavController(requireView()).setGraph(R.navigation.auth_graph);
                        }, throwable -> Log.i(TAG, throwable.getMessage())
                );
    }

    @Override
    public void onDataBackedUp(Completable completable) {
        completable.subscribe(
                () -> Log.i(TAG, "Data has been backed up"), throwable -> Log.e(TAG, throwable.getMessage())
        );
    }
}