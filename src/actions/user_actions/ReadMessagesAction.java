package actions.user_actions;

import java.util.Arrays;
import java.util.List;

import bank.InputScanner;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import users.UsersInformation;
import users.Profile;
import users.Status;

public class ReadMessagesAction extends UserAction {

    public ReadMessagesAction(Profile currentUserProfile) {
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
            return users.readMessages(Arrays.asList(getUserProfile().getUsername()));
        }
        throw new NotLoggedException();
    }
}
