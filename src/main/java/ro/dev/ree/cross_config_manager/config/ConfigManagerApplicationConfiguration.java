package ro.dev.ree.cross_config_manager.config;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;
import org.springframework.stereotype.Component;
import ro.dev.ree.cross_config_manager.ConfigManagerRwtEntryPoint;

import java.util.HashMap;

@Component
public class ConfigManagerApplicationConfiguration implements ApplicationConfiguration {

    @Override
    public void configure(Application application) {
        // SWT_COMPATIBILITY mode is required for using blocking Dialog API. Required when using JFace.
        application.setOperationMode(Application.OperationMode.SWT_COMPATIBILITY);

        // Register entry points, resources, themes, service handlers etc....
        var properties = new HashMap<String, String>();
        properties.put(WebClient.PAGE_TITLE, "CROSS Config Manager");

        // RWT themes can be used as well.
        //application.addStyleSheet(RWT.DEFAULT_THEME_ID, "themes/customRwtTheme.css");

        // Using a static reference to the EntryPoint class here. Could also be an EntryPointFactory that works
        // with injected EntryPoint components at application startup.
        application.addEntryPoint("/ui", ConfigManagerRwtEntryPoint.class, properties);
    }
}
