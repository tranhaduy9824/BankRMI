package org.example;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import javax.swing.*;
import java.awt.*;

public class BankServer {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bank Servers");
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Runnable server1 = () -> startServer(1099, "BankService1", textArea);
        Runnable server2 = () -> startServer(1100, "BankService2", textArea);
        Runnable server3 = () -> startServer(1101, "BankService3", textArea);

        new Thread(server1).start();
        new Thread(server2).start();
        new Thread(server3).start();
    }

    private static void startServer(int port, String serviceName, JTextArea textArea) {
        try {
            LocateRegistry.createRegistry(port);
            BankService bankService = new BankServiceImpl(textArea, serviceName);
            String hostAddress = java.net.InetAddress.getLocalHost().getHostAddress(); // Lấy địa chỉ IP của máy chủ
            Naming.rebind("rmi://" + hostAddress + ":" + port + "/" + serviceName, bankService);

            // Cập nhật thông báo với địa chỉ IP
            SwingUtilities.invokeLater(() -> textArea.append("Bank RMI Server " + serviceName + " is running on port " + port + " at IP address " + hostAddress + "...\n"));
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> textArea.append("Error starting server " + serviceName + ": " + e.getMessage() + "\n"));
            e.printStackTrace();
        }
    }
}