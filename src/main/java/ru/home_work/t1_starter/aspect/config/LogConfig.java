package ru.home_work.t1_starter.aspect.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.home_work.t1_starter.aspect.LogAspect;
import ru.home_work.t1_starter.aspect.props.LogProperties;


@Configuration
@EnableConfigurationProperties({LogProperties.class})
@ConditionalOnProperty(
        value = {"logger.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class LogConfig {
    private final LogProperties logProperties;

    @Bean
    public LogAspect logAspect(@Value("${logger.level}") String level) {
        if (level == null || level.length() == 0) {
            logProperties.setLevel("INFO");
        } else {
            level = level.toUpperCase();
            logProperties.setLevel(level);
        }
        return new LogAspect(logProperties.getLevel());
    }
}
