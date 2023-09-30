package com.giraffe.foodplannerapplication.features.home.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.home.presenter.HomePresenter;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.giraffe.foodplannerapplication.util.FilterDialog;
import com.giraffe.foodplannerapplication.util.LoadingDialog;

public class HomeFragment extends Fragment implements HomeView ,OnFilterClick{
    public final static String TAG = "HomeFragment";

    private HomePresenter presenter;

    private ImageView ivFilter,ivRandom;
    private TextView tvRandom;

    private EditText edtSearch;

    private View viewBlur;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

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
        inflateViews(view);
        initClicks();
        handleSearch();
        presenter.getRandomMeal();
    }
    @Override
    public void inflateViews(View view) {
        ivFilter =  view.findViewById(R.id.iv_filter);
        viewBlur = view.findViewById(R.id.v_blur);
        edtSearch = view.findViewById(R.id.edt_search);
        ivRandom = view.findViewById(R.id.iv_random);
        tvRandom = view.findViewById(R.id.tv_random);
    }

    @Override
    public void initClicks() {
        ivFilter.setOnClickListener(v->{
            FilterDialog.getInstance(getParentFragmentManager(),this).showFilter();
        });
    }

    void handleSearch() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    viewBlur.setVisibility(View.INVISIBLE);
                } else {
                    viewBlur.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void handleRandomMealImg(String imgUrl) {
        Glide.with(requireContext()).load(imgUrl).into(ivRandom);
    }

    void handleRandomMealTitle(String mealTitle) {
        String title = "What about trying " + mealTitle + " today?";
        Spannable spannableString = new SpannableString(title);
        int startIndex = title.indexOf(mealTitle);
        int endIndex = startIndex + mealTitle.length();
        int color = ContextCompat.getColor(requireContext(), R.color.yellow);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRandom.setText(spannableString);
    }

    @Override
    public void onGetRandomMeal(Meal meal) {
        handleRandomMealTitle(meal.getStrMeal());
        handleRandomMealImg(meal.getStrMealThumb());
    }

    @Override
    public void onGetRandomMealFail(String errorMsg) {
        Log.i(TAG, errorMsg);
    }

    public void showDialog() {
        LoadingDialog.getInstance(getParentFragmentManager()).showLoading();
    }

    public void dismissDialog() {
        LoadingDialog.getInstance(getParentFragmentManager()).dismissLoading();
    }


    @Override
    public void onFilterClick(String category,String country,String ingredient) {
        FilterDialog.getInstance(getParentFragmentManager(),this).dismissFilter();
        Log.i(TAG,"category: " + category +" / country: " + country+" / ingredient: " + ingredient);

    }
}