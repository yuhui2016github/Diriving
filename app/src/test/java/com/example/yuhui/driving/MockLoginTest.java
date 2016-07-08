package com.example.yuhui.driving;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import org.mockito.Matchers;


/**
 * Created by yuhui on 2016-7-8.
 */
public class MockLoginTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testLogin() throws Exception {
        MockTest.UserManager userManager = Mockito.mock(MockTest.UserManager.class);
        MockTest mockTest = new MockTest();
        mockTest.setUserManager(userManager);
        MockTest.PasswordValidator passwordValidator = Mockito.mock(MockTest.PasswordValidator.class);
        mockTest.setPasswordValidator(passwordValidator);
        Mockito.when(passwordValidator.verifyPassword(Mockito.anyString())).thenReturn(true);
        mockTest.login("xiaochuang", "xiaochuang password");
        //使用校验函数verify时，参数如果有模糊匹配则所有参数都要用模糊匹配
        Mockito.verify(userManager, Mockito.times(1)).performLogin(Mockito.anyString(), Matchers.eq("xiaochuang password"));
    }


}