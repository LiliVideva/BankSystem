package actions;

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

public abstract class Action {
    private Profile userProfile;

    protected Action(Profile userProfile) {
        this.userProfile = userProfile;
    }

    protected Profile getUserProfile() {
        return userProfile;
    }

    protected void setUserProfile(Profile userProfile) {
        this.userProfile = userProfile;
    }

    public abstract boolean executeAction(InputScanner scanner, String lineArguments, Profile factoryCurrentUserProfile,
            UsersInformation users, AccountsDatabase accounts) throws InvalidFieldException, NoSuchUserException,
            NotLoggedException, UserAlreadyLoggedException, ViolationException, NoSuchAccountException;

    protected abstract List<String> receiveArguments(InputScanner scanner, String lineArguments);

    protected abstract boolean execute(List<String> arguments, Status userStatus, UsersInformation users,
            AccountsDatabase accounts) throws InvalidFieldException, NoSuchUserException, NotLoggedException,
            UserAlreadyLoggedException, ViolationException, NoSuchAccountException;

    protected Status checkUserStatus(Profile userProfile, UsersInformation users) {
        Status userStatus = (userProfile == null) ? Status.UNREGISTERED
                : users.checkUserStatus(userProfile.getUsername());
        return userStatus;
    }
}
