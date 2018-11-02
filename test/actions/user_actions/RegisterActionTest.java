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

public class RegisterActionTest {
    Profile userProfile = mock(Profile.class);
    UserAction registerAction = new RegisterAction(userProfile);
    UsersInformation users = mock(UsersInformation.class);
    InputScanner scanner = mock(InputScanner.class);
    List<String> regularUserData = Arrays.asList("UNREGISTERED", "user", "user66Us", "user66Us", "user@abv.bg",
            "CLERK");
    List<String> fromAdminUserData = Arrays.asList("ADMIN", "user", "user66Us", "user66Us", "user@abv.bg", "CLERK");

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenUserStatusIsNotAdminAndHaveLineArguments() {
        when(userProfile.getUsername()).thenReturn("user");
        when(userProfile.getStatus()).thenReturn(Status.UNREGISTERED);

        assertEquals(regularUserData,
                registerAction.receiveArguments(scanner, "user user66Us user66Us user@abv.bg CLERK"));
    }

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenUserStatusIsNotAdminAndHaveArgumentsFromScanner() {
        when(userProfile.getUsername()).thenReturn("user");
        when(userProfile.getStatus()).thenReturn(Status.UNREGISTERED);
        when(scanner.nextLine()).thenReturn("user", "user66Us", "user66Us", "user@abv.bg", "CLERK");

        assertEquals(regularUserData, registerAction.receiveArguments(scanner, null));
    }

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenUserStatusIsAdminAndHaveLineArguments() {
        when(userProfile.getUsername()).thenReturn("user");
        when(userProfile.getStatus()).thenReturn(Status.ADMIN);

        assertEquals(fromAdminUserData,
                registerAction.receiveArguments(scanner, "user user66Us user66Us user@abv.bg CLERK"));
    }

    @Test
    public void testReceiveArguments_ReturnsFilledArray_WhenGivenUserStatusIsAdminAndHaveArgumentsFromScanner() {
        when(userProfile.getUsername()).thenReturn("user");
        when(userProfile.getStatus()).thenReturn(Status.ADMIN);
        when(scanner.nextLine()).thenReturn("user", "user66Us", "user66Us", "user@abv.bg", "CLERK");

        assertEquals(fromAdminUserData, registerAction.receiveArguments(scanner, null));
    }

    @Test(expected = UserAlreadyLoggedException.class)
    public void testExecute_ReturnsUserAlreadyLoggedException_WhenGivenUserStatusIsClient()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        registerAction.execute(null, Status.CLIENT, users);
    }

    @Test(expected = UserAlreadyLoggedException.class)
    public void testExecute_ReturnsUserAlreadyLoggedException_WhenGivenUserStatusIsClerk()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        registerAction.execute(null, Status.CLERK, users);

    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsUnregisteredAndRegisterFailed()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        when(users.registerUser(null)).thenReturn(false);

        assertFalse(registerAction.execute(null, Status.UNREGISTERED, users));

    }

    @Test
    public void testExecute_ReturnsFalse_WhenGivenUserStatusIsAdminAndRegisterFailed()
            throws InvalidFieldException, UserAlreadyLoggedException, NoSuchUserException, NotLoggedException {
        when(users.registerUser(null)).thenReturn(false);

        assertFalse(registerAction.execute(null, Status.ADMIN, users));

    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsUnregisteredAndRegisterSucceeded()
            throws UserAlreadyLoggedException, InvalidFieldException, NoSuchUserException, NotLoggedException {
        when(users.registerUser(null)).thenReturn(true);

        registerAction.execute(null, Status.UNREGISTERED, users);
    }

    @Test
    public void testExecute_ReturnsTrue_WhenGivenUserStatusIsAdminAndRegisterSucceeded()
            throws UserAlreadyLoggedException, IndexOutOfBoundsException, InvalidFieldException, NoSuchUserException,
            NotLoggedException {
        when(users.registerUser(null)).thenReturn(true);
        registerAction.execute(null, Status.ADMIN, users);
    }
}
