package com.kirlitsa.converter.valuteconvertor.presenters;

import android.util.Log;
import android.widget.AdapterView;

import com.kirlitsa.converter.valuteconvertor.R;
import com.kirlitsa.converter.valuteconvertor.data.CurrencyEntry;
import com.kirlitsa.converter.valuteconvertor.data.DataManager;
import com.kirlitsa.converter.valuteconvertor.models.CurrencyModel;
import com.kirlitsa.converter.valuteconvertor.network.RequestCallback;
import com.kirlitsa.converter.valuteconvertor.views.MainView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements BasePresenter {

    private CurrencyModel mModel;
    private WeakReference<MainView> mView;
    private DataManager mDataManager;

    public MainPresenter(MainView view, DataManager dataManager) {
        mModel = new CurrencyModel();
        mView = new WeakReference<>(view);
        mDataManager = dataManager;
        mDataManager.loadDataFromCache(mModel);

        mDataManager.loadDataFromNetwork(mModel, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                setCurrenciesList();
            }

            @Override
            public void onError() {

            }
        });

        setCurrenciesList();
    }


    @Override
    public void onConvertButtonPressed(String amount, int fromIndex, int toIndex) {
        try {
            double toAmount = Double.parseDouble(amount);
            if (fromIndex != AdapterView.INVALID_POSITION && toIndex != AdapterView.INVALID_POSITION) {
                if (fromIndex == toIndex) {
                    mView.get().showResult(String.format ("%.4f", toAmount));
                } else {
                    CurrencyEntry fromEntry = mModel.getCurrencyItem(fromIndex);
                    CurrencyEntry toEntry = mModel.getCurrencyItem(toIndex);
                    double fromRate = fromEntry.getValue() / fromEntry.getNominal();
                    double toRate = toEntry.getValue() / toEntry.getNominal();
                    double result = toAmount * fromRate / toRate;
                    mView.get().showResult(String.format ("%.4f", result));
                }

            } else {
                mView.get().showErrorMessage(R.string.error_empty_currency);
            }
        } catch (NumberFormatException exception) {
            mView.get().showErrorMessage(R.string.error_wrong_amount);
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    private void setCurrenciesList() {
        List<CurrencyEntry> currenciesList = mModel.getCurrencyList();
        List<String> namesList = new ArrayList<>(currenciesList.size());
        for (CurrencyEntry entry : currenciesList) {
            namesList.add(entry.getName());
        }
        mView.get().setCurrenciesList(namesList);
        Log.e("MainPresenter", "setCurList");
    }

    public void setModel(CurrencyModel model) {
        mModel = model;
    }
}
