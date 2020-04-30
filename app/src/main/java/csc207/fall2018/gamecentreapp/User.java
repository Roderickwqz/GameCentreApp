package csc207.fall2018.gamecentreapp;


/**
 * A class representing the players of games.
 */
public class User {
    /**
     * The name of the user.
     */
    private String userName;
    /**
     * The password of the user.
     */
    private String password;

    /**
     * A constructor of the user.
     *
     * @param userName the name of a user
     * @param password the password of a user.
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Return password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password according to input.
     *
     * @param password a string which can be represented as the password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the userName according to input.
     *
     * @param userName a string which is the name of the user.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String toString() {
        return getUserName();
    }
}
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return getUserName().equals(user.getUserName());
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(getUserName());
//    }
//}
