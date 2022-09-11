package io.github.mrvictor42.Escola.X.Backend.repository

import io.github.mrvictor42.Escola.X.Backend.model.RankRoom
import org.springframework.data.jpa.repository.JpaRepository

interface RankRepository : JpaRepository<RankRoom, Long> {
    fun existsByName(name : String) : Boolean
}