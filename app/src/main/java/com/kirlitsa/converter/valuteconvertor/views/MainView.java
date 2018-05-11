package com.kirlitsa.converter.valuteconvertor.views;

import com.kirlitsa.converter.valuteconvertor.data.CurrencyEntry;

import java.util.List;

public interface MainView {

    void setCurrenciesList(List<String> currencies);

    void showErrorMessage(int messageResourceId);

    void showResult(String result);

}
