package io.mrvictor42.escolax.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(val authenticationManager: AuthenticationManager) : AuthorizationServerConfigurerAdapter() {

    @Value("\${security.jwt.signing-key}")
    val secretKey : String? = null
    @Bean
    fun tokenStore() : TokenStore {
        return InMemoryTokenStore()
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
            .inMemory()
            .withClient("escolax-app")
            .secret(secretKey)
            .scopes("READ", "WRITE")
            .authorizedGrantTypes("password","refresh_token")
            .accessTokenValiditySeconds(1800)
    }
}