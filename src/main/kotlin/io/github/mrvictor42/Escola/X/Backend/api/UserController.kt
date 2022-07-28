package io.github.mrvictor42.Escola.X.Backend.api

import io.github.mrvictor42.Escola.X.Backend.model.User
import io.github.mrvictor42.Escola.X.Backend.services.UserService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.servlet.http.Part
import javax.validation.Valid

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController(private val userService : UserService) {

    @PostMapping("/save")
    fun save(@Valid @RequestBody user : User, @RequestParam("avatar") avatar: Part?) : ResponseEntity<User> {
        return try {
            val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString())
            ResponseEntity.created(uri).body(userService.save(user, avatar))
        } catch (runtime : RuntimeException) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/list")
    fun getUserList() : ResponseEntity<List<User>> {
        return ResponseEntity.ok().body(userService.userList())
    }

    @GetMapping("/current_user")
    fun getCurrentUser(@RequestParam("username") username : String?) : ResponseEntity<User> {
        return try {
            ResponseEntity.ok().body(userService.getCurrentUser(username!!))
        } catch (runtime : RuntimeException) {
            ResponseEntity.badRequest().body(null)
        }
    }
}