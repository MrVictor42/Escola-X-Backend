package io.github.mrvictor42.Escola.X.backend.repository

import io.github.mrvictor42.Escola.X.backend.model.generic.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository<T : User> : JpaRepository<T, Long> {
    fun findByUsername(username : String) : User
    fun existsByUsername(username: String) : Boolean
}