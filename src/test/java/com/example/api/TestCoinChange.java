package com.example.api;

import com.example.model.Coin;
import com.example.model.impl.CoinImpl;
import com.example.model.impl.Denomination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by tonyperrin on 26/10/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class TestCoinChange {
    @Autowired
    private CoinChange coinChange;

    @Test
    public void testApiMock() {
        Collection<Coin> coins = new ArrayList<Coin>();
        coins.add(new CoinImpl(new Denomination(1, "One Penny")));
        CoinChange mock = mock(CoinChange.class);
        when(mock.getOptimalChangeFor(anyInt())).thenReturn(coins);
        assertEquals(mock.getOptimalChangeFor(1).stream().count(), 1);
    }

    @Test
    public void testApi() {
        Collection<Coin> coins = coinChange.getOptimalChangeFor(1);
        assertEquals(coins.stream().count(), 1);
    }
}
