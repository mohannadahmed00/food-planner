package com.giraffe.foodplannerapplication.network;

public interface RemoteSource {
    <T> ApiServices makeNetworkCall(NetworkCallback<T> callback);//optional till now just for testing
}
