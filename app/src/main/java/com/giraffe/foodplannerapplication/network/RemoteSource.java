package com.giraffe.foodplannerapplication.network;

public interface RemoteSource {
    <T> ApiServices makeNetworkCall(NetworkCallback<T> callback);//optional till now just for testing
    <T> ApiServices callRequest();//optional till now just for testing
}
