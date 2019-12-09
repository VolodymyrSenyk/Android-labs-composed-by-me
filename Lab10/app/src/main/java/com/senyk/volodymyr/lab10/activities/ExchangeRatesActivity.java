package com.senyk.volodymyr.lab10.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.senyk.volodymyr.lab10.R;
import com.senyk.volodymyr.lab10.adapters.ExchangeRatesAdapter;
import com.senyk.volodymyr.lab10.models.pojo.CurrencyPojo;
import com.senyk.volodymyr.lab10.repository.PrivatbankApiRepository;

import java.util.List;

public class ExchangeRatesActivity extends AppCompatActivity {
    private RecyclerView list;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView contactsList = findViewById(R.id.exchange_rates_list);
        contactsList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        contactsList.setLayoutManager(layoutManager);
        contactsList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        this.list = contactsList;

        this.progressBar = findViewById(R.id.progress_bar);

        new RetrieveFeedTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class RetrieveFeedTask extends AsyncTask<Void, Void, List<CurrencyPojo>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected List<CurrencyPojo> doInBackground(Void... voids) {
            return PrivatbankApiRepository.getInstance().getExchangeRates();
        }

        protected void onPostExecute(List<CurrencyPojo> feed) {
            list.setAdapter(new ExchangeRatesAdapter(feed));
            progressBar.setVisibility(View.GONE);
        }
    }
}
