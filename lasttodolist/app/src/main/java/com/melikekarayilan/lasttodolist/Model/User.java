package com.melikekarayilan.lasttodolist.Model;

/**
 * Created by MehmetAliATLI on 8.10.2019.
 */
public class User {

    public static final String USER_TABLE = "user";
    public static final String USER_ID = "userId";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userpassword";

    private int id;
    private String email;
    private String password;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + USER_TABLE + "("
                    + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + USER_EMAIL + " TEXT,"
                    + USER_PASSWORD + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public User() {

    }

    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
