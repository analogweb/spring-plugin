package org.analogweb.spring;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;

import org.analogweb.ContainerAdaptor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author 0211.hk
 */
public class SpringContainerAdaptor implements ContainerAdaptor {

    private final ServletContext servletContext;
    private WebApplicationContext webApplicationContext;

    public SpringContainerAdaptor(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public <T> T getInstanceOfType(final Class<T> type) {
        WebApplicationContext webApplicationContext = getWebApplicationContext();
        if (webApplicationContext != null) {
            return webApplicationContext.getBean(type);
        }
        return null;
    }

    @Override
    public <T> List<T> getInstancesOfType(final Class<T> type) {
        WebApplicationContext webApplicationContext = getWebApplicationContext();
        if (webApplicationContext == null) {
            return Collections.emptyList();
        }
        Map<String, T> m = webApplicationContext.getBeansOfType(type);
        return new CopyOnWriteArrayList<T>(m.values());
    }

    protected WebApplicationContext getWebApplicationContext() {
        if (webApplicationContext == null) {
            webApplicationContext = WebApplicationContextUtils
                    .getWebApplicationContext(servletContext);
        }
        return webApplicationContext;
    }
}
