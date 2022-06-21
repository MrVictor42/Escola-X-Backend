package io.github.mrvictor42.Escola.X.Backend.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(authenticationManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter() {

    @Value("\${security.jwt.signing-key}")
    val secretKey : String? = null

    init {
        this.authenticationManager = authenticationManager
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val username : String = request.getParameter("username")
        val password : String = request.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)

        return authenticationManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authentication: Authentication?
    ) {
        val user : User = authentication?.principal as User
        val algorithm : Algorithm = Algorithm.HMAC256(secretKey.toString())
        val accessToken =
            JWT.create()
                .withSubject(user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request?.requestURL.toString())
                .withClaim("roles", user.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm)
        val refreshToken =
            JWT.create()
                .withSubject(user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request?.requestURL.toString())
                .withClaim("roles", user.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm)
        response?.setHeader("access_token", accessToken)
        response?.setHeader("refresh_token", refreshToken)
    }
}