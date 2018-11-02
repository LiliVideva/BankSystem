package actions.user_actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import users.Profile;
import users.UsersInformation;
import users.Status;

public class LogoutActionTest {
    Profile userProfile = mock(Profile.class);
    UserAction logoutAction = new LogoutAction(userProfile);
    UsersInformation users = mock(UsersInformation.class);

    @Test(expected = NotLoggedException.class)
    public void testExecute_ReturnsNotLoggedException_WhenGivenUserStatusIsUnregistered()
            throws NotLoggedException, InvalidFieldException, NoSuchUserException, UserAlreadyLoggedException {
        logoutAction.execute(null, Status.UNREGISTERED, users);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClient()
            throws NotLoggedException, InvalidFieldException, NoSuchUserException, UserAlreadyLoggedException {
        assertTrue(logoutAction.execute(null, Status.CLIENT, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsClerk()
            throws NotLoggedException, InvalidFieldException, NoSuchUserException, UserAlreadyLoggedException {
        assertTrue(logoutAction.execute(null, Status.CLERK, users));
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsAdmin()
            throws NotLoggedException, InvalidFieldException, NoSuchUserException, UserAlreadyLoggedException {
        assertTrue(logoutAction.execute(null, Status.ADMIN, users));
    }
}
