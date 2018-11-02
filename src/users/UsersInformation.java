package users;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import exceptions.InvalidFieldException;
import exceptions.NoSuchUserException;

public class UsersInformation implements Cloneable {
    private String database;
    private List<Profile> usersProfiles;
    private static Set<String> clerksUsernames;
    private static Set<String> adminsUsernames;

    public UsersInformation(String database) {
        this.database = database;
        usersProfiles = new ArrayList<>();
        clerksUsernames = new LinkedHashSet<>();
        adminsUsernames = new LinkedHashSet<>();
        restoreProfilesFromDatabase();
    }

    @Override
    public List<Profile> clone() {
        List<Profile> cloned = new ArrayList<>();

        for (Profile userProfile : usersProfiles) {
            User user = userProfile.getUser();
            cloned.add(new Profile(user.clone()));
        }
        return cloned;
    }

    public void writeProfilesInDatabase() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(database, false))) {
            objectOutputStream.writeObject(usersProfiles);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean registerUser(List<String> arguments) throws InvalidFieldException {
        if (validateArgumentsListSize(arguments, 6)) {
            Status currentUserStatus = Status.valueOf(arguments.get(0));
            String username = arguments.get(1);
            String password = arguments.get(2);
            String repeatedPassword = arguments.get(3);
            String email = arguments.get(4);
            Status status = Status.valueOf(arguments.get(5));

            if (status.equals(Status.CLERK)) {
                clerksUsernames.add(username);
            }
            if (status.equals(Status.ADMIN)) {
                if (currentUserStatus.equals(Status.ADMIN)) {
                    adminsUsernames.add(username);
                } else {
                    System.out.println("Can't register as admin!");
                    return false;
                }
            }

            if (checkArguments(username, password, repeatedPassword, email)) {
                Profile newUserProfile = new Profile(new User(username, password, email, status));

                try {
                    getPersonalDetails(Arrays.asList(username, username));
                } catch (NoSuchUserException e) {
                    usersProfiles.add(newUserProfile);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean loginUser(List<String> arguments) throws InvalidFieldException {
        if (validateArgumentsListSize(arguments, 2)) {
            String username = arguments.get(0);
            String password = arguments.get(1);

            if (usernameAlreadyInTheSystem(username)) {
                for (Profile userProfile : usersProfiles) {
                    User user = userProfile.getUser();

                    if (username.equals(user.getUsername())) {
                        if ((password.hashCode()) == user.getPassword()) {
                            System.out.printf("You have %d new message(s) from users!%n",
                                    userProfile.unreadMessagesCount());
                            return true;
                        }
                        throw new InvalidFieldException("Invalid password: " + password);
                    }
                }
            }
            throw new InvalidFieldException("Invalid username: " + username);
        }
        return false;
    }

    public boolean deleteUser(List<String> arguments) throws NoSuchUserException {
        if (validateArgumentsListSize(arguments, 1)) {
            String username = arguments.get(0);

            return usersProfiles.remove(findProfileByUsername(username));
        }
        return false;
    }

    public Profile getPersonalDetails(List<String> arguments) throws NoSuchUserException {
        if (validateArgumentsListSize(arguments, 2)) {
            String currentUserUsername = arguments.get(0);
            String argument = arguments.get(1);
            String checkedUserUsername = "";

            try {
                if (validatePassword(argument)) {
                    checkedUserUsername = currentUserUsername;
                }
            } catch (InvalidFieldException e) {
                checkedUserUsername = argument;
            }

            Profile currentUserProfile = findProfileByUsername(currentUserUsername);
            Profile checkedUserProfile = findProfileByUsername(checkedUserUsername);

            if (checkedUserProfile.equals(currentUserProfile)
                    || (currentUserProfile.getStatus().equals(Status.CLERK)
                            && checkedUserProfile.getStatus().equals(Status.CLIENT))
                    || currentUserProfile.getStatus().equals(Status.ADMIN)) {

                return checkedUserProfile;
            }
        }
        return null;
    }

    public void printPersonalDetails(List<String> arguments) throws NoSuchUserException, InvalidFieldException {
        Profile userProfile = getPersonalDetails(arguments);

        if (userProfile == null) {
            System.out.println("Access denied!");
        } else {
            userProfile.printUserDetails();
        }
    }

    public Status checkUserStatus(String username) {
        if (adminsUsernames.contains(username)) {
            return Status.ADMIN;
        } else if (clerksUsernames.contains(username)) {
            return Status.CLERK;
        } else {
            try {
                findProfileByUsername(username);
            } catch (NoSuchUserException e) {
                return Status.UNREGISTERED;
            }
        }
        return Status.CLIENT;
    }

    public boolean sendMessage(List<String> arguments) throws NoSuchUserException {
        if (validateArgumentsListSize(arguments, 3)) {
            String sender = arguments.get(0);
            String receiver = arguments.get(1);
            String message = arguments.get(2);

            Profile receiverProfile = findProfileByUsername(receiver);
            return receiverProfile.receiveMessage(sender, message);
        }
        return false;
    }

    public boolean readMessages(List<String> arguments) throws NoSuchUserException {
        if (validateArgumentsListSize(arguments, 1)) {
            String username = arguments.get(0);

            Profile userProfile = findProfileByUsername(username);
            userProfile.printMessagesInbox();
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void restoreProfilesFromDatabase() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(database))) {
            usersProfiles = (List<Profile>) objectInputStream.readObject();
            collectClerksAndAdminsUsernames();
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    private void collectClerksAndAdminsUsernames() {
        usersProfiles.stream().filter(x -> x.getStatus().equals(Status.ADMIN))
                .forEach(x -> adminsUsernames.add(x.getUsername()));
        usersProfiles.stream().filter(x -> x.getStatus().equals(Status.CLERK))
                .forEach(x -> clerksUsernames.add(x.getUsername()));
    }

    private boolean validateArgumentsListSize(List<String> arguments, int expectedSize) {
        return expectedSize == arguments.size();
    }

    private boolean checkArguments(String username, String password, String repeatedPassword, String email)
            throws InvalidFieldException {
        return (!usernameAlreadyInTheSystem(username) && validatePassword(password) && password.equals(repeatedPassword)
                && validateEmail(email));
    }

    private boolean usernameAlreadyInTheSystem(String username) {
        return usersProfiles.stream().map(Profile::getUsername).anyMatch(x -> x.equals(username));
    }

    private boolean validatePassword(String password) throws InvalidFieldException {
        if (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
            return true;
        }
        throw new InvalidFieldException("Invalid password format: " + password);
    }

    private boolean validateEmail(String email) throws InvalidFieldException {
        if (email.matches("^([a-z]+[A-Za-z0-9_-]*)@([a-z0-9]+\\.[a-z]+)$")) {
            return true;
        }
        throw new InvalidFieldException("Invalid email format: " + email);
    }

    private Profile findProfileByUsername(String username) throws NoSuchUserException {
        Optional<Profile> userProfile = usersProfiles.stream().filter(x -> x.getUsername().equals(username))
                .findFirst();

        if (userProfile.isPresent()) {
            return userProfile.get();
        }
        throw new NoSuchUserException("No such user: " + username);
    }

    List<Profile> getUsers() {
        return clone();
    }

}
