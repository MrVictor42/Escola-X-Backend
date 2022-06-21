package io.github.mrvictor42.Escola.X.Backend.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import java.util.*

@Configuration
class InternationalizationConfig {

    @Bean
    fun messageSource() : MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()

        messageSource.setBasename("classpath:messages")
        messageSource.setDefaultEncoding("ISO-8859-1")
        messageSource.setDefaultLocale(Locale.getDefault())

        return messageSource
    }

    @Bean
    fun validatorFactoryBean() : LocalValidatorFactoryBean {
        val bean = LocalValidatorFactoryBean()

        bean.setValidationMessageSource(messageSource())

        return bean
    }
}