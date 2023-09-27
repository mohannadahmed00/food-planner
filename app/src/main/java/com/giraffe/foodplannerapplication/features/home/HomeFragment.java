package com.giraffe.foodplannerapplication.features.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.util.LoadingDialog;

public class HomeFragment extends Fragment {
    LoadingDialog loading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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