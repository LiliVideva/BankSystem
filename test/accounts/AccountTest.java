package accounts;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import accounts.types.AccountType;
import exceptions.ViolationException;

public class AccountTest {
    AccountType type = mock(AccountType.class);
    Account account = new Account(type, null, 500);

    @Test(expected = ViolationException.class)
    public void testTakeMoney_ReturnsViolationException_WhenGivenAccountIsBlocked() throws ViolationException {
        account = new Account(type, null, 10);
        account.takeMoney(200);
    }

    @Test(expected = ViolationException.class)
    public void testTakeMoney_ReturnsViolationException_WhenGivenAmountIsNegativeNumber() throws ViolationException {
        account.takeMoney(-10);
    }

    @Test(expected = ViolationException.class)
    public void testTakeMoney_ReturnsViolationException_WhenGivenAmountIsZero() throws ViolationException {
        account.takeMoney(0);
    }

    @Test(expected = ViolationException.class)
    public void testTakeMoney_ReturnsViolationException_WhenGivenAmountHigherThanMoneyInAccount()
            throws ViolationException {
        account.takeMoney(700);
    }

    @Test
    public void testTakeMoney_ReturnsTrue_WhenGivenAccountIsNotBlockedAndAmountLowerThanMoneyInAccount()
            throws ViolationException {
        assertTrue(account.takeMoney(300));
    }

    @Test(expected = ViolationException.class)
    public void testReceiveMoney_ReturnsViolationException_WhenGivenAmountIsNegativeNumber() throws ViolationException {
        account.receiveMoney(-10);
    }

    @Test(expected = ViolationException.class)
    public void testReceiveMoney_ReturnsViolationException_WhenGivenAmountIsZero() throws ViolationException {
        account.receiveMoney(0);
    }

    @Test
    public void testReceiveMoney_ReturnsTrue_WhenGivenAccountIsNotBlockedAndAmountLowerThanMoneyInAccount()
            throws ViolationException {
        assertTrue(account.receiveMoney(300));
    }

    @Test
    public void testViolatedMinimalAmountOfMoneyInAccount_ReturnsFalse_WhenGivenMoneyInAccountIsEvenOrMoreThanMinimum()
            throws ViolationException {
        assertFalse(account.violatedMinimalAmountOfMoneyInAccount());
    }

    @Test
    public void testViolatedMinimalAmountOfMoneyInAccount_ReturnsTrue_WhenGivenMoneyInAccountIsLessThanMinimum()
            throws ViolationException {
        account = new Account(type, null, 10);
        assertTrue(account.violatedMinimalAmountOfMoneyInAccount());
    }
}
