package com.example.yuhui.driving;

/**
 * Created by yuhui on 2016-7-8.
 */
public class MockTest {
    private UserManager mUserManager = new UserManager();
    private PasswordValidator mPasswordValidator = new PasswordValidator();

    public void login(String username, String password) {
        if (username == null || username.length() == 0) return;
        if (!mPasswordValidator.verifyPassword(password)) return;
        mUserManager.performLogin(username, password);
        mUserManager.performLogin(username, password, new NetworkCallback() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });

    }

    public void setUserManager(UserManager mUserManager) {
        this.mUserManager = mUserManager;
    }

    public void setPasswordValidator(PasswordValidator mPasswordValidator) {
        this.mPasswordValidator = mPasswordValidator;
    }

    static class UserManager {
        public void performLogin(String username, String password, NetworkCallback networkCallback) {
            System.out.print("username " + username);
        }

        public void performLogin(String username, String password) {

        }

    }

    static class PasswordValidator {
        public boolean verifyPassword(String password) {
            return "xiaochuang password".equals(password);
        }
    }

    interface NetworkCallback {
        public void onSuccess(Object data);

        public void onFailure(int code, String msg);
    }
}
