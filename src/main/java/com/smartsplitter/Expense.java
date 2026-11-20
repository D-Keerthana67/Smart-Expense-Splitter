package com.smartsplitter;

import java.util.List;

public class Expense {
    private final String description;
    private final long amountInPaise;
    private final User paidBy;
    private final List<User> participants;

    public Expense(String description, long amountInPaise, User paidBy, List<User> participants) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Expense description cannot be empty.");
        }
        if (amountInPaise <= 0) throw new IllegalArgumentException("Amount must be greater than zero.");
        if (paidBy == null) throw new IllegalArgumentException("Payer is required.");
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("At least one participant is required.");
        }
        this.description = description.trim();
        this.amountInPaise = amountInPaise;
        this.paidBy = paidBy;
        this.participants = List.copyOf(participants);
    }

    public String getDescription() { return description; }
    public long getAmountInPaise() { return amountInPaise; }
    public User getPaidBy() { return paidBy; }
    public List<User> getParticipants() { return participants; }
}
