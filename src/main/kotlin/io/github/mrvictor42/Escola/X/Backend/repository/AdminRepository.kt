package io.github.mrvictor42.Escola.X.Backend.repository

import io.github.mrvictor42.Escola.X.Backend.model.Admin
import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
    fun findByUsername(username : String) : User
    fun existsByUsername(username: String) : Boolean
}