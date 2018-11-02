package users;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;

public class UsersInformationTest {

    UsersInformation users = new UsersInformation("users.txt");
    List<String> registerData = new ArrayList<>(
            Arrays.asList("UNREGISTERED", "dummyUser", "user66User", "user66User", "user@gmail.com", "CLIENT"));

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidPasswordWithoutAtLeastOneDigit()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "userUser", "userUser", "user@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidPasswordWithoutAtLeastOneLowerCaseLetter()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "USER66US", "USER66US", "user@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidPasswordWithoutAtLeastOneUpperCaseLetter()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66us", "user66us", "user@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidPasswordWithoutAtLeastEightSigns()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user6Us", "user6Us", "user@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidEmailStartingWithUpperCaseLetter()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "User@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidEmailStartingWithDigit()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "6user@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidEmailStartingWithDash()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "-user@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidEmailStartingWithUnderscore()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "-user@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidEmailStartingWithAt()
            throws InvalidFieldException {
        users.registerUser(Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidEmailContainingPunctuationSign()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "us!er@gmail.com", "CLIENT"));
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "us.er@gmail.com", "CLIENT"));
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "us?er@gmail.com", "CLIENT"));
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "us,er@gmail.com", "CLIENT"));
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "us;er@gmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidEmailWithoutAt()
            throws InvalidFieldException {
        users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "usergmail.com", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidEmailWithEndingAt()
            throws InvalidFieldException {
        users.registerUser(Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "user@", "CLIENT"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testRegisterUser_ReturnsInvalidFieldException_WhenGivenInvalidEmailWithoutEnding()
            throws InvalidFieldException {
        users.registerUser(Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66Us", "user@gmail", "CLIENT"));
    }

    @Test
    public void testRegisterUser_ReturnsFalse_WhenGivenWrongNumberOfArguments() throws InvalidFieldException {
        assertFalse(
                users.registerUser(Arrays.asList("UNREGISTERED", "dummyUser", "userUser", "user@gmail.com", "CLIENT")));
    }

    @Test
    public void testRegisterUser_ReturnsFalse_WhenGivenDifferentPasswordAndRepeatPassword()
            throws InvalidFieldException {
        assertFalse(users.registerUser(
                Arrays.asList("UNREGISTERED", "dummyUser", "user66Us", "user66User", "user@gmail.com", "CLIENT")));
    }

    @Test
    public void testRegisterUser_ReturnsFalse_WhenGivenPresent() throws InvalidFieldException {
        assertTrue(users.registerUser(registerData));

        assertFalse(users.registerUser(registerData));
    }

    @Test
    public void testRegisterUser_ReturnsTrue_WhenGivenNewUserWithCorrectData() throws InvalidFieldException {
        assertTrue(users.registerUser(registerData));
    }

    @Test(expected = InvalidFieldException.class)
    public void testLoginUser_ReturnsInvalidFieldException_WhenGivenInvalidUsername() throws InvalidFieldException {
        assertTrue(users.registerUser(registerData));

        users.loginUser(Arrays.asList("userS", "user66User"));
    }

    @Test(expected = InvalidFieldException.class)
    public void testLoginUser_ReturnsInvalidFieldException_WhenGivenInvalidPassword() throws InvalidFieldException {
        assertTrue(users.registerUser(registerData));

        users.loginUser(Arrays.asList("dummyUser", "user66Users"));
    }

    @Test
    public void testLoginUser_ReturnsFalse_WhenGivenWrongNumberOfArguments() throws InvalidFieldException {
        assertTrue(users.registerUser(registerData));

        assertFalse(users.loginUser(Arrays.asList("dummyUser")));
    }

    @Test
    public void testLoginUser_ReturnsTrue_WhenGivenCorrectData() throws InvalidFieldException {
        assertTrue(users.registerUser(registerData));

        assertTrue(users.loginUser(Arrays.asList("dummyUser", "user66User")));
    }

    @Test(expected = NoSuchUserException.class)
    public void testDeleteUser_ReturnsNoSuchUserException_WhenGivenInvalidUsername()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        users.deleteUser(Arrays.asList("userS"));
    }

    @Test
    public void testDeleteUser_ReturnsFalse_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        assertFalse(users.deleteUser(new ArrayList<>()));
    }

    @Test
    public void testDeleteUser_ReturnsTrue_WhenGivenCorrectData() throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));
        assertTrue(users.deleteUser(Arrays.asList("dummyUser")));
    }

    @Test(expected = NoSuchUserException.class)
    public void testGetPersonalDetails_ReturnsNoSuchUserException_WhenGivenUsernameNotInTheSystem()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        users.getPersonalDetails(Arrays.asList("dummyUser", "otherUser"));
    }

    @Test
    public void testGetPersonalDetails_ReturnsNull_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        assertEquals(null, users.getPersonalDetails(Arrays.asList("dummyUser")));
    }

    @Test
    public void testGetPersonalDetails_ReturnsUserProfile_WhenGivenCorrectData()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        assertEquals("user", users.getPersonalDetails(Arrays.asList("user", "user")).getUsername());
        assertEquals(Status.ADMIN, users.getPersonalDetails(Arrays.asList("user", "user")).getStatus());
    }

    @Test
    public void testGetPersonalDetails_ReturnsUserProfile_WhenGivenAdminIsSearchingForOtherUserInfo()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));
        assertTrue(users.registerUser(
                Arrays.asList("UNREGISTERED", "otherUser", "user66User", "user66User", "user@gmail.com", "CLERK")));

        assertEquals("otherUser", users.getPersonalDetails(Arrays.asList("user", "otherUser")).getUsername());
        assertEquals(Status.CLERK, users.getPersonalDetails(Arrays.asList("user", "otherUser")).getStatus());
    }

    @Test
    public void testCheckStatus_ReturnsUnregistered_WhenGivenUsernameIsUnregistered()
            throws InvalidFieldException, NoSuchUserException {
        assertEquals(Status.UNREGISTERED, users.checkUserStatus("someUser"));
    }

    @Test
    public void testCheckStatus_ReturnsClient_WhenGivenUsernameIsAClient()
            throws InvalidFieldException, NoSuchUserException {
        assertEquals(Status.CLIENT, users.checkUserStatus("lili"));
    }

    @Test
    public void testCheckStatus_ReturnsClerk_WhenGivenUsernameIsAClerk()
            throws InvalidFieldException, NoSuchUserException {
        assertEquals(Status.CLERK, users.checkUserStatus("clerk"));
    }

    @Test
    public void testCheckStatus_ReturnsAdmin_WhenGivenUsernameIsAnAdmin()
            throws InvalidFieldException, NoSuchUserException {
        assertEquals(Status.ADMIN, users.checkUserStatus("user"));
    }

    @Test(expected = NoSuchUserException.class)
    public void testSendMessage_ReturnsNoSuchUserException_WhenGivenUsernameNotInTheSystem()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        users.sendMessage(Arrays.asList("dummyUser", "otherUser", "message"));
    }

    @Test
    public void testSendMessage_ReturnsFalse_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        assertFalse(users.sendMessage(Arrays.asList("dummyUser", "message")));
    }

    @Test
    public void testSendMessage_ReturnsTrue_WhenGivenCorrectData() throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));
        assertTrue(users.registerUser(
                Arrays.asList("UNREGISTERED", "otherUser", "user66User", "user66User", "user@gmail.com", "CLERK")));

        assertTrue(users.sendMessage(Arrays.asList("dummyUser", "otherUser", "message")));
    }

    @Test(expected = NoSuchUserException.class)
    public void testReadMessages_ReturnsNoSuchUserException_WhenGivenUsernameNotInTheSystem()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        users.readMessages(Arrays.asList("userS"));
    }

    @Test
    public void testReadMessages_ReturnsFalse_WhenGivenWrongNumberOfArguments()
            throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        assertFalse(users.readMessages(new ArrayList<>()));
    }

    @Test
    public void testReadMessages_ReturnsTrue_WhenGivenCorrectData() throws InvalidFieldException, NoSuchUserException {
        assertTrue(users.registerUser(registerData));

        assertTrue(users.readMessages(Arrays.asList("dummyUser")));
    }
}
