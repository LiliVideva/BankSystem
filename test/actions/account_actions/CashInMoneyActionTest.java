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

public class CashInMoneyActionTest {
    Profile userProfile = mock(Profile.class);
    CashInMoneyAction cashInMoneyAction = new CashInMoneyAction(userProfile);
    AccountsDatabase accounts = mock(AccountsDatabase.class);
    InputScanner scanner = mock(InputScanner.class);
    List<String> accountData = Arrays.asList("200", "BG11AAAA22223333444455");

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenLineArguments() {
        assertEquals(accountData, cashInMoneyAction.receiveArguments(scanner, "200 BG11AAAA22223333444455"));
    }

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenArgumentsFromScanner() {
        when(scanner.nextLine()).thenReturn("200", "BG11AAAA22223333444455");

        assertEquals(accountData, cashInMoneyAction.receiveArguments(scanner, null));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsUnregisteredAndCashingFailed()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.cashInMoney(null)).thenReturn(false);

        assertFalse(cashInMoneyAction.execute(null, Status.UNREGISTERED, accounts));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsClientAndCashingFailed()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.cashInMoney(null)).thenReturn(false);

        assertFalse(cashInMoneyAction.execute(null, Status.CLIENT, accounts));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsClerkAndCashingFailed() throws UserAlreadyLoggedException,
            InvalidFieldException, NoSuchUserException, NotLoggedException, NoSuchAccountException, ViolationException {
        when(accounts.cashInMoney(null)).thenReturn(false);

        assertFalse(cashInMoneyAction.execute(null, Status.CLERK, accounts));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsAdminAndCashingFailed() throws UserAlreadyLoggedException,
            InvalidFieldException, NoSuchUserException, NotLoggedException, NoSuchAccountException, ViolationException {
        when(accounts.cashInMoney(null)).thenReturn(false);

        assertFalse(cashInMoneyAction.execute(null, Status.ADMIN, accounts));

    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsUnregisteredAndCashingSucceeded()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.cashInMoney(null)).thenReturn(true);

        assertTrue(cashInMoneyAction.execute(null, Status.UNREGISTERED, accounts));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClientAndCashingSucceeded()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.cashInMoney(null)).thenReturn(true);

        assertTrue(cashInMoneyAction.execute(null, Status.CLIENT, accounts));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClerkAndCashingSucceeded()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.cashInMoney(null)).thenReturn(true);

        assertTrue(cashInMoneyAction.execute(null, Status.CLERK, accounts));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsAdminAndCashingSucceeded()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException,
            NoSuchAccountException, ViolationException {
        when(accounts.cashInMoney(null)).thenReturn(true);

        assertTrue(cashInMoneyAction.execute(null, Status.ADMIN, accounts));
    }
}
