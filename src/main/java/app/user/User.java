package app.user;

public class User {
    private int id;
    private String email;
    private String username;
    private String salt;
    private String hashedPassword;
    private int privilege;

    public User(int id, String email, String username, String salt, String hashedPassword,
                int privilege) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.privilege = privilege;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

}

