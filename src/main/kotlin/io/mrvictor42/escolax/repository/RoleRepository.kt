package io.mrvictor42.escolax.repository

import io.mrvictor42.escolax.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: String): Role
    fun existsByName(name: String): Boolean
}