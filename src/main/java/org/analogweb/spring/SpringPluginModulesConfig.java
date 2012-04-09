package org.analogweb.spring;

import org.analogweb.ModulesBuilder;
import org.analogweb.PluginModulesConfig;
import org.analogweb.util.MessageResource;
import org.analogweb.util.PropertyResourceBundleMessageResource;
import org.analogweb.util.logging.Log;
import org.analogweb.util.logging.Logs;

/**
 * @author 0211.hk
 */
public class SpringPluginModulesConfig implements PluginModulesConfig {

    public static final MessageResource PLUGIN_MESSAGE_RESOURCE = new PropertyResourceBundleMessageResource(
            "org.analogweb.spring.analog-messages");
    private static final Log log = Logs.getLog(SpringPluginModulesConfig.class);

    @Override
    public ModulesBuilder prepare(ModulesBuilder builder) {
        log.log(PLUGIN_MESSAGE_RESOURCE, "ISB000001");
        builder.setInvocationInstanceProviderClass(SpringContainerAdaptorFactory.class);
        return builder;
    }

}
