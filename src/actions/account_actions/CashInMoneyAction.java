package actions.account_actions;

import java.util.ArrayList;
import java.util.Arrays;
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

public class CashInMoneyAction extends AccountAction {

    public CashInMoneyAction(Profile userProfile) {
        super(userProfile);
    }

    @Override
    public boolean executeAction(InputScanner scanner, String lineArguments, Profile factoryCurrentUserProfile,
            UsersInformation users, AccountsDatabase accounts) throws InvalidFieldException, NoSuchUserException,
            NotLoggedException, UserAlreadyLoggedException, ViolationException, NoSuchAccountException {

        setUserProfile(factoryCurrentUserProfile);
        Status userStatus = checkUserStatus(getUserProfile(), users);

        List<String> arguments = receiveArguments(scanner, lineArguments);
        return execute(arguments, userStatus, users, accounts);
    }

    @Override
    protected List<String> receiveArguments(InputScanner scanner, String lineArguments) {
        List<String> arguments = new ArrayList<>();

        if (lineArguments != null) {
            arguments.addAll(Arrays.asList(lineArguments.split(" ")));
        } else {
            System.out.print("money: ");
            arguments.add(scanner.nextLine());
            System.out.println("IBAN format - BG%%****%%%%%%%%%%%%%%, where % - digit (16), * - capital letter (4)");
            System.out.print("IBAN: ");
            arguments.add(scanner.nextLine());
        }

        return arguments;
    }

    @Override
    protected boolean execute(List<String> arguments, Status userStatus, AccountsDatabase accounts)
            throws InvalidFieldException, NoSuchUserException, NoSuchAccountException, ViolationException {
        return accounts.cashInMoney(arguments);
    }

}
