package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.model.RankRoom
import io.github.mrvictor42.Escola.X.Backend.model.generic.Room
import io.github.mrvictor42.Escola.X.Backend.repository.RoomRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class RoomService(private val roomRepository: RoomRepository<Room>) {

    fun saveAll(roomList : List<Room>) : List<Room> {
        return roomRepository.saveAll(roomList)
    }
}