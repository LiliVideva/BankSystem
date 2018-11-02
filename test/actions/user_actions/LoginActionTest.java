package actions.user_actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import accounts.AccountsDatabase;
import accounts.types.AccountTypeFactory;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class LoginActionTest {
    Profile userProfile = mock(Profile.class);
    UserAction loginAction = new LoginAction(userProfile);
    UsersInformation users = new UsersInformation("users.txt");
    AccountTypeFactory accountTypeFactory = mock(AccountTypeFactory.class);
    AccountsDatabase accounts = new AccountsDatabase(accountTypeFactory, "accounts.txt");

    InputScanner scanner = mock(InputScanner.class);
    List<String> userData = Arrays.asList("user", "user66Us");

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenLineArguments() {
        when(userProfile.getUsername()).thenReturn("user");

        assertEquals(userData, loginAction.receiveArguments(scanner, "user user66Us"));
    }

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenArgumentsFromScanner() {
        when(userProfile.getUsername()).thenReturn("user");
        when(scanner.nextLine()).thenReturn("user", "user66Us");

        assertEquals(userData, loginAction.receiveArguments(scanner, null));
    }

    @Test(expected = UserAlreadyLoggedException.class)
    public void testExecute_ReturnsUserAlreadyLoggedException_WhenGivenUserStatusIsClient()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException {
        loginAction.execute(null, Status.CLIENT, users, accounts);
    }

    @Test(expected = UserAlreadyLoggedException.class)
    public void testExecute_ReturnsUserAlreadyLoggedException_WhenGivenUserStatusIsClerk()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException {
        loginAction.execute(null, Status.CLERK, users, accounts);
    }

    @Test(expected = UserAlreadyLoggedException.class)
    public void testExecute_ReturnsUserAlreadyLoggedException_WhenGivenUserStatusIsAdmin()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException {
        loginAction.execute(null, Status.ADMIN, users, accounts);

    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsUnregisteredAndLoginSucceeded()
            throws InvalidFieldException, NoSuchUserException, UserAlreadyLoggedException, NotLoggedException {
        when(userProfile.getUsername()).thenReturn("user");

        assertTrue(users.loginUser(userData));

        assertTrue(loginAction.execute(userData, Status.UNREGISTERED, users, accounts));
    }
}
