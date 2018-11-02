package actions.user_actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bank.InputScanner;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import users.UsersInformation;
import users.Profile;
import users.Status;

public class SendMessageAction extends UserAction {

    public SendMessageAction(Profile currentUserProfile) {
        super(currentUserProfile);
    }

    @Override
    public List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        List<String> arguments = new ArrayList<>();

        if (getUserProfile() != null) {
            arguments.add(getUserProfile().getUsername());
        } else {
            arguments.add("none");
        }

        if (lineArguments != null) {
            arguments.addAll(Arrays.asList(lineArguments.split(" ", 2)));
        } else {
            System.out.print("username: ");
            arguments.add(scanner.nextLine());
            System.out.print("message: ");
            arguments.add(scanner.nextLine());
        }
        return arguments;
    }

    @Override
    public boolean execute(List<String> arguments, Status userStatus, UsersInformation users)
            throws NoSuchUserException, NotLoggedException {
        if (!userStatus.equals(Status.UNREGISTERED)) {
            return users.sendMessage(arguments);
        }
        throw new NotLoggedException();
    }
}
