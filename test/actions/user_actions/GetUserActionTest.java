package actions.user_actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Test;

import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import bank.InputScanner;
import users.UsersInformation;
import users.Profile;
import users.Status;

public class GetUserActionTest {
    Profile userProfile = mock(Profile.class);
    UserAction getUserAction = new GetUserAction(userProfile);
    UsersInformation users = mock(UsersInformation.class);
    InputScanner scanner = mock(InputScanner.class);

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenLineArguments() {
        when(userProfile.getUsername()).thenReturn("user");

        assertEquals(Arrays.asList("user", "user"), getUserAction.receiveArguments(scanner, "user"));
    }

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenArgumentsFromScanner() {
        when(userProfile.getUsername()).thenReturn("user");
        when(scanner.nextLine()).thenReturn("user");

        assertEquals(Arrays.asList("user", "user"), getUserAction.receiveArguments(scanner, null));
    }

    @Test(expected = NotLoggedException.class)
    public void testExecute_ReturnsNotLoggedException_WhenGivenUserStatusIsUnregistered()
            throws NotLoggedException, NoSuchUserException, InvalidFieldException, UserAlreadyLoggedException {
        getUserAction.execute(null, Status.UNREGISTERED, users);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClient()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        assertTrue(getUserAction.execute(null, Status.CLIENT, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClerk()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        assertTrue(getUserAction.execute(null, Status.CLERK, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsAdmin()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        assertTrue(getUserAction.execute(null, Status.ADMIN, users));
    }

}
