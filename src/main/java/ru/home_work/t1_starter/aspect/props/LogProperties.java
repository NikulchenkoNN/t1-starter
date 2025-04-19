package ru.home_work.t1_starter.aspect.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "logger")
public class LogProperties {
    private String level;
}
