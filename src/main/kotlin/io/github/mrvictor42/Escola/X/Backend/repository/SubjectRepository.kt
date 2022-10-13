package io.github.mrvictor42.Escola.X.Backend.repository

import io.github.mrvictor42.Escola.X.Backend.model.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository : JpaRepository<Subject, Long> {
    fun existsByName(name: String) : Boolean
}