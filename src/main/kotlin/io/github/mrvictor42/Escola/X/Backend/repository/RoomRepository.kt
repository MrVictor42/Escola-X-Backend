package io.github.mrvictor42.Escola.X.Backend.repository

import io.github.mrvictor42.Escola.X.Backend.model.generic.Room
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository<T : Room> : JpaRepository<T, Long> {

}