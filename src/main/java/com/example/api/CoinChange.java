package com.example.api;

import com.example.exceptions.InsufficientFundsException;
import com.example.model.Coin;
import com.example.properties.PropertyManager;
import com.example.repositories.CoinRepository;
import com.example.services.CoinService;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by tonyperrin.
 */
@Component
public class CoinChange {
    private final CoinService coinService;
    private final CoinRepository coinRepository;
    private final PropertyManager propertyManager;

    public CoinChange() {
        this.propertyManager = new PropertyManager();
        this.coinRepository = new CoinRepository(propertyManager);
        this.coinService = new CoinService(coinRepository);
    }

    /**
     * Calculates the minimum number of Coin objects required to make up the supplied amount.
     * @param amount
     * @return Collection of coins
     */
    public Collection<Coin> getOptimalChangeFor(int amount) {
        return coinService.getOptimalChangeFor(amount);
    }


    /**
     * Calculates the minimum number of Coin objects required to make up the supplied amount when coins are available in properties store.
     * @param amount
     * @return Collection of coins
     * @Throws InsufficientCoinageException if not enough coins left to make up change.
     */
    public Collection<Coin> getChangeFor(int amount) throws InsufficientFundsException {
        return coinService.getChangeFor(amount);
    }
}
