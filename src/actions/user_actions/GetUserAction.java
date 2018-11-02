package actions.user_actions;

import java.util.ArrayList;
import java.util.List;

import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class GetUserAction extends UserAction {

    public GetUserAction(Profile currentUserProfile) {
        super(currentUserProfile);
    }

    @Override
    protected List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        List<String> arguments = new ArrayList<>();

        if (getUserProfile() != null) {
            arguments.add(getUserProfile().getUsername());
        } else {
            arguments.add("none");
        }

        if (lineArguments != null) {
            arguments.add(lineArguments);
        } else {
            System.out.print("username: ");
            arguments.add(scanner.nextLine());
        }
        return arguments;
    }

    @Override
    public boolean execute(List<String> arguments, Status userStatus, UsersInformation users)
            throws NoSuchUserException, InvalidFieldException, NotLoggedException {
        if (!userStatus.equals(Status.UNREGISTERED)) {
            users.printPersonalDetails(arguments);
            return true;
        }
        throw new NotLoggedException();
    }

}
