package io.mrvictor42.escolax.api

import io.mrvictor42.escolax.model.Role
import io.mrvictor42.escolax.service.RoleService
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
@RequestMapping("/role")
class RoleController(private val roleService: RoleService) {

    @PostMapping("/save")
    fun save(@Valid @RequestBody role: Role) : ResponseEntity<Role> {
        return try {
            val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString())
            ResponseEntity.created(uri).body(roleService.save(role))
        } catch (runtime : RuntimeException) {
            ResponseEntity.badRequest().body(null)
        }
    }

    @GetMapping("/roles_list")
    fun getRolesList() : ResponseEntity<List<Role>> {
        return ResponseEntity.ok().body(roleService.rolesList())
    }
}