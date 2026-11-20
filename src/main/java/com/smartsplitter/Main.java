package com.smartsplitter;

import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("      SMART EXPENSE SPLITTER");
        System.out.println("=================================");
        System.out.print("Enter group name: ");
        Group group = new Group(sc.nextLine());

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addUser(group);
                    case "2" -> addExpense(group);
                    case "3" -> showExpenses(group);
                    case "4" -> showBalances(group);
                    case "5" -> showSettlements(group);
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Something went wrong. Please try again.");
            }
        }
        System.out.println("Thank you for using Smart Expense Splitter!");
    }

    private static void printMenu() {
        System.out.println("\n1. Add user");
        System.out.println("2. Add expense");
        System.out.println("3. View expenses");
        System.out.println("4. View balances");
        System.out.println("5. Simplify settlements");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addUser(Group group) {
        System.out.print("Enter user ID: ");
        String id = sc.nextLine();
        System.out.print("Enter user name: ");
        String name = sc.nextLine();
        group.addUser(new User(id, name));
        System.out.println("User added successfully.");
    }

    private static void addExpense(Group group) {
        if (group.getUsers().isEmpty()) {
            System.out.println("Add users before adding an expense.");
            return;
        }
        System.out.print("Expense description: ");
        String description = sc.nextLine();
        System.out.print("Amount (e.g. 450.50): ");
        long amount = Money.toPaise(sc.nextLine());
        System.out.print("Paid by (user ID): ");
        User payer = group.findUser(sc.nextLine());

        List<User> participants = new ArrayList<>();
        System.out.println("Available users:");
        for (User user : group.getUsers()) System.out.println(user.getId() + " - " + user.getName());
        System.out.print("Enter participant IDs separated by commas: ");
        String[] ids = sc.nextLine().split(",");
        Set<User> unique = new LinkedHashSet<>();
        for (String id : ids) unique.add(group.findUser(id.trim()));
        participants.addAll(unique);

        group.addExpense(new Expense(description, amount, payer, participants));
        System.out.println("Expense added and split equally.");
    }

    private static void showExpenses(Group group) {
        if (group.getExpenses().isEmpty()) {
            System.out.println("No expenses added yet.");
            return;
        }
        for (Expense expense : group.getExpenses()) {
            System.out.println(expense.getDescription() + " | " + Money.format(expense.getAmountInPaise())
                    + " | Paid by: " + expense.getPaidBy().getName()
                    + " | Participants: " + expense.getParticipants());
        }
    }

    private static void showBalances(Group group) {
        Map<User, Long> balances = group.calculateBalances();
        System.out.println("\n--- CURRENT BALANCES ---");
        for (Map.Entry<User, Long> entry : balances.entrySet()) {
            long value = entry.getValue();
            String status = value > 0 ? "gets back" : value < 0 ? "owes" : "settled";
            System.out.println(entry.getKey().getName() + ": " + status + " "
                    + Money.format(Math.abs(value)));
        }
    }

    private static void showSettlements(Group group) {
        List<Settlement> settlements = new ExpenseSplitter().simplify(group.calculateBalances());
        System.out.println("\n--- SIMPLIFIED SETTLEMENTS ---");
        if (settlements.isEmpty()) {
            System.out.println("Everyone is settled. No payments required.");
            return;
        }
        for (Settlement settlement : settlements) System.out.println(settlement);
        System.out.println("Number of transactions: " + settlements.size());
    }
}
