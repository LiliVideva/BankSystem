package accounts;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Period implements Serializable {
    private static final long serialVersionUID = 1L;

    private double moneyAtTheStartOfPeriod;
    private Map<String, List<Transaction>> accountOperations;

    Period(double moneyAtTheStartOfPeriod) {
        accountOperations = new LinkedHashMap<>();
        accountOperations.put("income", new LinkedList<>());
        accountOperations.put("outcome", new LinkedList<>());
    }

    void addTransaction(String transactionType, double money) {
        List<Transaction> transactions = accountOperations.remove(transactionType);
        transactions.add(new Transaction(money));
        accountOperations.put(transactionType, transactions);
    }

    void printPeriodHistory() {
        System.out.println("Money at the start: " + moneyAtTheStartOfPeriod);
        accountOperations.entrySet().stream().forEach(operation -> {
            System.out.println(operation.getKey());
            operation.getValue().stream().forEach(transaction -> {
                System.out.printf("Date of transaction: %s Money: %s%n", transaction.getDate().toString(),
                        transaction.getMoney());
            });
        });
    }

    double getMoneyAtTheStartOfPeriod() {
        return moneyAtTheStartOfPeriod;
    }

    Map<String, List<Transaction>> getAccountOperations() {
        return accountOperations;
    }
}
