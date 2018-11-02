package actions.account_actions;

import java.util.Arrays;
import java.util.List;

import accounts.AccountsDatabase;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchAccountException;
import exceptions.NoSuchUserException;
import exceptions.ViolationException;
import users.Profile;
import users.Status;

public class CalculateBestSavingsAction extends AccountAction {

    public CalculateBestSavingsAction(Profile userProfile) {
        super(userProfile);
    }

    @Override
    protected List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        return super.receiveArguments(scanner, lineArguments);
    }

    @Override
    protected boolean execute(List<String> arguments, Status userStatus, AccountsDatabase accounts)
            throws ViolationException, InvalidFieldException, NoSuchUserException, NoSuchAccountException {
        double savings = accounts.calculateBestSavings(Arrays.asList(getUserProfile().getUsername()));
        System.out.printf("You can save %.2f!%n", savings);
        return true;
    }

}
