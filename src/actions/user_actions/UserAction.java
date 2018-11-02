package actions.user_actions;

import java.util.List;

import accounts.AccountsDatabase;
import actions.Action;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

abstract class UserAction extends Action {

    UserAction(Profile userProfile) {
        super(userProfile);
    }

    protected boolean execute(List<String> arguments, Status userStatus, UsersInformation users)
            throws InvalidFieldException, NoSuchUserException, NotLoggedException, UserAlreadyLoggedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean executeAction(InputScanner scanner, String lineArguments, Profile factoryCurrentUserProfile,
            UsersInformation users, AccountsDatabase accounts)
            throws InvalidFieldException, NoSuchUserException, NotLoggedException, UserAlreadyLoggedException {
        setUserProfile(factoryCurrentUserProfile);

        Status userStatus = checkUserStatus(getUserProfile(), users);
        List<String> arguments = receiveArguments(scanner, lineArguments);

        if (execute(arguments, userStatus, users, accounts)) {
            return true;
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
            throws InvalidFieldException, NoSuchUserException, NotLoggedException, UserAlreadyLoggedException {
        return execute(arguments, userStatus, users);
    }
}
