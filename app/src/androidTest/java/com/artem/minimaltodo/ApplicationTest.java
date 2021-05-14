package com.artem.minimaltodo;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AbstractTest {

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(5000);
        fail("Fake fail!!!");
    }
}