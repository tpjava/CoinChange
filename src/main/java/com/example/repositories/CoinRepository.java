package com.example.repositories;

import com.example.model.Coin;
import com.example.properties.PropertyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * Created by tonyperrin.
 */
@Repository
@Configuration
@Component
public class CoinRepository {
    private final PropertyManager propertyManager;

    @Autowired
    public CoinRepository(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;

    }

    public int findCoinCountByDenomination(int denomination) {
        return propertyManager.getProperty(denomination);
    }

    public boolean removeCoin(Coin coin) {
        if(propertyManager.getProperty(coin.getDenomination()) <= 0) {
            return false;
        }
        int count = propertyManager.getProperty(coin.getDenomination());
        propertyManager.setProperty(coin.getDenomination(), --count);
        return true;
    }
}
