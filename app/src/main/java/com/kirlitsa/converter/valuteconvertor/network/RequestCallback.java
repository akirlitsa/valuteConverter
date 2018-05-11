package com.kirlitsa.converter.valuteconvertor.network;

public interface RequestCallback {

    void onSuccess(String data);

    void onError();
}
