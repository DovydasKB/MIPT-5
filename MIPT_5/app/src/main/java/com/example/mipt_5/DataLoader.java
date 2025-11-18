package com.example.mipt_5;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataLoader extends AsyncTask<Void, Void, ArrayList<Stock>> {

    public interface DataLoadedListener {
        void onDataLoaded(ArrayList<Stock> stocks);
    }

    private final DataLoadedListener listener;

    public DataLoader(DataLoadedListener listener) {
        System.out.println("DataLoader constructor called");
        this.listener = listener;
    }

    @Override
    protected ArrayList<Stock> doInBackground(Void... voids) {
        System.out.println("DataLoader.doInBackground called");

        ArrayList<Stock> list = new ArrayList<>();

        try {
            String downloadUrl = "https://nasdaqbaltic.com/statistics/lt/shares?download=1";
            URL url = new URL(downloadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream stream = conn.getInputStream();
            list = ParserXlsx.parseExcel(stream);

            stream.close();
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("DataLoader error: " + e.getMessage());
        }

        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<Stock> stocks) {
        System.out.println("DataLoader.onPostExecute called");

        if (listener != null) {
            listener.onDataLoaded(stocks);
        }
    }
}