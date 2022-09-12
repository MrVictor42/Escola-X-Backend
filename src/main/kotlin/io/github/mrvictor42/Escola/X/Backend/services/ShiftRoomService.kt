package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.model.ShiftRoom
import io.github.mrvictor42.Escola.X.Backend.repository.RoomRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class ShiftRoomService(private val roomRepository: RoomRepository<ShiftRoom>) {

    fun saveAll(shiftRoomList: MutableList<ShiftRoom>) : List<ShiftRoom> {
        return roomRepository.saveAll(shiftRoomList)
    }
}