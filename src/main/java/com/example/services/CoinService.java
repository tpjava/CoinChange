package com.example.services;

import com.example.exceptions.InsufficientCoinageException;
import com.example.model.Coin;
import com.example.model.impl.*;
import com.example.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by tonyperrin.
 */
@Service
public class CoinService {
    private static final List<Coin> coins = Arrays.asList(
            new CoinImpl(new Denomination(100, "One Pound")),
            new CoinImpl(new Denomination(50, "Fifty Pence")),
            new CoinImpl(new Denomination(20, "Twenty Pence")),
            new CoinImpl(new Denomination(10, "Ten Pence")),
            new CoinImpl(new Denomination(5, "Five Pence")),
            new CoinImpl(new Denomination(2, "Two Pence")),
            new CoinImpl(new Denomination(1, "One Pence")));

    private CoinRepository coinRepository;

    @Autowired
    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public Collection<Coin> getOptimalChangeFor(int amount) {
        if(amount < 0) {
            return new ArrayList<>();
        }
        List<Coin> results = new ArrayList<>();

        for(Coin coin : coins) { //loop through coins finding highest value coin who's denomination fits the remaining value.
            int count = amount / coin.getDenomination();

            if(count > 0) { //valid denomination for coin.
                for(int idx = 0 ; idx < count ; idx++) {
                    results.add(coin);
                    amount-= coin.getDenomination();
                }
            }
            if(amount == 0) {
                break; //no more change to give out.
            }
        }
        return results; //return collection of coins.
    }

    public Collection<Coin> getChangeFor(int amount) {
        if(amount < 0) {
            return new ArrayList<Coin>();
        }
        List<Coin> results = new ArrayList<>();

        for(Coin coin : coins) {
            int count = amount / coin.getDenomination();

            if(count > 0) { //valid denomination for coin.
                for(int idx = 0 ; idx < count ; idx++) {
                    if(coinRepository.removeCoin(coin)) {
                        results.add(coin);
                        amount -= coin.getDenomination();
                    } else {
                        throw new InsufficientCoinageException("There were not enough " + coin + "to make up the required change.");
                    }
                }
            }
            if(amount == 0) {
                break; //no more change to give out.
            }
        }
        return results; //return collection of coins.
    }
}
