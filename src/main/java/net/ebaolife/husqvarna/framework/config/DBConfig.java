package net.ebaolife.husqvarna.framework.config;

/**
 * @ Author     ：Wu JianTao
 * @ Date       ：Created in 11:06 AM 2019/4/18
 * @ Description：${description}
 * @ Modified By：
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*指定配置文件名，默认从classpath下寻找该文件，也就是等同于classpath:dataSource.properties
 * 可以指定多个文件
 */
/*
 * 指定前缀，读取的配置信息项必须包含该前缀，且除了前缀外，剩余的字段必须和实体类的属性名相同，
 * 才能完成银映射
 */

@Configuration
@PropertySource(value = "application.properties")
@ConfigurationProperties(prefix = "spring.datasource")
public class DBConfig {
    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private String initialSize;

    private String minIdle;

    private String maxActive;

    private String maxWait;

    private String timeBetweenEvictionRunsMillis;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(String initialSize) {
        this.initialSize = initialSize;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(String minIdle) {
        this.minIdle = minIdle;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public String getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    public String getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(String timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }
}
