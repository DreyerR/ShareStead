package za.co.technetic.ss.translator.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import za.co.technetic.ss.repo.config.RepositoryConfig;

@Import({RepositoryConfig.class})
@ComponentScan({
        "za.co.technetic.ss.translator"
})
@Configuration
public class TranslatorConfig {
}
