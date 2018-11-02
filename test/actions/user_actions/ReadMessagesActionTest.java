package actions.user_actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;

import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class ReadMessagesActionTest {
    Profile userProfile = mock(Profile.class);
    UserAction readMessagesAction = new ReadMessagesAction(userProfile);
    UsersInformation users = mock(UsersInformation.class);

    @Test(expected = NotLoggedException.class)
    public void testExecute_ReturnsNotLoggedException_WhenGivenUserStatusIsUnregistered()
            throws NotLoggedException, NoSuchUserException, InvalidFieldException, UserAlreadyLoggedException {
        readMessagesAction.execute(null, Status.UNREGISTERED, users);
    }

    @Test
    public void testExecute_ReturnsFalse_WhenReadMessagesFailed()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(users.readMessages(null)).thenReturn(false);

        assertFalse(readMessagesAction.execute(null, Status.CLIENT, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClient()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(userProfile.getUsername()).thenReturn("user");
        when(users.readMessages(Arrays.asList("user"))).thenReturn(true);

        assertTrue(readMessagesAction.execute(null, Status.CLIENT, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClerk()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(userProfile.getUsername()).thenReturn("user");
        when(users.readMessages(Arrays.asList("user"))).thenReturn(true);

        assertTrue(readMessagesAction.execute(null, Status.CLERK, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsAdmin()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(userProfile.getUsername()).thenReturn("user");
        when(users.readMessages(Arrays.asList("user"))).thenReturn(true);

        assertTrue(readMessagesAction.execute(null, Status.ADMIN, users));
    }

}
