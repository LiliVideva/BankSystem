package accounts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

import accounts.types.AccountType;
import accounts.types.AccountTypeFactory;
import exceptions.InvalidFieldException;
import exceptions.NoSuchAccountException;
import exceptions.NoSuchUserException;
import exceptions.ViolationException;

public class AccountsDatabase {
    private AccountTypeFactory accountTypeFactory;
    private Map<String, List<AccountProfile>> usersAccounts;
    private String database;

    public AccountsDatabase(AccountTypeFactory accountTypeFactory, String database) {
        this.accountTypeFactory = accountTypeFactory;
        this.database = database;
        usersAccounts = new LinkedHashMap<>();
        restoreAccountsFromDatabase();
    }

    public void writeAccountsInDatabase() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(database, false))) {
            objectOutputStream.writeObject(usersAccounts);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean createAccount(List<String> arguments) throws InvalidFieldException, ViolationException {
        if (validateArgumentsListSize(arguments, 4)) {
            String holder = arguments.get(0);
            AccountType type = accountTypeFactory.getAccountType(arguments.get(1));
            String iban = arguments.get(2);
            double money = Double.parseDouble(arguments.get(3));

            if (validateIban(iban) && satisfyMinimumOpeningDeposit(type, money)) {
                Account account = new Account(type, iban, money);
                List<AccountProfile> accountProfiles = new ArrayList<>();

                if (usersAccounts.containsKey(holder)) {
                    accountProfiles = usersAccounts.remove(holder);
                }

                accountProfiles.add(new AccountProfile(account));
                usersAccounts.put(holder, accountProfiles);
                return true;
            }
        }
        return false;
    }

    public boolean transferMoney(List<String> arguments)
            throws NoSuchUserException, InvalidFieldException, NoSuchAccountException, ViolationException {
        if (validateArgumentsListSize(arguments, 4)) {
            String sender = arguments.get(0);
            String senderAccountIban = arguments.get(1);
            String receiverAccountIban = arguments.get(2);
            double money = Double.parseDouble(arguments.get(3));

            if (validateIban(senderAccountIban) && validateIban(receiverAccountIban)
                    && !senderAccountIban.equals(receiverAccountIban)) {
                AccountProfile senderAccountProfile = findAccountProfileByHolderAndIban(sender, senderAccountIban);
                AccountProfile receiverAccountProfile = findAccountProfileByIban(receiverAccountIban);

                if (senderAccountProfile.takeMoney(money) && receiverAccountProfile.receiveMoney(money)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean cashInMoney(List<String> arguments)
            throws InvalidFieldException, NoSuchAccountException, ViolationException {
        if (validateArgumentsListSize(arguments, 2)) {
            double money = Double.parseDouble(arguments.get(0));
            String iban = arguments.get(1);

            if (validateIban(iban)) {
                AccountProfile accountProfile = findAccountProfileByIban(iban);

                return accountProfile.receiveMoney(money);
            }
        }
        return false;
    }

    public boolean withdrawMoney(List<String> arguments)
            throws NoSuchUserException, InvalidFieldException, NoSuchAccountException, ViolationException {
        if (validateArgumentsListSize(arguments, 3)) {
            String holder = arguments.get(0);
            String iban = arguments.get(1);
            double money = Double.parseDouble(arguments.get(2));

            if (validateIban(iban)) {
                AccountProfile accountProfile = findAccountProfileByHolderAndIban(holder, iban);

                return accountProfile.takeMoney(money);
            }
        }
        return false;
    }

    public boolean displayAccounts(List<String> arguments) throws NoSuchUserException {
        if (validateArgumentsListSize(arguments, 1)) {
            String holder = arguments.get(0);
            List<AccountProfile> accountProfiles = usersAccounts.get(holder);

            if (accountProfiles != null) {
                accountProfiles.stream().sorted(new Comparator<AccountProfile>() {
                    @Override
                    public int compare(AccountProfile o1, AccountProfile o2) {
                        return o1.compareTo(o2);
                    }
                }).map(AccountProfile::getAccount).forEach(x -> x.printAccountDetails());
                return true;
            } else {
                System.out.println("No accounts!");
            }
        }
        return false;
    }

    public void printViolationMessages(String holder) {
        if (usersAccounts.get(holder) != null) {
            usersAccounts.get(holder).stream().forEach(accountProfile -> {
                if (!accountProfile.getViolationMessages().isEmpty()) {
                    System.out.printf("Account: %s%n", accountProfile.getIban());
                    System.out.println("Violation messages:");
                    accountProfile.getViolationMessages().stream().forEach(message -> {
                        System.out.println(message);
                    });
                }
            });
        }
    }

    public double calculateBestSavings(List<String> arguments) throws NoSuchAccountException {
        if (validateArgumentsListSize(arguments, 1)) {
            String holder = arguments.get(0);
            List<AccountProfile> accountProfiles = usersAccounts.get(holder);

            if (accountProfiles != null) {
                double incomes = accountProfiles.stream()
                        .filter(x -> !x.getAccountType().getType().equals("savingsAccount"))
                        .mapToDouble(AccountProfile::getIncomes).sum();
                double outcomes = accountProfiles.stream()
                        .filter(x -> !x.getAccountType().getType().equals("savingsAccount"))
                        .mapToDouble(AccountProfile::getOutcomes).sum();
                double currentMoney = accountProfiles.stream()
                        .filter(x -> !x.getAccountType().getType().equals("savingsAccount"))
                        .mapToDouble(AccountProfile::getMoney).sum();
                double accountsFees = accountProfiles.stream().map(AccountProfile::getAccountType)
                        .mapToInt(AccountType::getPeriodServiceFee).sum();

                if (incomes > outcomes) {
                    double percentageSpent = Double
                            .parseDouble(String.format(Locale.ENGLISH, "%.2f", (outcomes / incomes)));
                    double spareMoneyWithBankPayments = (1 - percentageSpent - 0.05) * incomes + currentMoney;
                    double moneyCanSave = (spareMoneyWithBankPayments - accountsFees) * 0.9;
                    return Double.parseDouble(String.format(Locale.ENGLISH, "%.2f", moneyCanSave));
                }
            } else {
                throw new NoSuchAccountException("No accounts to make calculations!");
            }
        }
        return 0;
    }

    public void movePeriod(List<String> arguments) throws NoSuchAccountException {
        usersAccounts.entrySet().stream().forEach(userAccounts -> userAccounts.getValue().stream()
                .forEach(AccountProfile -> AccountProfile.movePeriod()));
    }

    public boolean setAccountInterestRate(List<String> arguments) throws NoSuchAccountException, NoSuchUserException {
        if (validateArgumentsListSize(arguments, 2)) {
            String iban = arguments.get(0);
            double interestRate = Double.parseDouble(arguments.get(1));

            AccountProfile accountProfile = findAccountProfileByIban(iban);
            accountProfile.setInterestRate(interestRate);
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void restoreAccountsFromDatabase() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(database))) {
            usersAccounts = (Map<String, List<AccountProfile>>) objectInputStream.readObject();
        } catch (Exception e) {

        }
    }

    private boolean validateArgumentsListSize(List<String> arguments, int expectedSize) {
        return expectedSize == arguments.size();
    }

    private boolean validateIban(String iban) throws InvalidFieldException {
        if (iban.matches("^BG\\d{2}[A-Z]{4}\\d{6}[0-9A-Z]{8}$")) {
            return true;
        }
        throw new InvalidFieldException("Invalid IBAN format: " + iban);
    }

    private boolean satisfyMinimumOpeningDeposit(AccountType type, double money)
            throws ViolationException, InvalidFieldException {
        int minimumOpeningDeposit;

        try {
            minimumOpeningDeposit = type.getMinimumOpeningDeposit();
        } catch (NullPointerException e) {
            throw new InvalidFieldException("No such account type!");
        }

        if (money >= minimumOpeningDeposit) {
            return true;
        }
        throw new ViolationException("Less money than the minimum opening deposit required: " + minimumOpeningDeposit);
    }

    private AccountProfile findAccountProfileByHolderAndIban(String holder, String iban)
            throws NoSuchUserException, NoSuchAccountException {
        try {
            return usersAccounts.get(holder).stream().filter(accountProfile -> iban.equals(accountProfile.getIban()))
                    .findFirst().get();
        } catch (NullPointerException e) {
            throw new NoSuchUserException("No such holder: " + holder);
        } catch (NoSuchElementException e) {
            throw new NoSuchAccountException("Holder with no such account: " + iban);
        }
    }

    private AccountProfile findAccountProfileByIban(String iban) throws NoSuchAccountException {
        for (List<AccountProfile> accountProfiles : usersAccounts.values()) {
            for (AccountProfile accountProfile : accountProfiles) {
                if (iban.equals(accountProfile.getIban())) {
                    return accountProfile;
                }
            }
        }
        throw new NoSuchAccountException("No such account: " + iban);
    }
}
