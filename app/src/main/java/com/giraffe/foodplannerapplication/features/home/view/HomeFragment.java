package com.giraffe.foodplannerapplication.features.home.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.DatePicker;
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
import com.giraffe.foodplannerapplication.models.PlannedMeal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.ApiClient;
import com.giraffe.foodplannerapplication.util.BottomSheet;
import com.giraffe.foodplannerapplication.util.Filter.view.FilterDialog;
import com.giraffe.foodplannerapplication.util.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class HomeFragment extends Fragment implements HomeView, OnFilterClick, CategoriesAdapter.OnCategoryClick, CountriesAdapter.OnCountryClick, SearchAdapter.OnSearchClick {
    public final static String TAG = "HomeFragment";

    private HomePresenter presenter;

    private ImageView ivFilter, ivRandom, ivFav, ivAdd;
    private TextView tvRandom;

    private EditText edtSearch;

    private View viewBlur;

    private String category, country, ingredient, selectedDate;
    private RecyclerView rvSearch, rvCategories, rvCountries;
    private CategoriesAdapter categoriesAdapter;
    private CountriesAdapter countriesAdapter;
    private SearchAdapter searchAdapter;


    private List<Category> categories;
    private List<Country> countries;
    private List<Meal> meals;

    private Meal randomMeal;

    private ChipGroup cgType;
    private TextView tvWeekDay;
    private Button btnConfirm;

    private int mealType;

    private BottomSheetDialog bottomSheetDialog;

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
        presenter.getRandomMeal();
        presenter.getCategories();
        presenter.getCountries();
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
        if (randomMeal != null) {
            handleRandomMealImg(randomMeal.getStrMealThumb());
            handleRandomMealTitle(randomMeal.getStrMeal());
        }


        rvCategories.setAdapter(categoriesAdapter);
        rvCountries.setAdapter(countriesAdapter);
        rvSearch.setAdapter(searchAdapter);

    }


    @Override
    public void inflateViews(View view) {
        ivFav = view.findViewById(R.id.iv_fav);
        ivAdd = view.findViewById(R.id.iv_add);
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
            if (randomMeal != null) {
                HomeFragmentDirections.ActionHomeFragmentToDetailsFragment action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(randomMeal);
                Navigation.findNavController(requireView()).navigate(action);
            }
        });
        ivFav.setOnClickListener(v -> {
            if (randomMeal != null) {
                if (randomMeal.isSelected()) {
                    randomMeal.setSelected(false);
                    presenter.deleteFavMeal(randomMeal);
                } else {
                    randomMeal.setSelected(true);
                    presenter.insertFavMeal(randomMeal);
                }
            }
        });

        ivAdd.setOnClickListener(v -> {
            //showDatePickerDialog(getContext());
            //showBottomSheetDialog();
            if (randomMeal != null) {
                BottomSheet.showBottomSheetDialog(getContext(), randomMeal, new BottomSheet.OnBottomConfirmed() {
                    @Override
                    public void onClick(PlannedMeal plannedMeal, BottomSheetDialog dialog) {
                        bottomSheetDialog = dialog;
                        //dialog.dismiss();
                        presenter.insertPlannedMeal(plannedMeal);
                        Log.i(TAG, "store " + plannedMeal.getMeal().getStrMeal() + " as " + plannedMeal.getType() + " at " + plannedMeal.getDate().toString());
                    }
                });
            }
            /*PlannedMeal plannedMeal= new PlannedMeal(PlannedMeal.MealType.BREAKFAST, new Date().getTime(),randomMeal);
            presenter.insertPlannedMeal(plannedMeal);*/
        });
    }

    void handleSearch() {
        Observable
                .create(emitter -> {
                    edtSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
                })
                .debounce(1, TimeUnit.SECONDS)
                .filter(o -> o.toString().length() > 2)
                .distinctUntilChanged()
                .subscribe(s -> {
                    Log.i(TAG, "search: " + s);
                    showDialog();
                    presenter.getSearchResult(s.toString());
                }, throwable -> Log.i(TAG, throwable.getMessage()));

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
    public void onGetRandomMeal(Observable<Meal> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    this.randomMeal = meal;
                    handleRandomMealTitle(meal.getStrMeal());
                    handleRandomMealImg(meal.getStrMealThumb());
                }, throwable -> Log.i(TAG, throwable.getMessage()));
    }

    @Override
    public void onGetCategories(Observable<List<Category>> observable) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> categoriesAdapter.setList(categories), throwable -> Log.i(TAG, throwable.getMessage()));
    }

    @Override
    public void onGetCountries(Observable<List<Country>> observable) {
        observable.map(countries -> countries.stream().map(country -> {
                    country.setStrCode(getCountryCode(country.getStrArea()));
                    return country;
                }).collect(Collectors.toList())).observeOn(AndroidSchedulers.mainThread())
                .subscribe(countries -> countriesAdapter.setList(countries), throwable -> Log.i(TAG, "error: " + throwable.getMessage()));
    }

    @Override
    public void onGetSearchResult(Observable<List<Meal>> observable) {

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
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
                }, throwable -> Log.i(TAG, throwable.getMessage()));
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

    @Override
    public void onFavMealInserted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            randomMeal.setSelected(true);
                            ivFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red));
                        },
                        throwable -> {
                            randomMeal.setSelected(false);
                            Log.i(TAG, throwable.getMessage());
                        }
                );
    }

    @Override
    public void onFavMealDeleted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> ivFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white)),
                        throwable -> randomMeal.setSelected(true)
                );
    }

    @Override
    public void onPlannedMealInserted(Completable completable) {
        completable.observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    if (bottomSheetDialog != null) {
                        bottomSheetDialog.dismiss();
                    }
                },
                throwable -> Log.i(TAG, "meal has not be planned")
        );
    }


    public String getCountryCode(String country) {
        switch (country) {
            case "American":
                return "US";
            case "British":
                return "GB";
            case "Canadian":
                return "CA";
            case "Chinese":
                return "CN";
            case "Croatian":
                return "HR";
            case "Dutch":
                return "DE";
            case "Egyptian":
                return "EG";
            case "Filipino":
                return "PH";
            case "French":
                return "FR";
            case "Greek":
                return "GR";
            case "Indian":
                return "IN";
            case "Irish":
                return "IE";
            case "Italian":
                return "IT";
            case "Jamaican":
                return "JM";
            case "Japanese":
                return "JP";
            case "Kenyan":
                return "KE";
            case "Malaysian":
                return "MY";
            case "Mexican":
                return "MX";
            case "Moroccan":
                return "MA";
            case "Polish":
                return "PL";
            case "Portuguese":
                return "PT";
            case "Russian":
                return "RU";
            case "Spanish":
                return "ES";
            case "Thai":
                return "TH";
            case "Tunisian":
                return "TN";
            case "Turkish":
                return "TR";
            case "Vietnamese":
                return "VN";
            default:
                return "Unknown";
        }
    }
}