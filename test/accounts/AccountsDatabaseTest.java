package accounts;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import accounts.types.AccountType;
import accounts.types.AccountTypeFactory;
import exceptions.InvalidFieldException;
import exceptions.NoSuchAccountException;
import exceptions.NoSuchUserException;
import exceptions.ViolationException;

public class AccountsDatabaseTest {
    AccountTypeFactory accountTypeFactory = mock(AccountTypeFactory.class);
    AccountType type = mock(AccountType.class);
    AccountProfile accountProfileOne = mock(AccountProfile.class);
    AccountProfile accountProfileTwo = mock(AccountProfile.class);
    AccountsDatabase accounts = new AccountsDatabase(accountTypeFactory, "accounts.txt");

    @Test(expected = InvalidFieldException.class)
    public void testCreateAccount_ReturnsInvalidFieldException_WhenGivenInvalidAccountType()
            throws InvalidFieldException, ViolationException {
        accounts.createAccount(Arrays.asList("user", "childAccount", "BG11UUUU22223333444455", "300"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testCreateAccount_ReturnsInvalidFieldException_WhenGivenInvalidIban()
            throws InvalidFieldException, ViolationException {
        accounts.createAccount(Arrays.asList("user", "savingsAccount", "BG11UUUU222233334444GG", "300"));
    }

    @Test(expected = ViolationException.class)
    public void testCreateAccount_ReturnsViolationException_WhenGivenAmountOfMoneyIsLessThanMinimumDeposit()
            throws InvalidFieldException, ViolationException {
        when(accountTypeFactory.getAccountType("savingsAccount")).thenReturn(type);
        when(type.getMinimumOpeningDeposit()).thenReturn(200);

        accounts.createAccount(Arrays.asList("user", "savingsAccount", "BG11UUUU222233334444GG", "50"));
    }

    @Test
    public void testCreateAccount_ReturnsFalse_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, ViolationException {
        assertFalse(accounts.createAccount(Arrays.asList("user", "savingsAccount", "BG11UUUU22223333444455")));
    }

    @Test
    public void testCreateAccount_ReturnsTrue_WhenGivenCorrectData() throws InvalidFieldException, ViolationException {
        when(accountTypeFactory.getAccountType("savingsAccount")).thenReturn(type);
        when(type.getMinimumOpeningDeposit()).thenReturn(200);

        assertTrue(accounts.createAccount(Arrays.asList("user", "savingsAccount", "BG11UUUU222233334444GG", "250")));
    }

    @Test(expected = InvalidFieldException.class)
    public void testTransferMoney_ReturnsInvalidFieldException_WhenGivenInvalidIban()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.transferMoney(Arrays.asList("user", "BG11UUUU22223333444455", "BGAAUUUU22223333444455", "300"));
    }

    @Test(expected = NoSuchUserException.class)
    public void testTransferMoney_ReturnsNoSuchUserException_WhenGivenInvalidSenderUsername()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.transferMoney(Arrays.asList("dummyUser", "BG11UUUU22223333444455", "BG11UUWW22223333444455", "300"));
    }

    @Test(expected = NoSuchAccountException.class)
    public void testTransferMoney_ReturnsNoSuchAccountException_WhenGivenSenderIbanNotBelongToSender()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.transferMoney(Arrays.asList("user", "BG11AAAA22223333444455", "BG11UUWW22223333444455", "300"));
    }

