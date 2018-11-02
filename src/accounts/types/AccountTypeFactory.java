package accounts.types;

import java.util.Map;

public class AccountTypeFactory {
    private Map<String, AccountType> accountTypesList;

    public AccountTypeFactory(Map<String, AccountType> accountTypesList) {
        this.accountTypesList = accountTypesList;
    }

    public AccountType getAccountType(String accountType) throws NullPointerException {
        return accountTypesList.get(accountType);
    }

    public void printAccountTypesList() {
        accountTypesList.entrySet().stream().forEach(x -> {
            System.out.printf("Type: %s%n Description: %s", x.getKey(), x.getValue().getDescription());
        });
    }
}
