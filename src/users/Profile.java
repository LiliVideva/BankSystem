package users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;
    private Map<String, List<String>> messagesInbox;

    Profile(User user) {
        this.user = user;
        messagesInbox = new LinkedHashMap<>();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((messagesInbox == null) ? 0 : messagesInbox.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Profile other = (Profile) obj;
        return ((user == null && other.user == null) || user.equals(other.user));
    }

    public String getUsername() {
        return user.getUsername();
    }

    public Status getStatus() {
        return user.getStatus();
    }

    boolean receiveMessage(String sender, String message) {
        List<String> messagesFromSender = messagesInbox.get(sender);

        if (messagesFromSender == null) {
            messagesFromSender = new ArrayList<>();
        }
        messagesFromSender.add(message);
        messagesInbox.put(sender, messagesFromSender);
        return true;
    }

    int unreadMessagesCount() {
        return messagesInbox.values().stream().mapToInt(List::size).sum();
    }

    void printMessagesInbox() {
        messagesInbox.entrySet().stream().forEach(messagesFromUser -> {
            System.out.println("Sender: " + messagesFromUser.getKey());
            messagesFromUser.getValue().stream().forEach(message -> System.out.println("Message: " + message));
        });
        messagesInbox = new LinkedHashMap<>();
    }

    void printUserDetails() {
        user.printUserDetails();
    }

    User getUser() {
        return user;
    }

    Map<String, List<String>> getMessagesInbox() {
        return messagesInbox;
    }

}
