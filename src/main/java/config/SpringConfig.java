package config;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
@PropertySource("classpath:folderPaths.properties")
@ComponentScan(basePackages = {"app", "utils"})
public class SpringConfig {

}
