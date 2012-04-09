package org.analogweb.spring;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.analogweb.spring.pojo.Bar;
import org.analogweb.spring.pojo.Baz;
import org.analogweb.spring.pojo.Foo;
import org.analogweb.spring.pojo.FooImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @author 0211.hk
 */
public class SpringContainerAdaptorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SpringContainerAdaptor adaptor;
    private final String RESOURCE_BASE_PATH = "src/test/resources";
    private final String CONFIG_LOCATION = "applicationContext.xml";
    private MockServletContext mockServletContext;

    @Before
    public void setUp() throws Exception {
        mockServletContext = new MockServletContext(RESOURCE_BASE_PATH,
                new FileSystemResourceLoader());
        mockServletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, CONFIG_LOCATION);
        ServletContextListener contextListener = new ContextLoaderListener();
        ServletContextEvent event = new ServletContextEvent(mockServletContext);
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocations(new String[] { CONFIG_LOCATION });
        context.setServletContext(mockServletContext);
        context.refresh();
        contextListener.contextInitialized(event);
        adaptor = new SpringContainerAdaptor(mockServletContext);
    }

    @Test
    public void testGetInstanceOfType() {
        Foo p = adaptor.getInstanceOfType(FooImpl.class);
        Bar c = adaptor.getInstanceOfType(Bar.class);
        assertTrue(p instanceof Foo);
        assertTrue(c instanceof Bar);
    }

    @Test
    public void testGetInstanceOfTypeWithNotBindingType() {
        thrown.expect(NoSuchBeanDefinitionException.class);
        adaptor.getInstanceOfType(Baz.class);
    }

    @Test
    public void testGetInstanceOfTypeWithoutApplicationContext() {
        ServletContext servletContext = mock(ServletContext.class);
        adaptor = new SpringContainerAdaptor(servletContext);
        // servletContext will contains nothing.
        assertNull(adaptor.getInstanceOfType(Foo.class));
        assertNull(adaptor.getInstanceOfType(Bar.class));
        assertNull(adaptor.getInstanceOfType(Baz.class));
    }

    @Test
    public void testGetInstancesOfTypeWithoutContext() {
        ServletContext servletContext = mock(ServletContext.class);
        adaptor = new SpringContainerAdaptor(servletContext);
        // servletContext will contains nothing.
        assertTrue(adaptor.getInstancesOfType(Foo.class).isEmpty());
        assertTrue(adaptor.getInstancesOfType(Bar.class).isEmpty());
        assertTrue(adaptor.getInstancesOfType(Baz.class).isEmpty());
    }

    @Test
    public void testGetInstancesOfType() {
        List<Foo> foo = adaptor.getInstancesOfType(Foo.class);

        assertThat(foo.size(), is(3));
        assertTrue(foo.get(0) instanceof Foo);
        assertTrue(foo.get(1) instanceof Foo);
        assertTrue(foo.get(2) instanceof Foo);
        assertFalse(foo.get(0).getClass().equals(foo.get(1).getClass()));
    }

}
