package com.example.mipt_5;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText txtFilter;
    private StockAdapter adapter;

    private final ArrayList<Stock> fullList = new ArrayList<>();

    private final Handler handler = new Handler();
    private Runnable refreshTask;

    private static final int REFRESH_INTERVAL = 30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("MainActivity.onCreate called");

        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listViewStocks);
        txtFilter = findViewById(R.id.txtFilter);

        adapter = new StockAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        txtFilter.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { filterList(); }
            @Override public void afterTextChanged(android.text.Editable s) {}
        });

        loadData();
        startAutoRefresh();
    }

    private boolean isMarketOpen() {
        System.out.println("isMarketOpen called");

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY)
            return false;

        return hour >= 10 && hour < 16;
    }

    private void startAutoRefresh() {
        System.out.println("startAutoRefresh called");

        refreshTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("Auto-refresh triggered");

                if (isMarketOpen()) {
                    loadData();
                } else {
                    System.out.println("Market closed — skipping refresh");
                }

                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        };

        handler.postDelayed(refreshTask, 2000);
    }

    private void loadData() {
        System.out.println("loadData called");

        new DataLoader(stocks -> {
            System.out.println("loadData callback received");

            if (!stocks.equals(fullList)) {
                System.out.println("Data changed — updating UI");

                fullList.clear();
                fullList.addAll(stocks);

                filterList();
            } else {
                System.out.println("No data change — UI not updated");
            }
        }).execute();
    }

    private void filterList() {
        System.out.println("filterList called");

        String filter = txtFilter.getText().toString().toUpperCase();

        ArrayList<Stock> filtered = new ArrayList<>();

        for (Stock s : fullList) {
            if (s.getTicker().toUpperCase().contains(filter)) {
                filtered.add(s);
            }
        }

        adapter.clear();
        adapter.addAll(filtered);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        System.out.println("MainActivity.onDestroy called");
        handler.removeCallbacks(refreshTask);
        super.onDestroy();
    }
}