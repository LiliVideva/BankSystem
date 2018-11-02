package actions.user_actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class SendMessagesActionTest {
    Profile userProfile = mock(Profile.class);
    UserAction sendMessageAction = new SendMessageAction(userProfile);
    UsersInformation users = mock(UsersInformation.class);
    InputScanner scanner = mock(InputScanner.class);
    List<String> messageData = Arrays.asList("user", "receiver", "This is a message.");

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenLineArguments() {
        when(userProfile.getUsername()).thenReturn("user");

        assertEquals(messageData, sendMessageAction.receiveArguments(scanner, "receiver This is a message."));
    }

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenArgumentsFromScanner() {
        when(userProfile.getUsername()).thenReturn("user");
        when(scanner.nextLine()).thenReturn("receiver", "This is a message.");

        assertEquals(messageData, sendMessageAction.receiveArguments(scanner, null));
    }

    @Test(expected = NotLoggedException.class)
    public void testExecute_ReturnsNotLoggedException_WhenGivenUserStatusIsUnregistered()
            throws NotLoggedException, NoSuchUserException, InvalidFieldException, UserAlreadyLoggedException {
        sendMessageAction.execute(null, Status.UNREGISTERED, users);
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsClientAndRegisterFailed()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        when(users.sendMessage(null)).thenReturn(false);

        assertFalse(sendMessageAction.execute(null, Status.CLIENT, users));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsClerkAndRegisterFailed()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        when(users.sendMessage(null)).thenReturn(false);

        assertFalse(sendMessageAction.execute(null, Status.CLERK, users));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsAdminAndRegisterFailed()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        when(users.sendMessage(null)).thenReturn(false);

        assertFalse(sendMessageAction.execute(null, Status.ADMIN, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClientAndSendingSucceeded()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        when(users.sendMessage(messageData)).thenReturn(true);

        assertTrue(sendMessageAction.execute(messageData, Status.CLIENT, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClerkAndSendingSucceeded()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        when(users.sendMessage(messageData)).thenReturn(true);

        assertTrue(sendMessageAction.execute(messageData, Status.CLERK, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsAdminAndSendingSucceeded()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        when(users.sendMessage(messageData)).thenReturn(true);

        assertTrue(sendMessageAction.execute(messageData, Status.ADMIN, users));
    }

}
