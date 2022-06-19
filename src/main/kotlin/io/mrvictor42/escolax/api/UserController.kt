package io.mrvictor42.escolax.api

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import io.mrvictor42.escolax.model.Role
import io.mrvictor42.escolax.model.User
import io.mrvictor42.escolax.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.net.URI
import java.util.Date
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/save")
    fun save(@Valid @RequestBody user : User) : ResponseEntity<User> {
        return try {
            val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString())
            ResponseEntity.created(uri).body(userService.save(user))
        } catch (runtime : RuntimeException) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/user_list")
    fun getUserList() : ResponseEntity<List<User>> {
        return ResponseEntity.ok().body(userService.userList())
    }

    @GetMapping("/token/refresh")
    @Throws(IOException::class)
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                val refreshToken = authorizationHeader.substring("Bearer ".length)
                val algorithm: Algorithm = Algorithm.HMAC256("secret".toByteArray())
                val verifier: JWTVerifier = JWT.require(algorithm).build()
                val decodedJWT: DecodedJWT = verifier.verify(refreshToken)
                val username: String = decodedJWT.subject
                val user: User = userService.getCurrentUser(username)
                val accessToken: String = JWT.create()
                    .withSubject(user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.requestURL.toString())
                    .withClaim("roles", user.roles.stream().map(Role::name).collect(Collectors.toList()))
                    .sign(algorithm)
                val tokens: MutableMap<String, String> = HashMap()
                tokens["access_token"] = accessToken
                tokens["refresh_token"] = refreshToken
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, tokens)
            } catch (exception: Exception) {
                response.setHeader("error", exception.message)
                response.status = HttpStatus.FORBIDDEN.value()
                val error: MutableMap<String, String?> = HashMap()
                error["error_message"] = exception.message
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, error)
            }
        } else {
            throw RuntimeException("Refresh Token Perdido")
        }
    }
}