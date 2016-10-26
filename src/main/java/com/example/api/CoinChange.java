package com.example.api;

import com.example.model.Coin;
import com.example.services.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by tonyperrin on 26/10/2016.
 */
@Component
public class CoinChange {
    @Autowired
    private CoinService coinService;


    public Collection<Coin> getOptimalChangeFor(int amount) {
        return coinService.getOptimalChangeFor(amount);
    }
}
