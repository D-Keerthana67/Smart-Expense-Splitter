package com.smartsplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money {
    private Money() {}

    public static long toPaise(String input) {
        try {
            BigDecimal amount = new BigDecimal(input.trim()).setScale(2, RoundingMode.UNNECESSARY);
            long paise = amount.movePointRight(2).longValueExact();
            if (paise <= 0) throw new IllegalArgumentException("Amount must be greater than zero.");
            return paise;
        } catch (NumberFormatException | ArithmeticException ex) {
            throw new IllegalArgumentException("Enter a valid amount with at most 2 decimal places.");
        }
    }

    public static String format(long paise) {
        return "₹" + BigDecimal.valueOf(paise, 2).setScale(2, RoundingMode.UNNECESSARY);
    }
}
