package com.kirlitsa.converter.valuteconvertor.network;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestTask extends AsyncTask<URL, Void, String> {

    private RequestCallback mListener;

    public RequestTask(RequestCallback listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(URL... params) {
        URL url = params[0];
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream input = connection.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(input, "windows-1251"));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
                return total.toString().replace(',', '.');
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null && !s.isEmpty()) {
            mListener.onSuccess(s);
        } else {
            mListener.onError();
        }
    }
}
