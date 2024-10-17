package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

public class BankApp {
    private JFrame frame;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton balanceButton;
    private JButton transferButton;
    private JTextField accountField;
    private JTextField amountField;
    private JTextField toAccountField;
    private JLabel balanceLabel;
    private JComboBox<String> serverSelector;

    public BankApp() {
        frame = new JFrame("Bank Application");

        // Tạo các nút
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        balanceButton = new JButton("Check Balance");
        transferButton = new JButton("Transfer");

        // Tạo các trường nhập liệu
        accountField = new JTextField(10);
        amountField = new JTextField(10);
        toAccountField = new JTextField(10);
        balanceLabel = new JLabel("Balance: ");

        // Tạo danh sách chọn server
        serverSelector = new JComboBox<>(new String[]{"BankService1", "BankService2", "BankService3"});

        // Đăng ký sự kiện
        depositButton.addActionListener(e -> performTransaction("deposit"));
        withdrawButton.addActionListener(e -> performTransaction("withdraw"));
        balanceButton.addActionListener(e -> checkBalance());
        transferButton.addActionListener(e -> performTransaction("transfer"));

        // Thiết lập layout
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Thêm các thành phần vào frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Server:"), gbc);
        gbc.gridx = 1;
        frame.add(serverSelector, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Account:"), gbc);
        gbc.gridx = 1;
        frame.add(accountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        frame.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("To Account:"), gbc);
        gbc.gridx = 1;
        frame.add(toAccountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(depositButton, gbc);
        gbc.gridx = 1;
        frame.add(withdrawButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(balanceButton, gbc);
        gbc.gridx = 1;
        frame.add(transferButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        frame.add(balanceLabel, gbc);

        // Thiết lập giao diện
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        depositButton.setBackground(Color.GREEN);
        withdrawButton.setBackground(Color.RED);
        balanceButton.setBackground(Color.BLUE);
        transferButton.setBackground(Color.ORANGE);

        depositButton.setForeground(Color.WHITE);
        withdrawButton.setForeground(Color.WHITE);
        balanceButton.setForeground(Color.WHITE);
        transferButton.setForeground(Color.WHITE);

        // Kích thước frame
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void performTransaction(String type) {
        try {
            String selectedServer = (String) serverSelector.getSelectedItem();
            int port = 1099;
            if ("BankService2".equals(selectedServer)) {
                port = 1100;
            } else if ("BankService3".equals(selectedServer)) {
                port = 1101;
            }
            BankService bankService = (BankService) Naming.lookup("rmi://localhost:" + port + "/" + selectedServer);
            String account = accountField.getText();
            double amount = Double.parseDouble(amountField.getText());
            if (type.equals("deposit")) {
                bankService.deposit(account, amount);
            } else if (type.equals("withdraw")) {
                bankService.withdraw(account, amount);
            } else if (type.equals("transfer")) {
                String toAccount = toAccountField.getText();
                bankService.transfer(account, toAccount, amount);
            }
            checkBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkBalance() {
        try {
            String selectedServer = (String) serverSelector.getSelectedItem();
            int port = 1099;
            if ("BankService2".equals(selectedServer)) {
                port = 1100;
            } else if ("BankService3".equals(selectedServer)) {
                port = 1101;
            }
            BankService bankService = (BankService) Naming.lookup("rmi://localhost:" + port + "/" + selectedServer);
            String account = accountField.getText();
            double balance = bankService.getBalance(account);
            balanceLabel.setText("Balance: " + balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BankApp();
    }
}