package com.example.api;

import com.example.model.Coin;
import com.example.properties.PropertyManager;
import com.example.repositories.CoinRepository;
import com.example.services.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by tonyperrin on 26/10/2016.
 */
@Component
public class CoinChange {
    private static final CoinService coinService;
    private static final CoinRepository coinRepository;
    private static final PropertyManager propertyManager;

    static {
        propertyManager = new PropertyManager();
        coinRepository = new CoinRepository(propertyManager);
        coinService = new CoinService(coinRepository);
    }

    public Collection<Coin> getOptimalChangeFor(int amount) {
        return coinService.getOptimalChangeFor(amount);
    }
}
