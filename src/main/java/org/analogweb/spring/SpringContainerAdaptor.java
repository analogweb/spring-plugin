package org.analogweb.spring;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.analogweb.ContainerAdaptor;
import org.springframework.context.ApplicationContext;

/**
 * @author 0211.hk
 */
public class SpringContainerAdaptor implements ContainerAdaptor {

    private final ApplicationContext applicationContext;

    public SpringContainerAdaptor(final ApplicationContext context) {
        this.applicationContext = context;
    }

    @Override
    public <T> T getInstanceOfType(final Class<T> type) {
        ApplicationContext applicationContext = getApplicationContext();
        if (applicationContext != null) {
            return applicationContext.getBean(type);
        }
        return null;
    }

    @Override
    public <T> List<T> getInstancesOfType(final Class<T> type) {
        ApplicationContext applicationContext = getApplicationContext();
        if (applicationContext == null) {
            return Collections.emptyList();
        }
        Map<String, T> m = applicationContext.getBeansOfType(type);
        return new CopyOnWriteArrayList<T>(m.values());
    }

    protected ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

}
