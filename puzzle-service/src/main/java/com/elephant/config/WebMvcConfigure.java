package com.elephant.config;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * MVC配置
 *
 * @author chengwei.deng
 * @date 2022-12-10 22:00
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfigure implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    @Value("${spring.profiles.active:}")
    private String profileActive;

    @Value("${spring.cloud.config.profile:}")
    private String cloudProfileActive;

    private final List<String> excludes = Lists.newArrayList("local");

    @Override
    public void addInterceptors(@Nonnull final InterceptorRegistry registry) {

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/*/**")
                .excludePathPatterns("/chess/**")
                .excludePathPatterns("/chat/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/swagger-ui.html/**")
                .excludePathPatterns("/code.html")
                .excludePathPatterns("/codegen/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/null/api-docs", "/api-docs").setKeepQueryParams(true);
        registry.addRedirectViewController("/null/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
        registry.addRedirectViewController("/null/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/null/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/null/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
