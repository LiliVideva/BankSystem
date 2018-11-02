package actions.user_actions;

import java.util.Arrays;
import java.util.List;

import bank.InputScanner;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class DeleteUserAction extends UserAction {

    public DeleteUserAction(Profile currentUserProfile) {
        super(currentUserProfile);
    }

    @Override
    protected List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        return super.receiveArguments(scanner, lineArguments);
    }

    @Override
    public boolean execute(List<String> arguments, Status userStatus, UsersInformation users)
            throws NoSuchUserException, NotLoggedException {
        if (!userStatus.equals(Status.UNREGISTERED)) {
            if (users.deleteUser(Arrays.asList(getUserProfile().getUsername()))) {
                setUserProfile(null);
                return true;
            }
            return false;
        }
        throw new NotLoggedException();
    }
}
