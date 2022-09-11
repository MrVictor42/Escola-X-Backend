package io.github.mrvictor42.Escola.X.backend.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class WebConfig {

    @Bean
    fun corsFilterFilterRegistrationBean(): FilterRegistrationBean<CorsFilter>? {
        val all = listOf("*")
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedOrigins = listOf("http://localhost:4200/")
        corsConfiguration.allowedHeaders = all
        corsConfiguration.allowedMethods = all
        corsConfiguration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        val corsFilter = CorsFilter(source)
        val filter = FilterRegistrationBean(corsFilter)
        filter.order = Ordered.HIGHEST_PRECEDENCE

        return filter
    }
}