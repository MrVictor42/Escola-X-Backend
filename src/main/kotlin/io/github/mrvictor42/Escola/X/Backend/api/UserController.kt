package io.github.mrvictor42.Escola.X.Backend.api

import io.github.mrvictor42.Escola.X.Backend.exception.UserNotFoundException
import io.github.mrvictor42.Escola.X.Backend.model.User
import io.github.mrvictor42.Escola.X.Backend.services.UserService
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

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

    @PutMapping("/update")
    fun updateUser(@Valid @RequestBody user : User) : ResponseEntity<User> {
        return try {
            ResponseEntity.ok().body(userService.update(user))
        } catch (runtimeException : RuntimeException) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @PutMapping("/update/{id}/photo")
    fun updatePhoto(@PathVariable id: Long, @RequestParam("photo") photo: Part?) : Unit {
        userService.updateAvatar(id, photo)
    }

    @PutMapping("/update/password/{id}/new_password")
    fun updatePasswordAuthenticated(@PathVariable id : Long, @RequestBody new_password : String) {
        try {
            ResponseEntity.ok().body(userService.changePasswordAuthenticated(id, new_password))
        } catch (runtimeException : RuntimeException) {
            ResponseEntity.badRequest().body(null)
            throw UserNotFoundException("Usuário Não Atualizado")
        }
    }
}