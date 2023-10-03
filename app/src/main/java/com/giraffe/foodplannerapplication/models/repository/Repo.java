package com.giraffe.foodplannerapplication.models.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.database.LocalSource;
import com.giraffe.foodplannerapplication.features.splash.view.SplashFragment;
import com.giraffe.foodplannerapplication.models.CategoriesResponse;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.CountriesResponse;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;
import com.giraffe.foodplannerapplication.models.IngredientsResponse;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.MealsResponse;
import com.giraffe.foodplannerapplication.network.NetworkCallback;
import com.giraffe.foodplannerapplication.network.RemoteSource;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repo implements RepoInterface {
    private final RemoteSource remoteSource;
    private final LocalSource localSource;
    private final FirebaseAuth mAuth;
    private static Repo repo = null;

    private Repo(RemoteSource remoteSource, LocalSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
        mAuth = FirebaseAuth.getInstance();
    }

    public static Repo getInstance(RemoteSource remoteSource, LocalSource localSource) {
        if (repo == null) {
            repo = new Repo(remoteSource, localSource);
        }
        return repo;
    }


    //=================remote functions=================
    @Override
    public void getRandomMeal(NetworkCallback<MealsResponse> callback) {
        Call<MealsResponse> call = remoteSource.makeNetworkCall(callback).getRandomMeal();
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MealsResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getMealById(String mealId, NetworkCallback<Meal> callback) {
        Call<MealsResponse> call = remoteSource.makeNetworkCall(callback).getMealById(mealId);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                callback.onSuccess(response.body().getMeals().get(0));
            }

            @Override
            public void onFailure(@NonNull Call<MealsResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void createAccount(String email, String password, NetworkCallback<Boolean> callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mAuth.signOut();
                        callback.onSuccess(true);
                    } else {
                        if (task.getException() != null) {
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void login(String email, String password, NetworkCallback<Boolean> callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(true);
                    } else {
                        if (task.getException() != null) {
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public boolean logout() {
        mAuth.signOut();
        return mAuth.getCurrentUser() == null;
    }

    @Override
    public void getCategories(NetworkCallback<List<Category>> callback) {
        List<Category> categories = localSource.readCategories();
        if (categories == null || categories.isEmpty()) {
            Log.i(SplashFragment.TAG, "getCategories from remote");
            Call<CategoriesResponse> call = remoteSource.makeNetworkCall(callback).getCategories();
            call.enqueue(new Callback<CategoriesResponse>() {
                @Override
                public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {
                    localSource.storeCategories(response.body());
                    if (response.body() != null) {
                        callback.onSuccess(response.body().getCategories());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {
                    callback.onFailure(t.getMessage());
                }
            });
        } else {
            Log.i(SplashFragment.TAG, "getCategories from local");
            callback.onSuccess(categories);
        }

    }

    @Override
    public void getCountries(NetworkCallback<List<Country>> callback) {
        List<Country> countries = localSource.readCountries();
        if (countries == null || countries.isEmpty()) {
            Log.i(SplashFragment.TAG, "getCountries from remote");
            Call<CountriesResponse> call = remoteSource.makeNetworkCall(callback).getCountries();
            call.enqueue(new Callback<CountriesResponse>() {
                @Override
                public void onResponse(@NonNull Call<CountriesResponse> call, @NonNull Response<CountriesResponse> response) {
                    localSource.storeCountries(response.body());
                    if (response.body() != null) {
                        callback.onSuccess(response.body().getCountries());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CountriesResponse> call, @NonNull Throwable t) {
                    callback.onFailure(t.getMessage());
                }
            });
        } else {
            Log.i(SplashFragment.TAG, "getCountries from local");
            callback.onSuccess(countries);
        }

    }

    @Override
    public void getIngredients(NetworkCallback<List<Ingredient>> callback) {
        List<Ingredient> ingredients = localSource.readIngredients();
        if (ingredients == null || ingredients.isEmpty()) {
            Log.i(SplashFragment.TAG, "getIngredients from remote");
            Call<IngredientsResponse> call = remoteSource.makeNetworkCall(callback).getIngredients();
            call.enqueue(new Callback<IngredientsResponse>() {
                @Override
                public void onResponse(@NonNull Call<IngredientsResponse> call, @NonNull Response<IngredientsResponse> response) {
                    localSource.storeIngredients(response.body());
                    if (response.body() != null) {
                        callback.onSuccess(response.body().getIngredients());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<IngredientsResponse> call, @NonNull Throwable t) {
                    callback.onFailure(t.getMessage());
                }
            });
        } else {
            Log.i(SplashFragment.TAG, "getIngredients from local");
            callback.onSuccess(ingredients);
        }

    }

    @Override
    public void getCategoryMeals(String category, NetworkCallback<List<Meal>> callback) {
        Call<MealsResponse> call = remoteSource.makeNetworkCall(callback).getCategoryMeals(category);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                if (response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealsResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getCountryMeals(String country, NetworkCallback<List<Meal>> callback) {
        Call<MealsResponse> call = remoteSource.makeNetworkCall(callback).getCountryMeals(country);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                if (response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealsResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getSearchResult(String word, NetworkCallback<List<Meal>> callback) {
        Call<MealsResponse> call = remoteSource.makeNetworkCall(callback).getSearchResult(word);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                if (response.body() != null) {
                    callback.onSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealsResponse> call, @NonNull Throwable t) {
                callback.onSuccess(new ArrayList<>());
                callback.onFailure(t.getMessage());
            }
        });
    }


    //=================local functions=================
    @Override
    public boolean isLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public LiveData<List<Meal>> getLocalMeals() {
        return localSource.getMeals();
    }

    @Override
    public void insertMeal(Meal meal) {
        localSource.insertMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        localSource.deleteMeal(meal);
    }
}
