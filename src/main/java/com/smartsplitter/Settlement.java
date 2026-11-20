package com.smartsplitter;

public class Settlement {
    private final User from;
    private final User to;
    private final long amountInPaise;

    public Settlement(User from, User to, long amountInPaise) {
        this.from = from;
        this.to = to;
        this.amountInPaise = amountInPaise;
    }

    public User getFrom() { return from; }
    public User getTo() { return to; }
    public long getAmountInPaise() { return amountInPaise; }

    @Override
    public String toString() {
        return from.getName() + " pays " + to.getName() + " " + Money.format(amountInPaise);
    }
}