    @Test(expected = NoSuchAccountException.class)
    public void testTransferMoney_ReturnsNoSuchAccountException_WhenGivenReceiverIbanNotExist()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.transferMoney(Arrays.asList("user", "BG11UUUU22223333444455", "BG11AAAA22223333444455", "300"));
    }

    @Test
    public void testTransferMoney_ReturnsFalse_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        assertFalse(accounts.transferMoney(Arrays.asList("user", "BG11UUUU22223333444455", "BG11UUWW22223333444455")));
    }

    @Test
    public void testTransferMoney_ReturnsFalse_WhenGivenSenderAndReceiverIbansTheSame()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        assertFalse(accounts
                .transferMoney(Arrays.asList("user", "BG11UAUA22223333444455", "BG11UAUA22223333444455", "10")));
    }

    @Test
    public void testTransferMoney_ReturnsTrue_WhenGivenCorrectData()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        when(accountProfileOne.takeMoney(10)).thenReturn(true);
        when(accountProfileTwo.receiveMoney(10)).thenReturn(true);

        assertTrue(accounts
                .transferMoney(Arrays.asList("user", "BG11UUAA22223333444455", "BG11LLLL22223333444455", "10")));
    }

    @Test(expected = InvalidFieldException.class)
    public void testCashInMoney_ReturnsInvalidFieldException_WhenGivenInvalidIban()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.cashInMoney(Arrays.asList("300", "BGAAUUUU22223333444455"));
    }

    @Test(expected = NoSuchAccountException.class)
    public void testCashInMoney_ReturnsNoSuchAccountException_WhenGivenIbanNotExist()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.cashInMoney(Arrays.asList("300", "BG11AAAA22223333444455"));
    }

    @Test
    public void testCashInMoney_ReturnsFalse_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        assertFalse(accounts.cashInMoney(Arrays.asList("300")));
    }

    @Test
    public void testCashInMoney_ReturnsTrue_WhenGivenCorrectData()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        when(accountProfileOne.receiveMoney(200)).thenReturn(true);
        assertTrue(accounts.cashInMoney(Arrays.asList("200", "BG11LLLL22223333444455")));
    }

    @Test(expected = InvalidFieldException.class)
    public void testWithdrawMoney_ReturnsInvalidFieldException_WhenGivenInvalidIban()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.withdrawMoney(Arrays.asList("user", "BGAAUUUU22223333444455", "300"));
    }

    @Test(expected = NoSuchUserException.class)
    public void testWithdrawMoney_ReturnsNoSuchUserException_WhenGivenInvalidHolderUsername()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.withdrawMoney(Arrays.asList("dummyUser", "BG11UUUU22223333444455", "300"));
    }

    @Test(expected = NoSuchAccountException.class)
    public void testWithdrawMoney_ReturnsNoSuchAccountException_WhenGivenIbanNotExist()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.withdrawMoney(Arrays.asList("user", "BG11AAAA22223333444455", "300"));
    }

    @Test(expected = NoSuchAccountException.class)
    public void testWithdrawMoney_ReturnsNoSuchAccountException_WhenGivenIbanNotBelongToHolder()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        accounts.withdrawMoney(Arrays.asList("user", "BG11LLLL22223333444455", "300"));
    }

    @Test
    public void testWithdrawMoney_ReturnsFalse_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        assertFalse(accounts.withdrawMoney(Arrays.asList("user", "BG11UUUU22223333444455")));
    }

    @Test
    public void testWithdrawMoney_ReturnsTrue_WhenGivenCorrectData()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        when(accountProfileOne.takeMoney(10)).thenReturn(true);
        assertTrue(accounts.withdrawMoney(Arrays.asList("user", "BG11UUAA22223333444455", "10")));
    }

    @Test
    public void testDisplayAccounts_ReturnsFalse_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        assertFalse(accounts.displayAccounts(new ArrayList<>()));
    }

    @Test
    public void testDisplayAccounts_ReturnsTrue_WhenGivenCorrectData()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        assertTrue(accounts.displayAccounts(Arrays.asList("user")));
    }

    @Test
    public void testSetAccountInterestRate_ReturnsFalse_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        assertFalse(accounts.setAccountInterestRate(Arrays.asList("BG11LLLL22223333444455")));
    }

    @Test
    public void testSetAccountInterestRate_ReturnsTrue_WhenGivenCorrectData()
            throws InvalidFieldException, ViolationException, NoSuchUserException, NoSuchAccountException {
        assertTrue(accounts.setAccountInterestRate(Arrays.asList("BG11LLLL22223333444455", "0.02")));
    }
}
