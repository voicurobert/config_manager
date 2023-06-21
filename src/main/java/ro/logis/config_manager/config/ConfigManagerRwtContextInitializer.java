package ro.logis.config_manager.config;


import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.application.ApplicationRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;


@Component
public class ConfigManagerRwtContextInitializer implements ServletContextInitializer {

    private final ApplicationConfiguration applicationConfiguration;
    private ApplicationRunner applicationRunner;

    @Autowired
    public ConfigManagerRwtContextInitializer(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        applicationRunner = new ApplicationRunner(applicationConfiguration, servletContext);
        applicationRunner.start();
    }

    @PreDestroy
    public void destroy() {
        if (applicationRunner != null) {
            try {
                applicationRunner.stop();
            } finally {
                applicationRunner = null;
            }
        }
    }
}
