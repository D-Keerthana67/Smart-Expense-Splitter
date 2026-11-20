package com.smartsplitter;

import java.util.*;

public class Group {
    private final String name;
    private final Map<String, User> users = new LinkedHashMap<>();
    private final List<Expense> expenses = new ArrayList<>();

    public Group(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Group name cannot be empty.");
        this.name = name.trim();
    }

    public String getName() { return name; }
    public Collection<User> getUsers() { return Collections.unmodifiableCollection(users.values()); }
    public List<Expense> getExpenses() { return Collections.unmodifiableList(expenses); }

    public void addUser(User user) {
        String key = user.getId().toLowerCase();
        if (users.containsKey(key)) throw new IllegalArgumentException("A user with this ID already exists.");
        users.put(key, user);
    }

    public User findUser(String id) {
        User user = users.get(id.trim().toLowerCase());
        if (user == null) throw new IllegalArgumentException("User ID not found: " + id);
        return user;
    }

    public void addExpense(Expense expense) {
        if (!users.containsKey(expense.getPaidBy().getId().toLowerCase())) {
            throw new IllegalArgumentException("The payer must belong to this group.");
        }
        for (User participant : expense.getParticipants()) {
            if (!users.containsKey(participant.getId().toLowerCase())) {
                throw new IllegalArgumentException("Every participant must belong to this group.");
            }
        }
        expenses.add(expense);
    }

    // Positive balance means the user should receive money; negative means they owe money.
    public Map<User, Long> calculateBalances() {
        Map<User, Long> balances = new LinkedHashMap<>();
        for (User user : users.values()) balances.put(user, 0L);

        for (Expense expense : expenses) {
            long total = expense.getAmountInPaise();
            List<User> people = expense.getParticipants();
            long eachShare = total / people.size();
            long remainder = total % people.size();

            balances.put(expense.getPaidBy(), balances.get(expense.getPaidBy()) + total);

            for (int i = 0; i < people.size(); i++) {
                long share = eachShare + (i < remainder ? 1 : 0);
                User participant = people.get(i);
                balances.put(participant, balances.get(participant) - share);
            }
        }
        return balances;
    }
}
