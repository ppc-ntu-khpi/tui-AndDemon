package com.mybank.domain;

public class SampleData {

    public static void init() {
        Bank bank = Bank.getBank();


        bank.addCustomer("Андрій", "Грисенко");
        Customer customer1 = bank.getCustomer(0);
        customer1.addAccount(new CheckingAccount(12000.00, 50.00));


        bank.addCustomer("Дмитро", "Петрено");
        Customer customer2 = bank.getCustomer(1);
        customer2.addAccount(new SavingsAccount(1500.00, 0.05));
    }
}
