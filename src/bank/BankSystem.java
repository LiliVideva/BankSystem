package bank;

import accounts.AccountsDatabase;
import accounts.types.AccountTypeFactory;
import actions.ActionFactory;
import users.UsersInformation;

public class BankSystem {
    private ActionFactory actionFactory;
    private UsersInformation users;
    private AccountsDatabase accounts;

    BankSystem(ActionFactory actionFactory, AccountTypeFactory accountTypeFactory, String usersDatabase,
            String accountsDatabase) {
        this.actionFactory = actionFactory;
        this.users = new UsersInformation(usersDatabase);
        this.accounts = new AccountsDatabase(accountTypeFactory, accountsDatabase);
    }

    void resolveAction(InputScanner scanner) {
        boolean continueWorking = false;

        do {
            continueWorking = actionFactory.processAction(scanner, users, accounts);
        } while (continueWorking);

        users.writeProfilesInDatabase();
        accounts.writeAccountsInDatabase();
    }
}
