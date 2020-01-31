package com.geektech.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.geektech.news.currency.Example;
import com.geektech.news.currency.Rates;
import com.geektech.news.data.RetrofitBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Object[] keys;
    private ArrayList<String> values;
    private Spinner spinnerOne, spinnerTwo;
    private EditText etCurrent;
    private TextView tvResult;
    private double one, two;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        fetchCurrencies();
        setupListener();
    }

    private void setupViews() {
        spinnerOne = findViewById(R.id.spinnerOne);
        spinnerTwo = findViewById(R.id.spinnerTwo);
        etCurrent = findViewById(R.id.etCurrent);
        tvResult = findViewById(R.id.tvResult);

        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                one = Double.parseDouble(values.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                two = Double.parseDouble(values.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void setupListener() {
        etCurrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double sum = (Double.parseDouble(String.valueOf(s)) / one) * two;
                    tvResult.setText(String.valueOf(sum));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_LONG).show();
                }


            }

        });
    }

    private void fetchCurrencies() {
        RetrofitBuilder
                .getService()
                .getCurrencies("1fc2a9bd0a85d3520261791025761f74")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            parseJson(response.body());

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void parseJson(JsonObject body) {
        JsonObject rates = body.getAsJsonObject("rates");
        keys = rates.keySet().toArray();
        values = new ArrayList<>();

        for (Object item : keys) {
            values.add(rates.getAsJsonPrimitive(item.toString()).toString());

        }
        setupAdapter();
    }

    private void setupAdapter() {
        ArrayAdapter arrayAdapter = new ArrayAdapter<Object>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                keys);

        spinnerOne.setAdapter(arrayAdapter);
        spinnerTwo.setAdapter(arrayAdapter);
    }

}
