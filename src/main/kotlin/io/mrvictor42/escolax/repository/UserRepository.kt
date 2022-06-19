package io.mrvictor42.escolax.repository

import io.mrvictor42.escolax.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User
    fun existsByUsername(username: String): Boolean
}