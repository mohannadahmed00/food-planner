package com.giraffe.foodplannerapplication.features.splash.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.splash.presenter.SplashPresenter;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;

public class SplashFragment extends Fragment implements SplashView {
    private final static String TAG = "SplashFragment";
    private Animation fadeIn;
    private ImageView logo;

    private SplashPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenter(this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        logo = view.findViewById(R.id.iv_logo);
        logo.startAnimation(fadeIn);
        Handler handler = new Handler();
        handler.postDelayed(() -> presenter.isLoggedIn(), 4000);
    }

    @Override
    public void onGetLoggedInFlag(boolean isLoggedIn) {
        if (isLoggedIn) {
            Navigation.findNavController(requireView()).setGraph(R.navigation.main_graph);
        } else {
            //check it is your first time or not
            Navigation.findNavController(requireView()).setGraph(R.navigation.on_board_graph);
        }
    }
}