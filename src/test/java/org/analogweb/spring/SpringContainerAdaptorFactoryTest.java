package org.analogweb.spring;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.analogweb.core.AssertionFailureException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.ApplicationContext;

/**
 * @author 0211.hk
 */
public class SpringContainerAdaptorFactoryTest {

    private SpringContainerAdaptorFactory factory;
    private org.analogweb.ApplicationContext resolver;
    private ApplicationContext app;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = new SpringContainerAdaptorFactory();
        resolver = mock(org.analogweb.ApplicationContext.class);
        app = mock(ApplicationContext.class);
    }

    @Test
    public void testCreateContainerAdaptor() {
        when(
                resolver.getAttribute(ApplicationContext.class,
                        SpringContainerAdaptorFactory.ROOT_APPLICATION_CONTEXT_KEY))
                .thenReturn(app);
        SpringContainerAdaptor containerAdaptor = factory.createContainerAdaptor(resolver);
        assertThat(containerAdaptor, is(notNullValue()));
        SpringContainerAdaptor other = factory.createContainerAdaptor(resolver);
        assertThat(containerAdaptor, is(not(sameInstance(other))));
    }

    @Test
    public void testCreateContainerAdaptorWithoutContext() {
        when(
                resolver.getAttribute(ApplicationContext.class,
                        SpringContainerAdaptorFactory.ROOT_APPLICATION_CONTEXT_KEY)).thenReturn(
                null);
        SpringContainerAdaptor containerAdaptor = factory.createContainerAdaptor(resolver);
        assertThat(containerAdaptor, is(nullValue()));
    }

    @Test
    public void testCreateContainerAdaptorWithNullServletContext() {
        thrown.expect(AssertionFailureException.class);
        factory.createContainerAdaptor(null);
    }

}
