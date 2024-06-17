# UI Lab 1
Грисенка Андрія 35 - група
![image](https://github.com/AndDemon/Banking/assets/115999885/209e6e04-496f-4e7e-8984-8742ffac9354)
TUIdemo.java:
```java
package com.mybank.domain;
import jexer.TAction;
import jexer.TApplication;
import jexer.TField;
import jexer.TText;
import jexer.TWindow;
import jexer.event.TMenuEvent;
import jexer.menu.TMenu;

public class TUIdemo extends TApplication {

    private static final int ABOUT_APP = 2000;
    private static final int CUST_INFO = 2010;

    public static void main(String[] args) throws Exception {

        SampleData.init();

        TUIdemo tdemo = new TUIdemo();
        (new Thread(tdemo)).start();
    }

    public TUIdemo() throws Exception {
        super(BackendType.SWING);

        addToolMenu();

        TMenu fileMenu = addMenu("&File");
        fileMenu.addItem(CUST_INFO, "&Customer Info");
        fileMenu.addDefaultItem(TMenu.MID_SHELL);
        fileMenu.addSeparator();
        fileMenu.addDefaultItem(TMenu.MID_EXIT);


        addWindowMenu();


        TMenu helpMenu = addMenu("&Help");
        helpMenu.addItem(ABOUT_APP, "&About...");


        setFocusFollowsMouse(true);

        ShowCustomerDetails();
    }

    @Override
    protected boolean onMenu(TMenuEvent menu) {
        if (menu.getId() == ABOUT_APP) {
            messageBox("About", "\t\t\t\t\t   Just a simple Jexer demo.\n\nCopyright \u00A9 2019 Alexander 'Taurus' Babich").show();
            return true;
        }
        if (menu.getId() == CUST_INFO) {
            ShowCustomerDetails();
            return true;
        }
        return super.onMenu(menu);
    }

    private void ShowCustomerDetails() {
        TWindow custWin = addWindow("Customer Window", 2, 1, 40, 10, TWindow.NOZOOMBOX);
        custWin.newStatusBar("Enter valid customer number and press Show...");

        custWin.addLabel("Enter customer number: ", 2, 2);
        TField custNo = custWin.addField(24, 2, 3, false);
        TText details = custWin.addText("Owner Name: \nAccount Type: \nAccount Balance: ", 2, 4, 38, 8);
        custWin.addButton("&Show", 28, 2, new TAction() {
            @Override
            public void DO() {
                try {
                    int custNum = Integer.parseInt(custNo.getText());
                    Bank bank = Bank.getBank();
                    Customer customer = bank.getCustomer(custNum);

                    if (customer == null) {
                        messageBox("Error", "Customer not found!").show();
                        return;
                    }

                    StringBuilder customerDetails = new StringBuilder();
                    customerDetails.append("Owner Name: ").append(customer.getFirstName()).append(" ").append(customer.getLastName()).append("\n");

                    if (customer.getNumberOfAccounts() > 0) {
                        Account account = customer.getAccount(0);
                        if (account instanceof CheckingAccount) {
                            customerDetails.append("Account Type: Checking\n");
                        } else if (account instanceof SavingsAccount) {
                            customerDetails.append("Account Type: Savings\n");
                        }
                        customerDetails.append("Account Balance: $").append(account.getBalance()).append("\n");
                    } else {
                        customerDetails.append("No accounts found for this customer.");
                    }

                    details.setText(customerDetails.toString());
                } catch (Exception e) {
                    messageBox("Error", "You must provide a valid customer number!").show();
                }
            }
        });
    }
}
```

SampleData.java:
```java
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

```
![image](https://github.com/AndDemon/Banking/assets/115999885/f69d6d0a-75a4-4591-86ac-4d78219e81d0)
![image](https://github.com/AndDemon/Banking/assets/115999885/3df846ea-8e37-4700-9817-f733cdcf9859)

