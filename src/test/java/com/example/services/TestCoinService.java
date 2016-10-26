package com.example.services;
import com.example.TestBase;
import com.example.exceptions.InsufficientFundsException;
import com.example.model.Coin;
import com.example.model.impl.CoinImpl;
import com.example.model.impl.Denomination;
import com.example.properties.PropertyManager;
import com.example.repositories.CoinRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by tonyperrin on 20/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class TestCoinService extends TestBase {
    @Autowired
    private CoinService coinService;

    @Test
    public void testCoinServiceMock() {
        CoinService mock = mock(CoinService.class);
        when(mock.getOptimalChangeFor(anyInt())).thenReturn(anyObject());
    }

    @Test
    public void testCoinServiceOptimalChange() {
        assertEquals(coinService.getOptimalChangeFor(50).stream().count(), 1);
        assertEquals(coinService.getOptimalChangeFor(50).stream().filter(c -> c.getDenomination() == 50).findFirst().get().getDenomination(), 50);
    }

    @Test
    public void testCoinServiceZeroPenceOptimalChange() {
        assertEquals(coinService.getOptimalChangeFor(0).stream().count(), 0);
    }

    @Test
    public void testCoinServiceFourPenceOptimalChange() {
        assertEquals(coinService.getOptimalChangeFor(4).stream().count(), 2);
        assertEquals(coinService.getOptimalChangeFor(4).stream().filter(c -> c.getDenomination() == 2).count(), 2);
    }

    @Test
    public void testCoinServiceFiftyFivePenceOptimalChange() {
        assertEquals(coinService.getOptimalChangeFor(55).stream().count(), 2);
        assertEquals(coinService.getOptimalChangeFor(55).stream().filter(c -> c.getDenomination() == 50).count(), 1);
        assertEquals(coinService.getOptimalChangeFor(55).stream().filter(c -> c.getDenomination() == 5).count(), 1);
    }

    @Test
    public void testCoinServiceSeventySevenPenceOptimalChange() {
        assertEquals(coinService.getOptimalChangeFor(77).stream().count(), 4);
        assertEquals(coinService.getOptimalChangeFor(77).stream().filter(c -> c.getDenomination() == 50).count(), 1);
        assertEquals(coinService.getOptimalChangeFor(77).stream().filter(c -> c.getDenomination() == 20).count(), 1);
        assertEquals(coinService.getOptimalChangeFor(77).stream().filter(c -> c.getDenomination() == 5).count(), 1);
        assertEquals(coinService.getOptimalChangeFor(77).stream().filter(c -> c.getDenomination() == 2).count(), 1);

    }

    @Test
    public void testCoinServiceNegativeValueOptimalChange() {
        assertEquals(coinService.getOptimalChangeFor(-5).stream().count(), 0);
    }

    @Test
    public void testCoinServiceChangeForNegativeValue() throws InsufficientFundsException {
        assertEquals(coinService.getChangeFor(-5).stream().count(), 0);
    }

    @Test
    public void testCoinServiceChangeForWithPropertiesOnePenceMock() throws InsufficientFundsException {
        CoinService mock = mock(CoinService.class);
        Collection<Coin> coins = new ArrayList<>();
        coins.add(new CoinImpl(new Denomination(2, "Two Penny")));
        when(mock.getChangeFor(2)).thenReturn(coins);
        coins = mock.getChangeFor(2);
        assertEquals(coins.stream().count(), 1);
        assertEquals(coins.stream().filter(c -> c.getDenomination() == 2).count(), 1);
    }

    @Test(expected = InsufficientFundsException.class)
    public void testCoinServiceChangeForWithPropertiesTwoOnePenceNotOneTwoPence() throws InsufficientFundsException {
        Collection<Coin> coins = coinService.getChangeFor(2);
        assertEquals(coins.stream().filter(c -> c.getDenomination() == 2).count(), 1);
        coinService.getChangeFor(2); //no two pence coins left
    }

    @Test(expected = InsufficientFundsException.class)
    public void testPropertiesFilePersistsLatestValueWhenCoinsReduced() throws InsufficientFundsException {
        //properties coins should be 2=3
        Collection<Coin> coins = coinService.getChangeFor(2);
        assertEquals(coins.stream().filter(c -> c.getDenomination() == 2).count(), 1);
        //properties coins should be 2=2
        coins = coinService.getChangeFor(2);
        assertEquals(coins.stream().filter(c -> c.getDenomination() == 2).count(), 1);
        //properties coins should be 2=1
        coins = coinService.getChangeFor(2);
        assertEquals(coins.stream().filter(c -> c.getDenomination() == 2).count(), 1);
        //properties coins should be 2=0
        coinService = new CoinService(new CoinRepository(new PropertyManager())); //new property manager should pick up updated properties
        coinService.getChangeFor(2); //should throw exception as not enough two pence left
    }

    @Test(expected = InsufficientFundsException.class)
    public void testCoinServiceChangeForInsufficientCoins() throws InsufficientFundsException {
        coinService.getChangeFor(2);
    }
}
