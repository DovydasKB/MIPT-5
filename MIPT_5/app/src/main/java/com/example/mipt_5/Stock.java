package com.example.mipt_5;

import java.util.Objects;

public class Stock {

    private final String ticker;
    private final String name;
    private final double lastPrice;
    private final double changePercent;

    public Stock(String ticker, String name, double lastPrice, double changePercent) {
        System.out.println("Stock constructor called");
        this.ticker = ticker;
        this.name = name;
        this.lastPrice = lastPrice;
        this.changePercent = changePercent;
    }

    public String getTicker() { return ticker; }
    public String getName() { return name; }

    public double getLastPrice() { return lastPrice; }
    public double getChangePercent() { return changePercent; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Double.compare(stock.lastPrice, lastPrice) == 0 &&
                Double.compare(stock.changePercent, changePercent) == 0 &&
                Objects.equals(ticker, stock.ticker) &&
                Objects.equals(name, stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, name, lastPrice, changePercent);
    }
}
