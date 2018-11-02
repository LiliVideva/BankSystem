package accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import accounts.types.AccountType;
import exceptions.ViolationException;

class AccountProfile implements Comparable<AccountProfile>, Serializable {
    private static final long serialVersionUID = 1L;

    private Account account;
    private List<String> violationMessages;
    private Stack<Period> accountHistory;

    AccountProfile(Account account) {
        this.account = account;
        this.violationMessages = new ArrayList<>();
        controlViolatedMinimalAmountOfMoneyInAccountMessage();
        accountHistory = new Stack<>();
        startNewPeriod();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((violationMessages == null) ? 0 : violationMessages.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AccountProfile other = (AccountProfile) obj;
        return (((account == null && other.account == null) || account.equals(other.account))
                && ((violationMessages == null && other.violationMessages == null)
                        || violationMessages.equals(other.violationMessages)));
    }

    @Override
    public int compareTo(AccountProfile o) {
        return account.compareTo(o.account);
    }

    boolean takeMoney(double amount) throws ViolationException {
        if (account.takeMoney(amount)) {
            controlViolatedMinimalAmountOfMoneyInAccountMessage();
            addTransaction("outcome", amount);
            return true;
        }
        return false;
    }

    boolean receiveMoney(double amount) throws ViolationException {
        if (account.receiveMoney(amount)) {
            controlViolatedMinimalAmountOfMoneyInAccountMessage();
            addTransaction("income", amount);
            return true;
        }
        return false;
    }

    double getIncomes() {
        Period lastFullPeriod = getLastFullPeriod();
        return lastFullPeriod.getAccountOperations().get("income").stream().mapToDouble(Transaction::getMoney).sum();
    }

    double getOutcomes() {
        Period lastFullPeriod = getLastFullPeriod();
        return lastFullPeriod.getAccountOperations().get("outcome").stream().mapToDouble(Transaction::getMoney).sum();
    }

    void setInterestRate(double rate) {
        account.setInterestRate(rate);
    }

    void movePeriod() {
        account.payFeeAndInterestRate();
        startNewPeriod();
    }

    void printAccountDetails() {
        account.printAccountDetails();
    }

    void printLastPeriodHistory() {
        accountHistory.peek().printPeriodHistory();
    }

    private void startNewPeriod() {
        accountHistory.push(new Period(account.getMoney()));
    }

    private void controlViolatedMinimalAmountOfMoneyInAccountMessage() {
        if (account.violatedMinimalAmountOfMoneyInAccount()) {
            violationMessages.add("Less than minimal amount of money in account!");
        } else {
            if (!violationMessages.isEmpty()) {
                violationMessages.remove(0);
            }
        }
    }

    private void addTransaction(String transactionType, double money) {
        Period currentPeriod = accountHistory.pop();
        currentPeriod.addTransaction(transactionType, money);
        accountHistory.push(currentPeriod);
    }

    private Period getLastFullPeriod() {
        Period currentPeriod = accountHistory.pop();

        if (!accountHistory.empty()) {
            Period lastPeriod = accountHistory.peek();
            accountHistory.push(currentPeriod);
            return lastPeriod;
        }
        accountHistory.push(currentPeriod);
        return currentPeriod;
    }

    Account getAccount() {
        return account;
    }

    List<String> getViolationMessages() {
        return violationMessages;
    }

    AccountType getAccountType() {
        return account.getAccountType();
    }

    String getIban() {
        return account.getIban();
    }

    double getMoney() {
        return account.getMoney();
    }
}
