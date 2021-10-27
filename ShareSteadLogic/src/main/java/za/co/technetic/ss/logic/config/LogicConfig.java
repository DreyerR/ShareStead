package za.co.technetic.ss.logic.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import za.co.technetic.ss.translator.config.TranslatorConfig;

@Import({TranslatorConfig.class})
@Component
@ComponentScan(basePackages = {
        "za.co.technetic.ss.logic.flow"
})
public class LogicConfig {
}
