package net.choge.myapp.api.config;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {
    @Bean
    public Filter tracingFilter() {
        return new AWSXRayServletFilter("myapp");
    }
}
