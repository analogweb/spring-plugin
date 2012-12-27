package org.analogweb.spring;

import org.analogweb.ApplicationContextResolver;
import org.analogweb.ContainerAdaptorFactory;
import org.analogweb.util.Assertion;
import org.springframework.context.ApplicationContext;

/**
 * @author 0211.hk
 */
public class SpringContainerAdaptorFactory implements
        ContainerAdaptorFactory<SpringContainerAdaptor> {

    static final String ROOT_APPLICATION_CONTEXT_KEY = "org.springframework.web.context.WebApplicationContext.ROOT";

    @Override
    public SpringContainerAdaptor createContainerAdaptor(ApplicationContextResolver resolver) {
        Assertion.notNull(resolver, ApplicationContextResolver.class.getName());
        ApplicationContext app = resolver.resolve(ApplicationContext.class,
                ROOT_APPLICATION_CONTEXT_KEY);
        if (app == null) {
            return null;
        }
        return new SpringContainerAdaptor(app);
    }

}
