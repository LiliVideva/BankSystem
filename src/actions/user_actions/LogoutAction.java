package actions.user_actions;

import java.util.List;

import bank.InputScanner;
import exceptions.NotLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class LogoutAction extends UserAction {

    public LogoutAction(Profile currentUserProfile) {
        super(currentUserProfile);
    }

    @Override
    protected List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        return super.receiveArguments(scanner, lineArguments);
    }

    @Override
    protected boolean execute(List<String> arguments, Status userStatus, UsersInformation users)
            throws NotLoggedException {
        if (!userStatus.equals(Status.UNREGISTERED)) {
            setUserProfile(null);
            return true;
        }
        throw new NotLoggedException();
    }
}
