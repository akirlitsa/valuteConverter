package com.kirlitsa.converter.valuteconvertor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.kirlitsa.converter.valuteconvertor.data.CurrencyEntry;
import com.kirlitsa.converter.valuteconvertor.data.DataManager;
import com.kirlitsa.converter.valuteconvertor.network.RequestTask;
import com.kirlitsa.converter.valuteconvertor.presenters.MainPresenter;
import com.kirlitsa.converter.valuteconvertor.views.MainView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private EditText mAmountView;
    private TextView mResultView;
    private Spinner mFromCurrencySpinner;
    private Spinner mToCurrencySpinner;
    private Button mConvertButton;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAmountView = findViewById(R.id.amount_view);
        mResultView = findViewById(R.id.result_view);
        mFromCurrencySpinner = findViewById(R.id.from_currency_spinner);
        mToCurrencySpinner = findViewById(R.id.to_currency_spinner);
        mConvertButton = findViewById(R.id.convert_button);

        mConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = mAmountView.getEditableText().toString();
                int fromIndex = mFromCurrencySpinner.getSelectedItemPosition();
                int toIndex = mToCurrencySpinner.getSelectedItemPosition();
                mPresenter.onConvertButtonPressed(amount, fromIndex, toIndex);
            }
        });

        mPresenter = new MainPresenter(this, new DataManager(this));

    }

    @Override
    public void setCurrenciesList(List<String> currencies) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mFromCurrencySpinner.setAdapter(adapter);
        mToCurrencySpinner.setAdapter(adapter);
    }

    @Override
    public void showErrorMessage(int messageResourceId) {

    }

    @Override
    public void showResult(String result) {
        mResultView.setText(result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
