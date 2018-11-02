package accounts;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import accounts.types.AccountType;
import exceptions.ViolationException;

public class AccountProfileTest {
    AccountType type = mock(AccountType.class);
    AccountProfile accountProfile = new AccountProfile(new Account(type, null, 500));

    @Test(expected = ViolationException.class)
    public void testTakeMoney_ReturnsViolationException_WhenGivenAccountIsBlocked() throws ViolationException {
        accountProfile = new AccountProfile(new Account(type, null, 10));

        accountProfile.takeMoney(200);
    }

    @Test(expected = ViolationException.class)
    public void testTakeMoney_ReturnsViolationException_WhenGivenAmountIsNegativeNumber() throws ViolationException {
        accountProfile.takeMoney(-10);
    }

    @Test(expected = ViolationException.class)
    public void testTakeMoney_ReturnsViolationException_WhenGivenAmountIsZero() throws ViolationException {
        accountProfile.takeMoney(0);
    }

    @Test(expected = ViolationException.class)
    public void testTakeMoney_ReturnsViolationException_WhenGivenAmountHigherThanMoneyInAccount()
            throws ViolationException {
        accountProfile.takeMoney(700);
    }

    @Test
    public void testTakeMoney_ReturnsTrueAndBlockesAccountAndAddsViolationMessage_WhenGivenAccountIsNotBlockedAndAmountLowerThanMoneyInAccountButResultMoneyIsLessThanMinimum()
            throws ViolationException {
        int violationMessagesCount = accountProfile.getViolationMessages().size();

        assertTrue(accountProfile.takeMoney(490));
        assertEquals(violationMessagesCount + 1, accountProfile.getViolationMessages().size());
    }

    @Test
    public void testTakeMoney_ReturnsTrue_WhenGivenAccountIsNotBlockedAndAmountLowerThanMoneyInAccount()
            throws ViolationException {
        assertTrue(accountProfile.takeMoney(300));
    }

    @Test(expected = ViolationException.class)
    public void testReceiveMoney_ReturnsViolationException_WhenGivenAmountIsNegativeNumber() throws ViolationException {
        accountProfile.receiveMoney(-10);
    }

    @Test(expected = ViolationException.class)
    public void testReceiveMoney_ReturnsViolationException_WhenGivenAmountIsZero() throws ViolationException {
        accountProfile.receiveMoney(0);
    }

    @Test
    public void testReceiveMoney_ReturnsTrueAndUnblockesAccountAndRemovesViolationMessage_WhenGivenAccountIsBlockedAndAmountLowerThanMoneyInAccountAndResultMoneyIsEvenOrMoreThanMinimum()
            throws ViolationException {
        accountProfile = new AccountProfile(new Account(type, null, 10));

        int violationMessagesCount = accountProfile.getViolationMessages().size();

        assertTrue(accountProfile.receiveMoney(490));
        assertEquals(violationMessagesCount - 1, accountProfile.getViolationMessages().size());
    }

    @Test
    public void testReceiveMoney_ReturnsTrue_WhenGivenAccountIsNotBlockedAndAmountLowerThanMoneyInAccount()
            throws ViolationException {
        assertTrue(accountProfile.receiveMoney(300));
    }

}
