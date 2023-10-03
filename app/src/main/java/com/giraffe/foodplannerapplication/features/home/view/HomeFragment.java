package com.giraffe.foodplannerapplication.features.home.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.giraffe.foodplannerapplication.R;
import com.giraffe.foodplannerapplication.database.ConcreteLocalSource;
import com.giraffe.foodplannerapplication.features.home.presenter.HomePresenter;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.giraffe.foodplannerapplication.util.Filter.view.FilterDialog;
import com.giraffe.foodplannerapplication.util.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;

public class HomeFragment extends Fragment implements HomeView, OnFilterClick, CategoriesAdapter.OnCategoryClick, CountriesAdapter.OnCountryClick, SearchAdapter.OnSearchClick {
    public final static String TAG = "HomeFragment";

    private HomePresenter presenter;

    private ImageView ivFilter, ivRandom;
    private TextView tvRandom;

    private EditText edtSearch;

    private View viewBlur;

    private String category, country, ingredient;
    private RecyclerView rvSearch, rvCategories, rvCountries;
    private CategoriesAdapter categoriesAdapter;
    private CountriesAdapter countriesAdapter;
    private SearchAdapter searchAdapter;


    private List<Category> categories;
    private List<Country> countries;
    private List<Meal> meals;

    private Meal randomMeal;

    private Observable<String> observable;

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
        categories = new ArrayList<>();
        countries = new ArrayList<>();
        meals = new ArrayList<>();
        categoriesAdapter = new CategoriesAdapter(categories, this);
        countriesAdapter = new CountriesAdapter(countries, this);
        searchAdapter = new SearchAdapter(meals, this);


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


        rvCategories.setAdapter(categoriesAdapter);
        rvCountries.setAdapter(countriesAdapter);
        rvSearch.setAdapter(searchAdapter);
        presenter.getRandomMeal();
        presenter.getCategories();
        presenter.getCountries();
    }


    @Override
    public void inflateViews(View view) {
        ivFilter = view.findViewById(R.id.iv_filter);
        viewBlur = view.findViewById(R.id.v_blur);
        edtSearch = view.findViewById(R.id.edt_search);
        ivRandom = view.findViewById(R.id.iv_random);
        tvRandom = view.findViewById(R.id.tv_random);
        rvCategories = view.findViewById(R.id.rv_category);
        rvCountries = view.findViewById(R.id.rv_country);
        rvSearch = view.findViewById(R.id.rv_search);
    }

    @Override
    public void initClicks() {
        ivFilter.setOnClickListener(v -> FilterDialog.getInstance(getParentFragmentManager(), this, category, country, ingredient).showFilter());
        ivRandom.setOnClickListener(view -> {
            if (randomMeal!=null) {
                HomeFragmentDirections.ActionHomeFragmentToDetailsFragment action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(randomMeal);
                Navigation.findNavController(requireView()).navigate(action);
            }
        });
    }

    void handleSearch() {

        observable = Observable.create(emitter -> {
            edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    /*if (charSequence.length()<2){
                        viewBlur.setVisibility(View.INVISIBLE);
                        rvSearch.setVisibility(View.INVISIBLE);
                    }else {*/
                    if (charSequence.length() < 2) {
                        viewBlur.setVisibility(View.INVISIBLE);
                        rvSearch.setVisibility(View.INVISIBLE);
                    }
                    emitter.onNext(charSequence.toString());
                    //}
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        });
        observable = observable.delay(1, TimeUnit.SECONDS).debounce(1, TimeUnit.SECONDS);
        observable.subscribe(s -> {
            if (s.length() >= 2) {
                Log.i(TAG, "search: " + s);
                showDialog();
                presenter.getSearchResult(s);
            }
            //presenter.getSearchResult(s);
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
        this.randomMeal = meal;
        handleRandomMealTitle(meal.getStrMeal());
        handleRandomMealImg(meal.getStrMealThumb());
    }

    @Override
    public void onGetCategories(List<Category> categories) {
        categoriesAdapter.setList(categories);
    }

    @Override
    public void onGetCountries(List<Country> countries) {
        countriesAdapter.setList(countries);
    }

    @Override
    public void onGetSearchResult(List<Meal> meals) {
        dismissDialog();
        if (meals == null || meals.isEmpty()) {
            viewBlur.setVisibility(View.INVISIBLE);
            rvSearch.setVisibility(View.INVISIBLE);
        } else {
            if (!edtSearch.getText().toString().isEmpty()) {
                viewBlur.setVisibility(View.VISIBLE);
                rvSearch.setVisibility(View.VISIBLE);
                searchAdapter.setList(meals);
            }
        }
    }

    @Override
    public void onFilterClick(String category, String country, String ingredient) {
        this.category = category;
        this.country = country;
        this.ingredient = ingredient;
        FilterDialog.getInstance(getParentFragmentManager(), this, category, country, ingredient).dismissFilter();
        if (category != null || country != null || ingredient != null) {
            ivFilter.setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange));
        } else {
            ivFilter.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray));
        }
    }

    public void showDialog() {
        LoadingDialog.getInstance(getParentFragmentManager()).showLoading();
    }

    public void dismissDialog() {
        LoadingDialog.getInstance(getParentFragmentManager()).dismissLoading();
    }

    @Override
    public void onClick(Category category) {
        HomeFragmentDirections.ActionHomeFragmentToMealsFragment action = HomeFragmentDirections.actionHomeFragmentToMealsFragment(categoriesAdapter.getList(), "category", this.categories.indexOf(category));
        Navigation.findNavController(requireView()).navigate(action);
        Log.i(TAG, category.getStrCategory());
    }

    @Override
    public void onClick(Country country) {
        HomeFragmentDirections.ActionHomeFragmentToMealsFragment action = HomeFragmentDirections.actionHomeFragmentToMealsFragment(countriesAdapter.getList(), "country", this.countries.indexOf(country));
        Navigation.findNavController(requireView()).navigate(action);
        Log.i(TAG, country.getStrArea());
    }

    @Override
    public void onSearchClick(Meal meal) {
        edtSearch.setText("");
        HomeFragmentDirections.ActionHomeFragmentToDetailsFragment action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(meal);
        Navigation.findNavController(requireView()).navigate(action);
    }
}