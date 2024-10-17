package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import javax.swing.*;

public class BankServiceImpl extends UnicastRemoteObject implements BankService {
    private JTextArea textArea;
    private String serverName;

    protected BankServiceImpl(JTextArea textArea, String serverName) throws RemoteException {
        super();
        this.textArea = textArea;
        this.serverName = serverName;
    }

    @Override
    public void deposit(String account, double amount) throws RemoteException {
        Database.deposit(account, amount);
        logEvent("Deposited: " + amount + " to " + account);
    }

    @Override
    public void withdraw(String account, double amount) throws RemoteException {
        Database.withdraw(account, amount);
        logEvent("Withdrew: " + amount + " from " + account);
    }

    @Override
    public double getBalance(String account) throws RemoteException {
        return Database.getBalance(account);
    }

    @Override
    public Map<String, Double> getAccounts() throws RemoteException {
        return Database.getAccounts();
    }

    @Override
    public void transfer(String fromAccount, String toAccount, double amount) throws RemoteException {
        Database.withdraw(fromAccount, amount);
        Database.deposit(toAccount, amount);
        logEvent("Transferred: " + amount + " from " + fromAccount + " to " + toAccount);
    }

    private void logEvent(String message) {
        SwingUtilities.invokeLater(() -> textArea.append("[" + serverName + "] " + message + "\n"));
    }
}