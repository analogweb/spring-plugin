package org.analogweb.spring;

import javax.servlet.ServletContext;

import org.analogweb.ContainerAdaptorFactory;
import org.analogweb.util.Assertion;

/**
 * @author 0211.hk
 */
public class SpringContainerAdaptorFactory implements
        ContainerAdaptorFactory<SpringContainerAdaptor> {

    @Override
    public SpringContainerAdaptor createContainerAdaptor(ServletContext servletContext) {
        Assertion.notNull(servletContext, ServletContext.class.getName());
        return new SpringContainerAdaptor(servletContext);
    }

}
