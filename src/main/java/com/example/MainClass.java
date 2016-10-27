package com.example;

import com.example.api.CoinChange;
import com.example.exceptions.InsufficientFundsException;
import com.example.model.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;
import java.util.Scanner;

//TODO - package jar with class files and test


@SpringBootApplication
public class MainClass {
    @Autowired
    private CoinChange coinChange;

    public static void main( String[] args )
    {
        SpringApplication.run(MainClass.class);
        runConsole();
    }

    private static void runConsole() {
        while(true) {
            System.out.println("\r\nEnter an amount to return coin change.  Type 'quit' to quit.");
            Scanner scanner = new Scanner(System.in);

            String input = scanner.next();

            boolean number = input.matches("^\\d{1,10}$");

            if(number) {
                int amount = Integer.parseInt(input);
                CoinChange coinChange = new CoinChange();
                try {
                    Collection<Coin> coins = coinChange.getChangeFor(amount);
                    printCoins(coins);
                } catch (InsufficientFundsException e) {
                    System.out.println(e.getMessage());
                }

            } else if(input.equalsIgnoreCase("QUIT")) {
                System.out.println("Quit.  Bye bye.");
                System.exit(0);
            } else {
                System.out.println("You cannot use value " + input + " as your change amount.  Please try again with a whole number.");
            }
        }
    }

    private static void printCoins(Collection<Coin> coins) {
        System.out.println("\r\nReturned coins:");
        for(Coin coin : coins) {
            System.out.println(coin.toString());
        }
    }
}
