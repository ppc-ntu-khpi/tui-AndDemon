package com.mybank.domain;

import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.jline.reader.*;
import org.jline.reader.impl.completer.*;
import org.jline.utils.*;
import org.fusesource.jansi.*;

/**
 * Console client for 'Banking' example
 *
 * @author Alexander 'Taurus' Babich
 */

public class CLIdemo {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private String[] commandsList;

    public void init() {
        commandsList = new String[]{"help", "customers", "customer", "exit", "report"};
    }

    public void run() {
        AnsiConsole.systemInstall(); // needed to support ansi on Windows cmd
        printWelcomeMessage();
        LineReaderBuilder readerBuilder = LineReaderBuilder.builder();
        List<Completer> completors = new LinkedList<Completer>();
        completors.add(new StringsCompleter(commandsList));
        readerBuilder.completer(new ArgumentCompleter(completors));
        LineReader reader = readerBuilder.build();
        String line;
        PrintWriter out = new PrintWriter(System.out);
        while ((line = readLine(reader, "")) != null) {
            if ("help".equals(line)) {
                printHelp();
            } else if ("customers".equals(line)) {
                listCustomers();
            } else if (line.startsWith("customer")) {
                showCustomerDetails(line);
            } else if ("report".equals(line)) {
                generateReport();
            } else if ("exit".equals(line)) {
                System.out.println("Exiting application");
                return;
            } else {
                System.out.println(ANSI_RED + "Invalid command, For assistance press TAB or type \"help\" then hit ENTER." + ANSI_RESET);
            }
        }
        AnsiConsole.systemUninstall();
    }

    private void printWelcomeMessage() {
        System.out.println("\nWelcome to " + ANSI_GREEN + " MyBank Console Client App" + ANSI_RESET + "! \nFor assistance press TAB or type \"help\" then hit ENTER.");
    }

    private void printHelp() {
        System.out.println("help\t\t\t- Show help");
        System.out.println("customers\t\t- Show list of customers");
        System.out.println("customer 'index'\t- Show customer details");
        System.out.println("report\t\t\t- Generate a report for customers");
        System.out.println("exit\t\t\t- Exit the app");
    }

    private String readLine(LineReader reader, String promtMessage) {
        try {
            String line = reader.readLine(promtMessage + ANSI_YELLOW + "\nbank> " + ANSI_RESET);
            return line.trim();
        } catch (UserInterruptException e) {
            // e.g. ^C
            return null;
        } catch (EndOfFileException e) {
            // e.g. ^D
            return null;
        }
    }

    private void listCustomers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("E:\\Personal files\\Programing\\java\\OOP-JAVA-master\\src\\com\\mybank\\domain\\test.dat"));
            String line;
            System.out.println("\nLast name\tFirst Name\tBalance");
            System.out.println("---------------------------------------");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String lastName = data[0];
                String firstName = data[1];
                double balance = Double.parseDouble(data[2]);
                System.out.println(lastName + "\t\t" + firstName + "\t\t$" + balance);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading file: " + e.getMessage() + ANSI_RESET);
        }
    }

    private void showCustomerDetails(String line) {
        try {
            int custNo = 0;
            String[] tokens = line.split(" ");
            if (tokens.length > 1) {
                custNo = Integer.parseInt(tokens[1]);
            }
            BufferedReader reader = new BufferedReader(new FileReader("E:\\Personal files\\Programing\\java\\OOP-JAVA-master\\src\\com\\mybank\\domain\\test.dat"));
            String dataLine;
            int counter = 0;
            while ((dataLine = reader.readLine()) != null) {
                if (counter == custNo) {
                    String[] data = dataLine.split(",");
                    String lastName = data[0];
                    String firstName = data[1];
                    double balance = Double.parseDouble(data[2]);
                    System.out.println("\nLast name\tFirst Name\tBalance");
                    System.out.println("---------------------------------------");
                    System.out.println(lastName + "\t\t" + firstName + "\t\t$" + balance);
                    reader.close();
                    return;
                }
                counter++;
            }
            reader.close();
            System.out.println(ANSI_RED + "ERROR! Wrong customer number!" + ANSI_RESET);
        } catch (IOException | NumberFormatException e) {
            System.out.println(ANSI_RED + "ERROR! Failed to retrieve customer details: " + e.getMessage() + ANSI_RESET);
        }
    }

    private void generateReport() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("E:\\Personal files\\Programing\\java\\OOP-JAVA-master\\src\\com\\mybank\\domain\\test.dat"));
            String line;
            double totalBalance = 0;
            int customerCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                double balance = Double.parseDouble(data[2]);
                totalBalance += balance;
                customerCount++;
            }
            reader.close();
            System.out.println("\nTotal number of customers: " + customerCount);
            System.out.println("Total balance across all customers: $" + totalBalance);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error generating report: " + e.getMessage() + ANSI_RESET);
        }
    }

    public static void main(String[] args) {
        CLIdemo shell = new CLIdemo();
        shell.init();
        shell.run();
    }
}
