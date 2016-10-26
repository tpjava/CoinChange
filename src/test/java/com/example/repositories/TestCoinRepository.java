package com.example.repositories;

import com.example.TestBase;
import com.example.model.impl.CoinImpl;
import com.example.model.impl.Denomination;
import com.example.properties.PropertyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertTrue;
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
public class TestCoinRepository extends TestBase {
    @Autowired
    private CoinRepository coinRepository;

    @Before
    public void setUp() {
        super.setUp();
        coinRepository = new CoinRepository(new PropertyManager());
    }

    @Test
    public void testCoinRepositoryMock() {
        PropertyManager mock = mock(PropertyManager.class);
        CoinRepository coinRepository = new CoinRepository(mock);
        when(mock.getProperty(anyInt())).thenReturn(1);
        assertEquals(coinRepository.findCoinCountByDenomination(1), 1);
    }

    @Test
    public void testCoinRepositoryWithTestPropertyFile() {
        assertEquals(coinRepository.findCoinCountByDenomination(1), 2);
    }

    @Test
    public void testCoinRepositoryRemoveOnePenceCoinFromPropertiesFile() {
        assertEquals(coinRepository.findCoinCountByDenomination(1), 2);
        assertTrue(coinRepository.removeCoin(new CoinImpl(new Denomination(1, "One Penny"))));
        assertEquals(coinRepository.findCoinCountByDenomination(1), 1);
    }

    @Test
    public void testRepositoryStoresLatestValuesBetweenPropertyManagerInstantiation() {
        assertEquals(coinRepository.findCoinCountByDenomination(2), 3);
        assertTrue(coinRepository.removeCoin(new CoinImpl(new Denomination(2, "Two Penny")))); //should now store 2=2 in properties file
        coinRepository = new CoinRepository(new PropertyManager()); //new properties manager should pick up new properties values
        assertEquals(coinRepository.findCoinCountByDenomination(2), 2);
    }
}
