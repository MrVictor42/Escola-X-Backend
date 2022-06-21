package io.github.mrvictor42.Escola.X.Backend.repository

import io.github.mrvictor42.Escola.X.Backend.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name : String) : Role
    fun existsByName(name: String) : Boolean
}