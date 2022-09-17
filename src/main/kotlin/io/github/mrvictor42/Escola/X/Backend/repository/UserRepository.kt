package io.github.mrvictor42.Escola.X.Backend.repository

import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username : String) : User
    fun existsByUsername(username: String) : Boolean
}