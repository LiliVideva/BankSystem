package actions;

import java.util.Map;

import accounts.AccountsDatabase;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchAccountException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import exceptions.ViolationException;
import users.Profile;
import users.UsersInformation;

public class ActionFactory {
    private static final String PROGRAM_TERMINATOR = "exit";
    private Map<String, Action> actionsList;
    protected Profile userProfile;

    public ActionFactory(Map<String, Action> actionsList, Profile userProfile) {
        this.actionsList = actionsList;
        this.userProfile = userProfile;
    }

    public boolean processAction(InputScanner scanner, UsersInformation users, AccountsDatabase accounts) {
        String line = scanner.nextLine();
        if (!line.equals(PROGRAM_TERMINATOR)) {
            String[] splittedLine = line.split(" ", 2);
            String actionName = splittedLine[0];
            String lineArguments = null;

            try {
                lineArguments = splittedLine[1];
            } catch (IndexOutOfBoundsException e) {
            }

            try {
                Action action = getAction(actionName);

                if (action == null) {
                    System.out.println("No such command to be executed!");
                } else {
                    if (action.executeAction(scanner, lineArguments, userProfile, users, accounts)) {
                        userProfile = action.getUserProfile();
                        System.out.println("Action executed successfully!");
                        users.writeProfilesInDatabase();
                        accounts.writeAccountsInDatabase();
                    }
                }
            } catch (InvalidFieldException | NoSuchUserException | NotLoggedException | UserAlreadyLoggedException
                    | ViolationException | NoSuchAccountException e) {
                System.out.println("Action failed!");
                System.out.println(e.getMessage());
                return true;
            }
            return true;
        }
        return false;
    }

    private Action getAction(String actionName) {
        return actionsList.get(actionName);
    }
}
