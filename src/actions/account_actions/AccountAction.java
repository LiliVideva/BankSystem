package actions.account_actions;

import java.util.List;

import accounts.AccountsDatabase;
import actions.Action;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchAccountException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import exceptions.ViolationException;
import users.UsersInformation;
import users.Profile;
import users.Status;

abstract class AccountAction extends Action {
    public AccountAction(Profile userProfile) {
        super(userProfile);
    }

    private boolean isLogged(Status userStatus) throws NotLoggedException {
        if (userStatus.equals(Status.UNREGISTERED)) {
            throw new NotLoggedException();
        }
        return true;
    }

    protected boolean execute(List<String> arguments, Status userStatus, AccountsDatabase accounts)
            throws ViolationException, InvalidFieldException, NoSuchUserException, NoSuchAccountException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean executeAction(InputScanner scanner, String lineArguments, Profile factoryCurrentUserProfile,
            UsersInformation users, AccountsDatabase accounts) throws InvalidFieldException, NoSuchUserException,
            NotLoggedException, UserAlreadyLoggedException, ViolationException, NoSuchAccountException {
        setUserProfile(factoryCurrentUserProfile);
        Status userStatus = checkUserStatus(getUserProfile(), users);

        if (isLogged(userStatus)) {
            List<String> arguments = receiveArguments(scanner, lineArguments);
            return execute(arguments, userStatus, users, accounts);
        }
        return false;
    }

    @Override
    protected List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected boolean execute(List<String> arguments, Status userStatus, UsersInformation users,
            AccountsDatabase accounts)
            throws ViolationException, InvalidFieldException, NoSuchUserException, NoSuchAccountException {
        return execute(arguments, userStatus, accounts);
    }
}
