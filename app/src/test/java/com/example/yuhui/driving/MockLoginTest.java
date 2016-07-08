package com.example.yuhui.driving;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static junit.framework.Assert.assertEquals;


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
        //指定mock对象的某个方法，让它返回特定值
        Mockito.when(passwordValidator.verifyPassword(Mockito.anyString())).thenReturn(true);
        mockTest.login("xiaochuang", "xiaochuang password");

        //使用校验函数verify时，参数如果有模糊匹配则所有参数都要用模糊匹配
        Mockito.verify(userManager, Mockito.times(1)).performLogin(Matchers.anyString(),
                Matchers.eq("xiaochuang password"));

        //指定一个方法执行特定的动作，这个功能一般是用在目标的方法是void类型的时候
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                //callback是第三个参数
                MockTest.NetworkCallback callback = (MockTest.NetworkCallback) arguments[2];
                callback.onFailure(500, "Server error");
                return 500;
            }
        }).when(userManager).performLogin(Matchers.anyString(), Matchers.anyString(), Matchers.any(MockTest.NetworkCallback.class));

        //spy
        MockTest.PasswordValidator spy = Mockito.spy(MockTest.PasswordValidator.class);
        mockTest.setPasswordValidator(spy);
        mockTest.login("xiaochuang", "xiaochuang password");
        assertEquals(true, spy.verifyPassword("xiaochuang password"));
        Mockito.verify(userManager, Mockito.times(2)).performLogin(Matchers.anyString(),
                Matchers.eq("xiaochuang password"));
    }


}