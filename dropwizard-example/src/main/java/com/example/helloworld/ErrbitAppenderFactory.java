package com.example.helloworld;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.Appender;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.dropwizard.logging.AbstractAppenderFactory;
import io.dropwizard.logging.async.AsyncAppenderFactory;
import io.dropwizard.logging.filter.LevelFilterFactory;
import io.dropwizard.logging.filter.ThresholdLevelFilterFactory;
import io.dropwizard.logging.layout.LayoutFactory;
import net.anthavio.airbrake.AirbrakeLogbackAppender;

import javax.validation.constraints.NotNull;

@JsonTypeName("errbit")
public class ErrbitAppenderFactory extends AbstractAppenderFactory {

    @NotNull
    private String url;

    @NotNull
    private String apiKey;

    @NotNull
    private String environment;

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty
    public String getApiKey() {
        return apiKey;
    }

    @JsonProperty
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @JsonProperty
    public String getEnvironment() {
        return environment;
    }

    @JsonProperty
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public Appender build(LoggerContext context, String applicationName, LayoutFactory layoutFactory, LevelFilterFactory levelFilterFactory, AsyncAppenderFactory asyncAppenderFactory) {
        final AirbrakeLogbackAppender appender = new AirbrakeLogbackAppender();
        appender.setName("errbit-appender");
        appender.setContext(context);

        appender.setUrl(url);
        appender.setApiKey(apiKey);
        appender.setEnv(environment);
        appender.setNotify(AirbrakeLogbackAppender.Notify.ALL);
        appender.addFilter(new ThresholdLevelFilterFactory().build(threshold));

        appender.start();

        return wrapAsync(appender, asyncAppenderFactory);
    }
}

