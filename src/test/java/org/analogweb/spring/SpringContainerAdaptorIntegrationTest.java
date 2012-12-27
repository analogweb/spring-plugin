package org.analogweb.spring;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.analogweb.ApplicationContextResolver;
import org.analogweb.core.ServletContextApplicationContextResolver;
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
public class SpringContainerAdaptorIntegrationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SpringContainerAdaptor adaptor;
    private SpringContainerAdaptorFactory factory;
    private final String RESOURCE_BASE_PATH = "src/test/resources";
    private final String CONFIG_LOCATION = "applicationContext.xml";
    private MockServletContext mockServletContext;

    @Before
    public void setUp() throws Exception {
        factory = new SpringContainerAdaptorFactory();
        mockServletContext = new MockServletContext(RESOURCE_BASE_PATH,
                new FileSystemResourceLoader());
        mockServletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, CONFIG_LOCATION);
        ServletContextListener contextListener = new ContextLoaderListener();
        ServletContextEvent event = new ServletContextEvent(mockServletContext);
        @SuppressWarnings("resource")
        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocations(new String[] { CONFIG_LOCATION });
        context.setServletContext(mockServletContext);
        context.refresh();
        contextListener.contextInitialized(event);
        ApplicationContextResolver resolver = new ServletContextApplicationContextResolver(
                mockServletContext);
        adaptor = factory.createContainerAdaptor(resolver);
    }

    @Test
    public void testGetInstanceOfType() {
        Foo p = adaptor.getInstanceOfType(FooImpl.class);
        Bar c = adaptor.getInstanceOfType(Bar.class);
        assertThat(p, is(instanceOf(Foo.class)));
        assertThat(c, is(instanceOf(Bar.class)));
    }

    @Test
    public void testGetInstanceOfTypeWithNotBindingType() {
        thrown.expect(NoSuchBeanDefinitionException.class);
        adaptor.getInstanceOfType(Baz.class);
    }

    @Test
    public void testGetInstanceOfTypeWithoutApplicationContext() {
        ServletContext servletContext = mock(ServletContext.class);
        ApplicationContextResolver resolver = new ServletContextApplicationContextResolver(
                servletContext);
        adaptor = factory.createContainerAdaptor(resolver);
        // servletContext will contains nothing.
        assertThat(adaptor, is(nullValue()));
    }

    @Test
    public void testGetInstancesOfType() {
        List<Foo> foo = adaptor.getInstancesOfType(Foo.class);

        assertThat(foo.size(), is(3));
        assertThat(foo.get(0), is(instanceOf(Foo.class)));
        assertThat(foo.get(1), is(instanceOf(Foo.class)));
        assertThat(foo.get(2), is(instanceOf(Foo.class)));
    }

}
