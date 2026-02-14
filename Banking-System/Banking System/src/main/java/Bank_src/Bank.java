package main.java.Bank_src;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Bank {
    private String bankName;
    private Map<String, Customer> customers;
    private Map<String, Account> accounts;

    public Bank(String bankName) {
        if (bankName == null || bankName.trim().isEmpty()) {
            throw new IllegalArgumentException("Bank name cannot be empty.");
        }
        this.bankName = bankName;
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
    }

    // Register a new customer
    public void registerCustomer(Customer customer) {
        if (customer == null || customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Customer is null or already exists.");
        }
        customers.put(customer.getId(), customer);
    }

    // Open a new account
    public void openAccount(Account account) {
        if (account == null || accounts.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException("Account is null or already exists.");
        }
        accounts.put(account.getAccountNumber(), account);
    }

    // Get a customer by ID
    public Customer getCustomer(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new NoSuchElementException("Customer not found: " + customerId);
        }
        return customer;
    }

    // Get an account by account number
    public Account getAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new NoSuchElementException("Account not found: " + accountNumber);
        }
        return account;
    }

    // Get all accounts for a customer
    public List<Account> getAccountsByCustomer(String customerId) {
        List<Account> customerAccounts = new ArrayList<>();
        for (Account acc : accounts.values()) {
            if (acc.getCustomer().getId().equals(customerId)) {
                customerAccounts.add(acc);
            }
        }
        return customerAccounts;
    }

    // Print all customers
    public void printAllCustomers() {
        System.out.println("Customers of " + bankName + ":");
        for (Customer customer : customers.values()) {
            System.out.println(" - " + customer.getName() + " [" + customer.getId() + "]");
        }
    }

    // Print all accounts
    public void printAllAccounts() {
        System.out.println("Accounts of " + bankName + ":");
        for (Account acc : accounts.values()) {
            System.out.println(" - Account #" + acc.getAccountNumber() +
                               ", Owner: " + acc.getCustomer().getName() +
                               ", Balance: $" + acc.getBalance());
        }
    }

    // Get total counts
    public int getTotalCustomers() {
        return customers.size();
    }

    public int getTotalAccounts() {
        return accounts.size();
    }

    public String getBankName() {
        return bankName;
    }
}
