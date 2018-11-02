package actions.user_actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import users.Profile;
import users.Status;
import users.UsersInformation;

public class DeleteUserActionTest {
    Profile userProfile = mock(Profile.class);
    UserAction deleteUserAction = new DeleteUserAction(userProfile);
    UsersInformation users = mock(UsersInformation.class);
    List<String> userData = Arrays.asList("user");

    @Test(expected = NotLoggedException.class)
    public void testExecute_ReturnsNotLoggedException_WhenGivenUserStatusIsUnregistered()
            throws NotLoggedException, NoSuchUserException, InvalidFieldException, UserAlreadyLoggedException {
        deleteUserAction.execute(null, Status.UNREGISTERED, users);
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsClientAndDeleteFailed()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(userProfile.getUsername()).thenReturn("user");
        when(users.deleteUser(userData)).thenReturn(false);

        assertFalse(deleteUserAction.execute(userData, Status.CLIENT, users));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsClerkAndDeleteFailed()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(userProfile.getUsername()).thenReturn("user");
        when(users.deleteUser(userData)).thenReturn(false);

        assertFalse(deleteUserAction.execute(userData, Status.CLERK, users));
    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsAdminAndDeleteFailed()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(userProfile.getUsername()).thenReturn("user");
        when(users.deleteUser(userData)).thenReturn(false);

        assertFalse(deleteUserAction.execute(userData, Status.ADMIN, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClientAndDeleteSucceeded()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(userProfile.getUsername()).thenReturn("user");
        when(users.deleteUser(userData)).thenReturn(true);

        assertTrue(deleteUserAction.execute(userData, Status.CLIENT, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClerkAndDeleteSucceeded()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(userProfile.getUsername()).thenReturn("user");
        when(users.deleteUser(userData)).thenReturn(true);

        assertTrue(deleteUserAction.execute(userData, Status.CLERK, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsAdminAndDeleteSucceeded()
            throws NoSuchUserException, InvalidFieldException, NotLoggedException, UserAlreadyLoggedException {
        when(userProfile.getUsername()).thenReturn("user");
        when(users.deleteUser(userData)).thenReturn(true);

        assertTrue(deleteUserAction.execute(userData, Status.ADMIN, users));
    }
}
