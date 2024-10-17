package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface BankService extends Remote {
    void deposit(String account, double amount) throws RemoteException;
    void withdraw(String account, double amount) throws RemoteException;
    double getBalance(String account) throws RemoteException;
    Map<String, Double> getAccounts() throws RemoteException;
    void transfer(String fromAccount, String toAccount, double amount) throws RemoteException;
}