package actions.account_actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import accounts.AccountsDatabase;
import exceptions.InvalidFieldException;
import exceptions.NoSuchAccountException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import exceptions.ViolationException;
import users.Profile;
import users.Status;

public class SetAccountInterestRateActionTest {
    Profile userProfile = mock(Profile.class);
    SetAccountInterestRateAction setAccountInterestRateAction = new SetAccountInterestRateAction(userProfile);
    AccountsDatabase accounts = mock(AccountsDatabase.class);

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsAdminAndCashingFailed()
            throws UserAlreadyLoggedException, IndexOutOfBoundsException, InvalidFieldException, NoSuchUserException,
            NotLoggedException, NoSuchAccountException, ViolationException {
        when(accounts.setAccountInterestRate(null)).thenReturn(false);

        assertFalse(setAccountInterestRateAction.execute(null, Status.ADMIN, accounts));

    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClientAndCashingSucceeded()
            throws UserAlreadyLoggedException, IndexOutOfBoundsException, InvalidFieldException, NoSuchUserException,
            NotLoggedException, NoSuchAccountException, ViolationException {
        when(accounts.setAccountInterestRate(null)).thenReturn(true);

        assertTrue(setAccountInterestRateAction.execute(null, Status.CLIENT, accounts));
    }
}
