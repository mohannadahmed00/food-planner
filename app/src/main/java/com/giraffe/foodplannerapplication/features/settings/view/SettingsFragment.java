package com.giraffe.foodplannerapplication.features.settings.view;

import android.animation.Animator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.settings.presenter.SettingsPresenter;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.giraffe.foodplannerapplication.util.NetworkConnection;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class SettingsFragment extends Fragment implements SettingsView {
    public static final String TAG = "SettingsFragment";
    private Button btnLogout, btnBackup;
    private SettingsPresenter presenter;

    private LottieAnimationView lvDone;

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
        lvDone.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                lvDone.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });
        presenter.isLoggedIn();
    }

    @Override
    public void initViews(View view) {
        lvDone = view.findViewById(R.id.lv_done);
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
                        btnBackup.setOnClickListener(v -> {
                            if (isConnected()) {
                                presenter.backup();
                            }
                        });
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
                () -> {
                    lvDone.setVisibility(View.VISIBLE);
                    lvDone.playAnimation();
                    Log.i(TAG, "Data has been backed up");
                }, throwable -> Log.e(TAG, throwable.getMessage())
        );
    }

    private boolean isConnected() {
        if (NetworkConnection.isConnected(requireContext())) {
            return true;
        } else {
            Toast.makeText(getContext(), R.string.check_your_internet_connection_and_try_again, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}