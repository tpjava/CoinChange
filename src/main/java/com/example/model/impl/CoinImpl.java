package com.example.model.impl;

import com.example.model.Coin;

/**
 * Created by tonyperrin.
 */
public class CoinImpl implements Coin {
    private final Denomination denomination;

    public CoinImpl(Denomination denomination) {
        this.denomination = denomination;
    }

    @Override
    public int getDenomination() {
        return denomination.getValue();
    }

    @Override
    public String toString() {
        return String.format("(%d) %s coin", denomination.getValue(), denomination.getName());
    }
}
