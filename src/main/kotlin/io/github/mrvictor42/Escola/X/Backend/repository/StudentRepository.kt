package io.github.mrvictor42.Escola.X.Backend.repository

import io.github.mrvictor42.Escola.X.Backend.model.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Long> {
    fun findByUsername(username : String) : Student
    fun existsByUsername(username: String) : Boolean
}