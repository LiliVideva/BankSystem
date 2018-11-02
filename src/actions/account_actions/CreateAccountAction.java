package actions.account_actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import accounts.AccountsDatabase;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.ViolationException;
import users.Profile;
import users.Status;

public class CreateAccountAction extends AccountAction {

    public CreateAccountAction(Profile userProfile) {
        super(userProfile);
    }

    @Override
    protected List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        List<String> arguments = new ArrayList<>();
        arguments.add(getUserProfile().getUsername());

        if (lineArguments != null) {
            arguments.addAll(Arrays.asList(lineArguments.split(" ")));
        } else {
            System.out.print("type: ");
            arguments.add(scanner.nextLine());
            System.out.println("IBAN format - BG%%****%%%%%%%%%%%%%%, where % - digit (16), * - capital letter (4)");
            System.out.print("IBAN: ");
            arguments.add(scanner.nextLine());
            System.out.print("money: ");
            arguments.add(scanner.nextLine());
        }
        return arguments;
    }

    @Override
    protected boolean execute(List<String> arguments, Status userStatus, AccountsDatabase accounts)
            throws InvalidFieldException, ViolationException {
        return accounts.createAccount(arguments);
    }
}
