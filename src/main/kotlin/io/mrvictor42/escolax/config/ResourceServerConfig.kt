package io.mrvictor42.escolax.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter

@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        val listAdmin : List<String> = listOf("/api/user/**", "/api/role/**")
        val listUser : List<String> = listOf("/api/user/user_list")

        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(listAdmin.toString()).hasAnyRole("ROLE_ADMIN").anyRequest().authenticated()
    }
}