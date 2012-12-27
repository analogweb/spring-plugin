package org.analogweb.spring;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.analogweb.spring.pojo.Bar;
import org.analogweb.spring.pojo.Baz;
import org.analogweb.spring.pojo.Foo;
import org.analogweb.spring.pojo.FooImpl;
import org.analogweb.util.Maps;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * @author 0211.hk
 */
public class SpringContainerAdaptorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SpringContainerAdaptor adaptor;
    private ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = mock(ApplicationContext.class);
        adaptor = new SpringContainerAdaptor(context);
    }

    @Test
    public void testGetInstanceOfType() {
        when(context.getBean(FooImpl.class)).thenReturn(new FooImpl());
        when(context.getBean(Bar.class)).thenReturn(new Bar());
        Foo p = adaptor.getInstanceOfType(FooImpl.class);
        Bar c = adaptor.getInstanceOfType(Bar.class);
        assertTrue(p instanceof Foo);
        assertTrue(c instanceof Bar);
    }

    @Test
    public void testGetInstanceOfTypeWithNotBindingType() {
        thrown.expect(NoSuchBeanDefinitionException.class);
        when(context.getBean(Baz.class)).thenThrow(
                new NoSuchBeanDefinitionException("no such bean!"));
        adaptor.getInstanceOfType(Baz.class);
    }

    @Test
    public void testGetInstanceOfTypeWithoutApplicationContext() {
        // ApplicationContext will contains nothing.
        when(context.getBean(Foo.class)).thenReturn(null);
        when(context.getBean(Bar.class)).thenReturn(null);
        when(context.getBean(Baz.class)).thenReturn(null);

        assertNull(adaptor.getInstanceOfType(Foo.class));
        assertNull(adaptor.getInstanceOfType(Bar.class));
        assertNull(adaptor.getInstanceOfType(Baz.class));
    }

    @Test
    public void testGetInstancesOfType() {
        Map<String, Foo> fooInstances = Maps.newHashMap("foo", (Foo) new FooImpl());
        fooInstances.put("foo2", new FooImpl());
        fooInstances.put("foo3", new FooImpl());
        when(context.getBeansOfType(Foo.class)).thenReturn(fooInstances);
        List<Foo> foo = adaptor.getInstancesOfType(Foo.class);

        assertThat(foo.size(), is(3));
        assertTrue(foo.get(0) instanceof Foo);
        assertTrue(foo.get(1) instanceof Foo);
        assertTrue(foo.get(2) instanceof Foo);
    }

}
