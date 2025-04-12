package ru.home_work.t1_starter.aspect.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "logger")
public class LogProperties {
    private String level;

    public void setLevel(String level) {
        if (level == null || level.length() == 0) {
            this.level = "INFO";
        } else {
            level = level.toUpperCase();
            this.level = level;
        }
    }
}
