package org.analogweb.spring;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;

import javax.servlet.ServletContext;

import org.analogweb.exception.AssertionFailureException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author 0211.hk
 */
public class SpringContainerAdaptorFactoryTest {

    private SpringContainerAdaptorFactory factory;
    private ServletContext servletContext;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = new SpringContainerAdaptorFactory();
        servletContext = mock(ServletContext.class);
    }

    @Test
    public void testCreateContainerAdaptor() {
        SpringContainerAdaptor containerAdaptor = factory.createContainerAdaptor(servletContext);
        assertNotNull(containerAdaptor);
        SpringContainerAdaptor other = factory.createContainerAdaptor(servletContext);
        assertNotSame(containerAdaptor, other);
    }

    @Test
    public void testCreateContainerAdaptorWithNullServletContext() {
        thrown.expect(AssertionFailureException.class);
        factory.createContainerAdaptor(null);
    }

}
