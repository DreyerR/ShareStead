package za.co.technetic.ss.logic.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import za.co.technetic.ss.translator.config.TranslatorConfig;

@Import({TranslatorConfig.class})
@Configuration
@ComponentScan(basePackages = {
        "za.co.technetic.ss.logic.flow",
        "za.co.technetic.ss.logic.service"
})
public class LogicConfig {
}
