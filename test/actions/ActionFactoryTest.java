package actions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import accounts.AccountsDatabase;
import actions.user_actions.DeleteUserAction;
import actions.user_actions.GetUserAction;
import actions.user_actions.LoginAction;
import actions.user_actions.LogoutAction;
import actions.user_actions.ReadMessagesAction;
import actions.user_actions.RegisterAction;
import actions.user_actions.SendMessageAction;
import bank.InputScanner;
import exceptions.InvalidFieldException;
import exceptions.NoSuchAccountException;
import exceptions.NoSuchUserException;
import exceptions.NotLoggedException;
import exceptions.UserAlreadyLoggedException;
import exceptions.ViolationException;
import users.Profile;
import users.UsersInformation;

public class ActionFactoryTest {
    InputScanner scanner = mock(InputScanner.class);
    Profile userProfile = mock(Profile.class);
    UsersInformation users = mock(UsersInformation.class);
    AccountsDatabase accounts = mock(AccountsDatabase.class);
    ActionFactory actionFactory = new ActionFactory(initializeActionsList(), userProfile);

    private Map<String, Action> initializeActionsList() {
        Map<String, Action> actionsList = new LinkedHashMap<>();
        actionsList.put("signUp", new RegisterAction(userProfile));
        actionsList.put("signIn", new LoginAction(userProfile));
        actionsList.put("deleteMyProfile", new DeleteUserAction(userProfile));
        actionsList.put("show", new GetUserAction(userProfile));
        actionsList.put("writeMessage", new SendMessageAction(userProfile));
        actionsList.put("seeAllNewMessages", new ReadMessagesAction(userProfile));
        actionsList.put("logout", new LogoutAction(userProfile));

        return actionsList;
    }

    @Test
    public void testProcessAction_ReturnsFalse_WhenGivenExitAction() {
        when(scanner.nextLine()).thenReturn("exit");

        assertFalse(actionFactory.processAction(scanner, users, accounts));
    }

    @Test
    public void testProcessAction_ReturnsTrue_WhenGivenActionDespiteIfExistingOrNot()
            throws InvalidFieldException, NoSuchUserException, NotLoggedException, UserAlreadyLoggedException,
            ViolationException, NoSuchAccountException {
        when(scanner.nextLine()).thenReturn("sign");

        assertTrue(actionFactory.processAction(scanner, users, accounts));
    }
}
