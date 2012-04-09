package org.analogweb.spring;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.analogweb.ModulesBuilder;
import org.junit.Before;
import org.junit.Test;

public class SpringPluginModulesConfigTest {

    private SpringPluginModulesConfig config;
    private ModulesBuilder modulesBuilder;

    @Before
    public void setUp() throws Exception {
        config = new SpringPluginModulesConfig();
        modulesBuilder = mock(ModulesBuilder.class);
    }

    @Test
    public void testPrepare() {

        when(modulesBuilder.setInvocationInstanceProviderClass(SpringContainerAdaptorFactory.class))
                .thenReturn(modulesBuilder);

        ModulesBuilder actual = config.prepare(modulesBuilder);
        assertSame(actual, modulesBuilder);

        verify(modulesBuilder).setInvocationInstanceProviderClass(
                SpringContainerAdaptorFactory.class);
    }

}
