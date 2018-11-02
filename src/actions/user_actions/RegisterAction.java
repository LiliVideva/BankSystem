package actions.user_actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.UserAlreadyLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class RegisterAction extends UserAction {

    public RegisterAction(Profile currentUserProfile) {
        super(currentUserProfile);
    }

    @Override
    public List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        List<String> arguments = new ArrayList<>();

        if (getUserProfile() != null) {
            arguments.add(getUserProfile().getStatus().toString());
        } else {
            arguments.add(Status.UNREGISTERED.toString());
        }

        if (lineArguments != null) {
            arguments.addAll(Arrays.asList(lineArguments.split(" ")));
        } else {
            System.out.print("username: ");
            arguments.add(scanner.nextLine());
            System.out
                    .println("Password restrictions - min 8 characters, at least 1 lowercase, 1 uppercase and 1 digit");
            System.out.print("password: ");
            arguments.add(scanner.nextLine());
            System.out.print("repeat password: ");
            arguments.add(scanner.nextLine());
            System.out.print("email: ");
            arguments.add(scanner.nextLine());
            System.out.print("status: ");
            arguments.add(scanner.nextLine());
        }
        return arguments;
    }

    @Override
    public boolean execute(List<String> arguments, Status userStatus, UsersInformation users)
            throws InvalidFieldException, UserAlreadyLoggedException {
        if (userStatus.equals(Status.UNREGISTERED) || userStatus.equals(Status.ADMIN)) {
            return users.registerUser(arguments);
        }
        throw new UserAlreadyLoggedException();
    }

}
