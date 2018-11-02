package actions.account_actions;

import java.util.List;

import accounts.AccountsDatabase;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchAccountException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import exceptions.ViolationException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class MovePeriodAction extends AccountAction {

    public MovePeriodAction(Profile userProfile) {
        super(userProfile);
    }

    @Override
    public boolean executeAction(InputScanner scanner, String lineArguments, Profile factoryCurrentUserProfile,
            UsersInformation users, AccountsDatabase accounts) throws InvalidFieldException, NoSuchUserException,
            NotLoggedException, UserAlreadyLoggedException, ViolationException, NoSuchAccountException {

        setUserProfile(factoryCurrentUserProfile);
        Status userStatus = checkUserStatus(getUserProfile(), users);

        if (userStatus.equals(Status.CLERK) || userStatus.equals(Status.ADMIN)) {
            List<String> arguments = receiveArguments(scanner, lineArguments);
            return execute(arguments, userStatus, users, accounts);
        }
        return false;
    }

    @Override
    protected List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        return super.receiveArguments(scanner, lineArguments);
    }

    @Override
    protected boolean execute(List<String> arguments, Status userStatus, AccountsDatabase accounts)
            throws ViolationException, InvalidFieldException, NoSuchUserException, NoSuchAccountException {
        accounts.movePeriod(arguments);
        return true;
    }
}
