package io.inkstand.scribble.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Gerald Muecke on 26.11.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class CallStackTest {

    @Mock
    private ClassLoader classLoader;
    private ClassLoader origCtxCl;

    @Before
    public void setUp() throws Exception {
        this.origCtxCl = Thread.currentThread().getContextClassLoader();
    }

    @After
    public void tearDown() throws Exception {
        Thread.currentThread().setContextClassLoader(origCtxCl);
    }

    @Test
    public void testGetCallerClass_defaultClassLoader() throws Exception {

        //prepare

        //act
        Class<?> cls = new CallingClass().callingMethod();

        //assert
        assertEquals(CallStackTest.class, cls);
    }


    @Test
    public void testGetCallerClass_contextClassLoader() throws Exception {

        //prepare
        when(classLoader.loadClass(CallStackTest.class.getName())).thenReturn((Class)MockClass.class);
        Thread.currentThread().setContextClassLoader(classLoader);

        //act
        Class<?> cls = new CallingClass().callingMethod();

        //assert
        assertEquals(MockClass.class, cls);
    }

    /**
     * Placeholder for a class that wants to get the caller of the calling method.
     */
    public static class CallingClass {
        private Class<?> callingMethod() {

            return CallStack.getCallerClass();
        }
    }

    public static class MockClass extends CallStackTest {

    }
}