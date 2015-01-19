package io.inkstand.scribble.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InjectionTest {

    private SimpleInjectionTarget injectionTarget;
    private String injectionValue;
    private Injection subject;

    @Before
    public void setUp() throws Exception {
        injectionTarget = new SimpleInjectionTarget();
        injectionValue = "testValue";
        subject = new Injection(injectionValue);
    }

    @Test
    public void testAsResource() throws Exception {
        assertNotNull(subject.asResource());
        // what happens with a resource subject is defined in the ResourceInjectionTest
    }

    @Test
    public void testAsConfigProperty() throws Exception {
        final ConfigPropertyInjection cpi = subject.asConfigProperty("myProperty");
        assertNotNull(cpi);
        // what happens with a configProperty subject is defined in the ConfigPropertyInjectionTest
    }

    @Test
    public void testIntoAll() throws Exception {
        // act
        subject.intoAll(injectionTarget);
        // assert
        assertEquals(injectionValue, injectionTarget.injectionTarget1);
        assertEquals(injectionValue, injectionTarget.injectionTarget2);
        assertNull(injectionTarget.injectionTarget3);
    }

    @Test
    public void testInto() throws Exception {
        // the test assumes the of the fields is always the same as defined in the class
        Assume.assumeTrue("injectionTarget1".equals(SimpleInjectionTarget.class.getDeclaredFields()[0].getName()));
        // only the first match should be injected
        // act
        subject.into(injectionTarget);
        // assert
        assertEquals(injectionValue, injectionTarget.injectionTarget1);
        assertNull(injectionTarget.injectionTarget2);
        assertNull(injectionTarget.injectionTarget3);
    }

    @Test
    public void testInto_nullValue() throws Exception {
        final Injection subject = new Injection(null);
        // act
        subject.into(injectionTarget);
        // assert
        // all fields should remain null while no exception occured
        assertNull(injectionTarget.injectionTarget1);
        assertNull(injectionTarget.injectionTarget2);
        assertNull(injectionTarget.injectionTarget3);
    }

    @Test
    public void testGetValue() throws Exception {
        assertEquals(injectionValue, subject.getValue());
    }

    @Test
    public void testIsMatching_nullValue_True() throws Exception {
        final Injection subject = new Injection(null);
        final Field field = SimpleInjectionTarget.class.getDeclaredField("injectionTarget3");
        assertTrue(subject.isMatching(field));
    }

    @Test
    public void testIsMatching_compatibleField_false() throws Exception {
        final Field field = SimpleInjectionTarget.class.getDeclaredField("injectionTarget1");
        assertTrue(subject.isMatching(field));
    }

    @Test
    public void testIsMatching_incompatibleField_false() throws Exception {
        final Field field = SimpleInjectionTarget.class.getDeclaredField("injectionTarget3");
        assertFalse(subject.isMatching(field));
    }

    static class SimpleInjectionTarget {

        String injectionTarget1;
        String injectionTarget2;
        Integer injectionTarget3;
    }
}
