package io.github.mrvictor42.Escola.X.backend.services

import io.github.mrvictor42.Escola.X.backend.model.RankRoom
import io.github.mrvictor42.Escola.X.backend.repository.RoomRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class RankRoomService(private val roomRepository: RoomRepository<RankRoom>) {

    fun saveAll(rankList : List<RankRoom>) : List<RankRoom> {
        return roomRepository.saveAll(rankList)
    }
}