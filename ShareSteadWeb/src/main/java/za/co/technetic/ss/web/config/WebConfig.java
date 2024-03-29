package za.co.technetic.ss.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import za.co.technetic.ss.logic.config.LogicConfig;

@Import({LogicConfig.class})
@Configuration
@ComponentScan(basePackages = {
        "za.co.technetic.ss.web.controller",
        "za.co.technetic.ss.web.util",
        "za.co.technetic.ss.web.filter"
})
@PropertySource(value = "classpath:application.properties")
public class WebConfig {
}
