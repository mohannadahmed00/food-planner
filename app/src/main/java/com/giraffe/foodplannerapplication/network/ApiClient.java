package com.giraffe.foodplannerapplication.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient implements RemoteSource {
    private static final String URL = "https://www.themealdb.com/api/json/v1/1/";
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

    @Override
    public <T> ApiServices makeNetworkCall(NetworkCallback<T> networkCallback) {
        //Gson gson =
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL).build();
        return retrofit.create(ApiServices.class);
    }
}
