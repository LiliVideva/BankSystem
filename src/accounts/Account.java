package accounts;

import java.io.Serializable;
import java.util.Locale;

import accounts.types.AccountType;
import exceptions.ViolationException;

class Account implements Comparable<Account>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MINIMUM_MONEY_IN_ACCOUNT = 20;

    private AccountType accountType;
    private String iban;
    private double money;
    private double accountInterestRate;
    private boolean isBlocked;

    Account(AccountType accountType, String iban, double money) {
        this.accountType = accountType;
        this.iban = iban;
        this.money = money;
        this.accountInterestRate = accountType.getTypeInterestRate();
        this.isBlocked = violatedMinimalAmountOfMoneyInAccount();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(accountInterestRate);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
        result = prime * result + ((iban == null) ? 0 : iban.hashCode());
        result = prime * result + (isBlocked ? 1231 : 1237);
        temp = Double.doubleToLongBits(money);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        Account other = (Account) obj;
        return (((iban == null && other.iban == null) || iban.equals(other.iban))
                && (Double.doubleToLongBits(accountInterestRate) == Double.doubleToLongBits(other.accountInterestRate))
                && ((accountType == null && other.accountType == null) || accountType.equals(other.accountType)));
    }

    @Override
    public int compareTo(Account o) {
        return iban.compareTo(o.iban);
    }

    boolean takeMoney(double amount) throws ViolationException {
        if (!isBlocked) {
            if (validateAmountOfMoney(amount) && amount <= money) {
                money = Double.parseDouble(String.format(Locale.ENGLISH, "%.2f", money - amount));
                return true;
            }
            throw new ViolationException("Less than desired money in account: " + money);
        }
        throw new ViolationException("Account blocked, because of violations!");
    }

    boolean receiveMoney(double amount) throws ViolationException {
        if (validateAmountOfMoney(amount)) {
            money = Double.parseDouble(String.format(Locale.ENGLISH, "%.2f", money + amount));

            if (!violatedMinimalAmountOfMoneyInAccount() && isBlocked) {
                isBlocked = false;
            }
            return true;
        }
        return false;
    }

    boolean violatedMinimalAmountOfMoneyInAccount() {
        if (money < MINIMUM_MONEY_IN_ACCOUNT) {
            isBlocked = true;
            return true;
        }
        return false;
    }

    void payFeeAndInterestRate() {
        money = (money - accountType.getPeriodServiceFee()) * (1 - accountInterestRate);
    }

    void setInterestRate(double rate) {
        accountInterestRate = rate;
    }

    void printAccountDetails() {
        System.out.println("Type: " + accountType.getType());
        System.out.println("Iban: " + iban);
        System.out.println("Money: " + String.format(Locale.ENGLISH, "%.2f", money));
        if (isBlocked) {
            System.out.println("Blocked!");
        }
    }

    private boolean validateAmountOfMoney(double amount) throws ViolationException {
        if (amount > 0) {
            return true;
        }
        throw new ViolationException("Non-positive amount of money!");
    }

    AccountType getAccountType() {
        return accountType;
    }

    String getIban() {
        return iban;
    }

    double getMoney() {
        return money;
    }

    double getAccountInterestRate() {
        return accountInterestRate;
    }

}
