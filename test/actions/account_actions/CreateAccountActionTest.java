package actions.account_actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

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

public class CreateAccountActionTest {
    Profile userProfile = mock(Profile.class);
    CreateAccountAction createAccountAction = new CreateAccountAction(userProfile);
    AccountsDatabase accounts = mock(AccountsDatabase.class);
    InputScanner scanner = mock(InputScanner.class);
    List<String> accountData = Arrays.asList("user", "savingsAccount", "BG11AAAA22223333444455", "200");

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenLineArguments() {
        when(userProfile.getUsername()).thenReturn("user");

        assertEquals(accountData,
                createAccountAction.receiveArguments(scanner, "savingsAccount BG11AAAA22223333444455 200"));
    }

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenArgumentsFromScanner() {
        when(userProfile.getUsername()).thenReturn("user");
        when(scanner.nextLine()).thenReturn("savingsAccount", "BG11AAAA22223333444455", "200");

        assertEquals(accountData, createAccountAction.receiveArguments(scanner, null));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsClientAndCashingFailed()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.createAccount(null)).thenReturn(false);

        assertFalse(createAccountAction.execute(null, Status.CLIENT, accounts));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsClerkAndCashingFailed() throws UserAlreadyLoggedException,
            InvalidFieldException, NoSuchUserException, NotLoggedException, NoSuchAccountException, ViolationException {
        when(accounts.createAccount(null)).thenReturn(false);

        assertFalse(createAccountAction.execute(null, Status.CLERK, accounts));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsAdminAndCashingFailed() throws UserAlreadyLoggedException,
            InvalidFieldException, NoSuchUserException, NotLoggedException, NoSuchAccountException, ViolationException {
        when(accounts.createAccount(null)).thenReturn(false);

        assertFalse(createAccountAction.execute(null, Status.ADMIN, accounts));

    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClientAndCashingSucceeded()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.createAccount(null)).thenReturn(true);

        assertTrue(createAccountAction.execute(null, Status.CLIENT, accounts));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClerkAndCashingSucceeded()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.createAccount(null)).thenReturn(true);

        assertTrue(createAccountAction.execute(null, Status.CLERK, accounts));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsAdminAndCashingSucceeded()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.createAccount(null)).thenReturn(true);

        assertTrue(createAccountAction.execute(null, Status.ADMIN, accounts));
    }

}
