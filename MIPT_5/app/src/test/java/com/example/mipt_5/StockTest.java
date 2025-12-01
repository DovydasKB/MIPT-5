package com.example.mipt_5;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StockTest {

    @Test
    public void stock_getters_returnCorrectValues() {
        Stock stock = new Stock("TICKER", "Name", 10.0, 0.5);

        assertEquals("TICKER", stock.getTicker());
        assertEquals("Name", stock.getName());
        assertEquals(10.0, stock.getLastPrice(), 0.001);
        assertEquals(0.5, stock.getChangePercent(), 0.001);
    }

    @Test
    public void stock_equalsAndHashCode_workCorrectly() {
        Stock stock1 = new Stock("TICKER", "Name", 10.0, 0.5);
        Stock stock2 = new Stock("TICKER", "Name", 10.0, 0.5);
        Stock stock3 = new Stock("OTHER", "Other Name", 20.0, 1.0);


        assertEquals(stock1, stock2);
        assertNotEquals(stock1, stock3);


        assertEquals(stock1.hashCode(), stock2.hashCode());
        assertNotEquals(stock1.hashCode(), stock3.hashCode());
    }
}
