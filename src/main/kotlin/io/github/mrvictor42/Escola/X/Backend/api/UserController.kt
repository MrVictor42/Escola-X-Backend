package io.github.mrvictor42.Escola.X.Backend.api

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.mrvictor42.Escola.X.Backend.dto.RoleUserDTO
import io.github.mrvictor42.Escola.X.Backend.model.User
import io.github.mrvictor42.Escola.X.Backend.services.UserService
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.Arrays
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import kotlin.collections.HashMap

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController(private val userService : UserService) {

    @Value("{security.jwt.signing-key}")
    val secretKey : String? = null

    @PostMapping("/save")
    fun save(@Valid @RequestBody user : User) : ResponseEntity<User> {
        return try {
            val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString())
            ResponseEntity.created(uri).body(userService.save(user))
        } catch (runtime : RuntimeException) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/list")
    fun getUserList() : ResponseEntity<List<User>> {
        return ResponseEntity.ok().body(userService.userList())
    }

    @PostMapping("/add_role_to_user")
    fun addRoleToUser(@RequestBody roleUserDTO: RoleUserDTO) : ResponseEntity<Unit> {
        userService.addRoleToUser(roleUserDTO.username, roleUserDTO.password)
        return ResponseEntity.ok().build()
    }
}