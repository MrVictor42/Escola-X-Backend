package io.github.mrvictor42.Escola.X.Backend.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Arrays.stream
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter : OncePerRequestFilter() {

    @Value("{security.jwt.signing-key}")
    val secretKey : String? = null

    @Throws(ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        if(request.servletPath == "/login") {
            filterChain.doFilter(request, response)
        } else {
            val authorizationHeader : String = request.getHeader(AUTHORIZATION)

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    val token : String = authorizationHeader.substring("Bearer ".length)
                    val algorithm : Algorithm = Algorithm.HMAC256("bWluaGEgZmFtaWxpYSDDqSB0dWRv")
                    val verifier : JWTVerifier = JWT.require(algorithm).build()
                    val decodeJWT : DecodedJWT = verifier.verify(token)
                    val username : String = decodeJWT.subject
                    val roles : Array<String> = decodeJWT.getClaim("roles").asArray(String::class.java)
                    val authorities : MutableList<SimpleGrantedAuthority> = mutableListOf()

                    stream(roles).forEach { role ->
                        authorities.add(SimpleGrantedAuthority(role))
                    }

                    val authenticationToken = UsernamePasswordAuthenticationToken(username, null, authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (exception : Exception) {
                    response.setHeader("error", exception.message)
                    response.status = FORBIDDEN.value()
                    val error : MutableMap<String, String> = HashMap()

                    error["error_message"] = exception.message.toString()
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, error)
                }
            }
        }
    }
}