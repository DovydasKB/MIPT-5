package com.example.mipt_5;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DataLoaderTest {

    @Mock
    private DataLoader.DataLoadedListener mockListener;

    @Captor
    private ArgumentCaptor<ArrayList<Stock>> captor;

    @Test
    public void onPostExecute_callsOnDataLoaded() {
        DataLoader dataLoader = new DataLoader(mockListener);
        ArrayList<Stock> testData = new ArrayList<>();
        testData.add(new Stock("TICKER", "Name", 10.0, 0.5));

        dataLoader.onPostExecute(testData);

        verify(mockListener).onDataLoaded(captor.capture());
        assertEquals(testData, captor.getValue());
    }
}
