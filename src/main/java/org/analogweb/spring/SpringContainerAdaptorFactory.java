package org.analogweb.spring;

import org.analogweb.ApplicationContext;
import org.analogweb.ContainerAdaptorFactory;
import org.analogweb.util.Assertion;

/**
 * @author 0211.hk
 */
public class SpringContainerAdaptorFactory implements
        ContainerAdaptorFactory<SpringContainerAdaptor> {

    static final String ROOT_APPLICATION_CONTEXT_KEY = "org.springframework.web.context.WebApplicationContext.ROOT";

    @Override
    public SpringContainerAdaptor createContainerAdaptor(ApplicationContext resolver) {
        Assertion.notNull(resolver, ApplicationContext.class.getName());
        org.springframework.context.ApplicationContext app = resolver.getAttribute(
                org.springframework.context.ApplicationContext.class, ROOT_APPLICATION_CONTEXT_KEY);
        if (app == null) {
            return null;
        }
        return new SpringContainerAdaptor(app);
    }
}
