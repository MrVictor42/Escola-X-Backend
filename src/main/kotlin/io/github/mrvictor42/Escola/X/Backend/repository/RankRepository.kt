package io.github.mrvictor42.Escola.X.Backend.repository

import io.github.mrvictor42.Escola.X.Backend.model.Rank
import org.springframework.data.jpa.repository.JpaRepository

interface RankRepository : JpaRepository<Rank, Long> {

}