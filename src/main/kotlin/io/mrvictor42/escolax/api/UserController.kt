package io.mrvictor42.escolax.api

import io.mrvictor42.escolax.model.User
import io.mrvictor42.escolax.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
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
}