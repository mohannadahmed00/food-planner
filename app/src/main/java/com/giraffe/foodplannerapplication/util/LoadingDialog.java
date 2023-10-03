package com.giraffe.foodplannerapplication.util;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.giraffe.foodplannerapplication.R;

public class LoadingDialog extends DialogFragment {
    private final static String TAG = "LoadingDialog";

    private static LoadingDialog instance;

    private static FragmentManager mFragmentManager;

    public static LoadingDialog getInstance(FragmentManager fragmentManager) {

        if (mFragmentManager == null || mFragmentManager.isDestroyed()) {
            mFragmentManager = fragmentManager;
        }

        if (instance == null) {
            instance = new LoadingDialog(fragmentManager);
        }
        return instance;
    }

    public void showLoading() {
        if (!instance.isVisible()) {
            instance.show(mFragmentManager, "loading");
        }
    }

    public void dismissLoading() {
        instance.dismiss();
    }

    private LoadingDialog(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loading_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }
}
