package users;

import java.io.Serializable;

class User implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private int password;
    private String email;
    private Status status;

    User(String username, String passwordString, String email, Status status) {
        this.username = username;
        this.password = passwordString.hashCode();
        this.email = email;
        this.status = status;
    }

    User(String username, int password, String email, Status status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + password;
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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

        User other = (User) obj;
        return (((email == null && other.email == null) || email.equals(other.email)) && (status == other.status)
                && ((username == null && other.username == null) || username.equals(other.username)));
    }

    @Override
    public User clone() {
        User cloned;
        try {
            cloned = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Can't clone user!");
        }

        cloned.username = this.username;
        cloned.password = this.password;
        cloned.email = this.email;
        cloned.status = this.status;

        return cloned;
    }

    void printUserDetails() {
        System.out.println("Username: " + username);
        System.out.println("Email:" + email);
        System.out.println("Status: " + status);
    }

    String getUsername() {
        return username;
    }

    int getPassword() {
        return password;
    }

    String getEmail() {
        return email;
    }

    Status getStatus() {
        return status;
    }

}