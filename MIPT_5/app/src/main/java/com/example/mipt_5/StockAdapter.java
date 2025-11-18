package com.example.mipt_5;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class StockAdapter extends ArrayAdapter<Stock> {

    public StockAdapter(Context context, ArrayList<Stock> stocks) {
        super(context, 0, stocks);
        System.out.println("StockAdapter constructor called");
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        System.out.println("StockAdapter.getView called");

        Stock stock = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        assert stock != null;
        text1.setText(stock.getTicker() + " — " + stock.getLastPrice());
        text2.setText(stock.getName() + " (" + stock.getChangePercent() + "%)");

        return convertView;
    }
}