package com.kirlitsa.converter.valuteconvertor.presenters;

public interface BasePresenter {

    void onConvertButtonPressed(String amount, int fromCurrencyIndex, int toCurrencyIndex);

    void onResume();

    void onDestroy();
}
