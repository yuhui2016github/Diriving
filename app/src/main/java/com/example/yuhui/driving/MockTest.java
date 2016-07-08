package com.example.yuhui.driving;

/**
 * Created by yuhui on 2016-7-8.
 */
public class MockTest {
    private UserManager mUserManager = new UserManager();
    private PasswordValidator mPasswordValidator = new PasswordValidator();

    public void login(String username, String password) {
        if (username == null || username.length() == 0) return;
        if (! mPasswordValidator.verifyPassword(password)) return;
            mUserManager.performLogin(username, password);

    }

    public void setUserManager(UserManager mUserManager) {
        this.mUserManager = mUserManager;
    }

    public void setPasswordValidator(PasswordValidator mPasswordValidator) {
        this.mPasswordValidator = mPasswordValidator;
    }

    static class UserManager {
        public void performLogin(String username, String password) {
            System.out.print(username + password);
        }
    }
    static class PasswordValidator {
        public boolean verifyPassword(String password){
            return true;
        }
    }
}
