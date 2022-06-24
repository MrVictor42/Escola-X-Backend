package io.github.mrvictor42.Escola.X.Backend.api

import io.github.mrvictor42.Escola.X.Backend.dto.RoleUserDTO
import io.github.mrvictor42.Escola.X.Backend.model.User
import io.github.mrvictor42.Escola.X.Backend.services.UserService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController(private val userService : UserService) {

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