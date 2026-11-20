package com.smartsplitter;

import java.util.*;

public class ExpenseSplitter {
    private static class Entry {
        User user;
        long amount;
        Entry(User user, long amount) { this.user = user; this.amount = amount; }
    }

    // Greedy matching: pair the largest debtor with the largest creditor each time.
    public List<Settlement> simplify(Map<User, Long> balances) {
        PriorityQueue<Entry> debtors = new PriorityQueue<>((a, b) -> Long.compare(b.amount, a.amount));
        PriorityQueue<Entry> creditors = new PriorityQueue<>((a, b) -> Long.compare(b.amount, a.amount));

        for (Map.Entry<User, Long> entry : balances.entrySet()) {
            long balance = entry.getValue();
            if (balance < 0) debtors.add(new Entry(entry.getKey(), -balance));
            else if (balance > 0) creditors.add(new Entry(entry.getKey(), balance));
        }

        List<Settlement> result = new ArrayList<>();
        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            Entry debtor = debtors.poll();
            Entry creditor = creditors.poll();
            long payment = Math.min(debtor.amount, creditor.amount);

            result.add(new Settlement(debtor.user, creditor.user, payment));
            debtor.amount -= payment;
            creditor.amount -= payment;

            if (debtor.amount > 0) debtors.add(debtor);
            if (creditor.amount > 0) creditors.add(creditor);
        }
        return result;
    }
}
