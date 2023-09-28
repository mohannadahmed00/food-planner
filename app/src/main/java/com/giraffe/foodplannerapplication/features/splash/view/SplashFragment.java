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

public class SplashFragment extends Fragment {
    private final static String TAG = "SplashFragment";
    Animation fadeIn;
    ImageView logo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //Navigation.findNavController(view).setGraph(R.navigation.main_graph)
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            //i use it just for passing data
            //Navigation.findNavController(view).navigate(SplashFragmentDirections.actionSplashFragmentToMainGraph());
            //i use it to never go back
            //Navigation.findNavController(view).setGraph(R.navigation.main_graph);
            Navigation.findNavController(view).setGraph(R.navigation.on_board_graph);
        }, 4000);
    }
}