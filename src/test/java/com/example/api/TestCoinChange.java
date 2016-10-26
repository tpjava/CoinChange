package com.example.api;

import com.example.TestBase;
import com.example.exceptions.InsufficientFundsException;
import com.example.model.Coin;
import com.example.model.impl.CoinImpl;
import com.example.model.impl.Denomination;
import org.junit.Before;
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
 * Created by tonyperrin.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class TestCoinChange extends TestBase {
    private CoinChange coinChange;

    @Before
    public void setUp() {
        super.setUp();
        coinChange = new CoinChange();
    }

    @Test
    public void testApiMock() {
        Collection<Coin> coins = new ArrayList<Coin>();
        coins.add(new CoinImpl(new Denomination(1, "One Penny")));
        CoinChange mock = mock(CoinChange.class);
        when(mock.getOptimalChangeFor(anyInt())).thenReturn(coins);
        assertEquals(mock.getOptimalChangeFor(1).stream().count(), 1);
    }

    @Test
    public void testApiGetOptimalChangeFor() {
        Collection<Coin> coins = coinChange.getOptimalChangeFor(1);
        assertEquals(coins.stream().count(), 1);
    }

    @Test
    public void testApiGetChangeFor() throws InsufficientFundsException {
        Collection<Coin> coins = coinChange.getChangeFor(4);
        assertEquals(coins.stream().count(), 2);
    }

    @Test(expected = InsufficientFundsException.class)
    public void testApiGetChangeForNotEnoughChange() throws InsufficientFundsException {
        coinChange.getChangeFor(4);
        coinChange.getChangeFor(4); //should only be one 2 pence coin left.
    }

    //100=2
    //50=3
    @Test(expected = InsufficientFundsException.class)
    public void testApiGetChangeForOnePoundFifty() throws InsufficientFundsException {
        Collection<Coin> coins = coinChange.getChangeFor(150);
        assertEquals(coins.stream().count(), 2);
        coins = coinChange.getChangeFor(150);
        assertEquals(coins.stream().count(), 2);
        coins = coinChange.getChangeFor(150);
    }

    //50=3
    //20=3
    //10=4
    @Test(expected = InsufficientFundsException.class)
    public void testApiGetChangeForEightyPence() throws InsufficientFundsException {
        Collection<Coin> coins = coinChange.getChangeFor(80);
        //50=2
        //20=2
        //10=3
        assertEquals(coins.stream().count(), 3);
        coins = coinChange.getChangeFor(80);
        //50=1
        //20=1
        //10=2
        assertEquals(coins.stream().count(), 3);
        coins = coinChange.getChangeFor(80);
        //50=0
        //20=0
        //10=1
        assertEquals(coins.stream().count(), 3);
        coins = coinChange.getChangeFor(80);
        //this will fail
        assertEquals(coins.stream().count(), 3);
    }

    //10=4
    //5=5
    //2=3
    //1=2
    @Test(expected = InsufficientFundsException.class)
    public void testApiGetChangeForSeventeenPence() throws InsufficientFundsException {
        Collection<Coin> coins = coinChange.getChangeFor(17);
        //10=3
        //5=4
        //2=2
        //1=2
        assertEquals(coins.stream().count(), 3);
        coins = coinChange.getChangeFor(17);
        //10=2
        //5=3
        //2=1
        //1=2
        assertEquals(coins.stream().count(), 3);
        coins = coinChange.getChangeFor(17);
        //10=1
        //5=2
        //2=0
        //1=1
        assertEquals(coins.stream().count(), 3);
        coins = coinChange.getChangeFor(17);
        //this will fail
        assertEquals(coins.stream().count(), 3);
    }

}
