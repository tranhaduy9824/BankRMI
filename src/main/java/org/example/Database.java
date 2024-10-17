package org.example;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static final Map<String, Double> accounts = new HashMap<>();

    static {
        accounts.put("Hà Duy", 1000.0);
        accounts.put("Đức Đoan", 2000.0);
        accounts.put("Hà Duy 2", 3000.0);
        accounts.put("Đức Đoan 2", 4000.0);
    }

    public static synchronized void deposit(String account, double amount) {
        accounts.put(account, accounts.getOrDefault(account, 0.0) + amount);
    }

    public static synchronized void withdraw(String account, double amount) {
        if (accounts.getOrDefault(account, 0.0) >= amount) {
            accounts.put(account, accounts.get(account) - amount);
        } else {
            throw new IllegalArgumentException("Insufficient funds.");
        }
    }

    public static synchronized double getBalance(String account) {
        return accounts.getOrDefault(account, 0.0);
    }

    public static synchronized Map<String, Double> getAccounts() {
        return new HashMap<>(accounts);
    }
}