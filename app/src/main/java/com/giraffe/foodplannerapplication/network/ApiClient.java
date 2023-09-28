package com.giraffe.foodplannerapplication.network;


import android.util.Log;

import androidx.annotation.NonNull;

import com.giraffe.foodplannerapplication.models.CategoriesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient /*implements RemoteSource*/ {
    private static final String URL = "www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "ApiClient";
    private static ApiClient client = null;

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        if (client == null) {
            client = new ApiClient();
        }
        return client;
    }

    //@Override
    public void makeNetworkCall(NetworkCallback networkCallback) {
        //Gson gson =
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL).build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        //the end
        /*Call<ProductsResponse> call = apiServices.getProducts();
        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductsResponse> call, @NonNull Response<ProductsResponse> response) {
                Log.i(TAG, "onResponse");
                networkCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProductsResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure");
                networkCallback.onFailure(t.getMessage());
            }
        });*/
    }
}
