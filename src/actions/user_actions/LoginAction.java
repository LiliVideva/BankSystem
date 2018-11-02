package actions.user_actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import accounts.AccountsDatabase;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.UserAlreadyLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class LoginAction extends UserAction {

    public LoginAction(Profile currentUserProfile) {
        super(currentUserProfile);
    }

    @Override
    public List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        List<String> arguments = new ArrayList<>();

        if (lineArguments != null) {
            arguments.addAll(Arrays.asList(lineArguments.split(" ")));
        } else {
            System.out.print("username: ");
            arguments.add(scanner.nextLine());
            System.out.print("password: ");
            arguments.add(scanner.nextLine());
        }
        return arguments;
    }

    @Override
    protected boolean execute(List<String> arguments, Status userStatus, UsersInformation users,
            AccountsDatabase accounts) throws InvalidFieldException, NoSuchUserException, UserAlreadyLoggedException {
        if (userStatus.equals(Status.UNREGISTERED)) {
            if (users.loginUser(arguments)) {
                setUserProfile(users.getPersonalDetails(arguments));

                String userProfile = getUserProfile().getUsername();
                accounts.printViolationMessages(userProfile);
                return true;
            }
            return false;
        }
        throw new UserAlreadyLoggedException();
    }

}
