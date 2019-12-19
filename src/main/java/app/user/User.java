package app.user;

/**
 * User class for users.
 */
public class User {

    /**
     * Unique user's number.
     */
    private int id;

    /**
     * Unique user's email.
     */
    private String email;

    /**
     * User`s username.
     */
    private String username;

    /**
     * Unique salt.
     */
    private String salt;

    /**
     * User's password.
     */
    private String hashedPassword;

    /**
     * User's rights and opportunities.
     */
    private int privilege;

    /**
     * Constructor - creating new recipe with certain parameters.
     *
     * @param id - unique user's number.
     * @param email - unique user's email.
     * @param username - user`s username.
     * @param salt - unique salt.
     * @param hashedPassword - hashed user's password.
     * @param privilege - user's rights and opportunities.
     */
    public User(int id, String email, String username, String salt, String hashedPassword,
                int privilege) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.privilege = privilege;
    }

    /**
     * This method return the user's id.
     *
     * @return the user's id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method return the user's email.
     *
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method return the user's username.
     *
     * @return the user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method return the user's salt.
     *
     * @return the user's salt.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method return the user's hashed password.
     *
     * @return the user's hashed password.
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * This method return the user's level of opportunities.
     *
     * @return the user's privilege.
     */
    public int getPrivilege() {
        return privilege;
    }
}

