package com.itsm.frontend.test;

import com.itsm.frontend.session.PasswordEncryptor;
import org.junit.Assert;
import org.junit.Test;

public class PasswordEncryptorsTest {
    @Test
    public void test1() {
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
        String result = passwordEncryptor.encrypt("admin");
        System.out.println(result);
        Assert.assertNotNull(result);
    }
}
