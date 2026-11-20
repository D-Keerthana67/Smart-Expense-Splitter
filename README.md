# Smart Expense Splitter

A Java console application inspired by Splitwise. Friends can record shared expenses, split them equally among selected members, view net balances, and simplify repayments using a greedy algorithm.

## Features
- Create a group and add members.
- Record an expense, its payer, and the people sharing it.
- Split each expense equally, including paise-level remainder handling.
- View each member's net balance.
- Generate simplified payments using two priority queues.
- Validate inputs and handle common errors.

## Example
If A owes B ₹100 and B owes C ₹100, the net balances can be settled with one payment: **A pays C ₹100**.

## Technologies and concepts
- Java 17
- Object-Oriented Programming
- ArrayList, LinkedHashMap, PriorityQueue
- Greedy algorithm
- Exception handling
- Maven

## Project structure
```text
smart-expense-splitter/
├── src/main/java/com/smartsplitter/
│   ├── Main.java
│   ├── User.java
│   ├── Expense.java
│   ├── Group.java
│   ├── Settlement.java
│   ├── ExpenseSplitter.java
│   └── Money.java
├── pom.xml
├── .gitignore
└── README.md
```

## Requirements
- JDK 17 or later
- Maven 3.6+

## Run the application
From the project root, run:

```bash
mvn clean compile
mvn exec:java
```

Alternatively, compile and run directly:

### Windows PowerShell
```powershell
javac -d out src/main/java/com/smartsplitter/*.java
java -cp out com.smartsplitter.Main
```

### macOS / Linux
```bash
javac -d out src/main/java/com/smartsplitter/*.java
java -cp out com.smartsplitter.Main
```

## How to use
1. Run the application and enter a group name.
2. Select `1` to add users with unique IDs.
3. Select `2` to add an expense. Enter the payer and participant IDs separated by commas.
4. Select `3` to view recorded expenses.
5. Select `4` to view net balances.
6. Select `5` to generate simplified settlements.
7. Select `0` to exit.

## Algorithm overview
For each expense, the payer is credited with the full amount and every participant is debited by their equal share. A positive net balance means the member should receive money; a negative balance means the member owes money.

The greedy settlement algorithm places debtors and creditors in priority queues. It repeatedly matches the largest outstanding debtor with the largest outstanding creditor, transfers the smaller outstanding amount, and reinserts any remaining balance. This creates a valid settlement plan using at most **k - 1 transactions**, where k is the number of non-zero balances. This greedy strategy is practical but is not guaranteed to find the globally minimum transaction count for every possible balance arrangement.

## Current scope
- In-memory storage: data resets when the application exits.
- Equal split only; custom percentage or share splits are not included.
- JDBC/MySQL persistence is a future enhancement.

## Future enhancements
- Store users and expenses in MySQL using JDBC.
- Add unequal and percentage-based splits.
- Add expense categories and date/time.
- Build a Java Swing or web-based interface.
- Add unit tests with JUnit.

## Author
**Keerthana Devan**

- GitHub: [D-Keerthana67](https://github.com/D-Keerthana67)
