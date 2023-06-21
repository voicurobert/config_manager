package ro.logis.config_manager.config;

import org.eclipse.rap.rwt.engine.RWTServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigManagerRwtServletConfiguration {

    @Bean
    public ServletRegistrationBean<RWTServlet> rwtServlet() {
        var bean = new ServletRegistrationBean<>(new RWTServlet());
        bean.addUrlMappings("/hello");
        return bean;
    }
}
