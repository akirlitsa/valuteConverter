package com.kirlitsa.converter.valuteconvertor.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.kirlitsa.converter.valuteconvertor.models.CurrencyModel;
import com.kirlitsa.converter.valuteconvertor.network.RequestCallback;
import com.kirlitsa.converter.valuteconvertor.network.RequestTask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

public class DataManager {

    private static final String SHARED_PREFERENCES = "currencies_cache";
    private static final String KEY = "currencies";

    private SharedPreferences mPrefs;

    public DataManager(Context context) {
        mPrefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveData(CurrencyModel model) {
        try {
            StringWriter writer = new StringWriter();
            Serializer serializer = new Persister();
            serializer.write(model, writer);
            String stringToSave = writer.getBuffer().toString();
            synchronized (mPrefs) {
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString(KEY, stringToSave).apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDataFromCache(CurrencyModel model) {
        String stringToParse = "";
        synchronized (mPrefs) {
            stringToParse = mPrefs.getString(KEY, "");
        }
        Serializer serializer = new Persister();
        try {
            serializer.read(model, stringToParse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDataFromNetwork(final CurrencyModel model, final RequestCallback callback) {
        RequestTask request = new RequestTask(new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                model.updateCurrencyList(data);
                saveData(model);
                callback.onSuccess(data);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
        try {
            request.execute(new URL("http://www.cbr.ru/scripts/XML_daily.asp"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
