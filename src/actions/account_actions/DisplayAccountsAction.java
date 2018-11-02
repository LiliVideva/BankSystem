package actions.account_actions;

import java.util.ArrayList;
import java.util.List;

import accounts.AccountsDatabase;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.ViolationException;
import users.Profile;
import users.Status;

public class DisplayAccountsAction extends AccountAction {

    public DisplayAccountsAction(Profile userProfile) {
        super(userProfile);
    }

    @Override
    protected List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        List<String> arguments = new ArrayList<>();
        arguments.add(getUserProfile().getUsername());

        return arguments;
    }

    @Override
    protected boolean execute(List<String> arguments, Status userStatus, AccountsDatabase accounts)
            throws InvalidFieldException, ViolationException, NoSuchUserException {
        return accounts.displayAccounts(arguments);
    }
}
