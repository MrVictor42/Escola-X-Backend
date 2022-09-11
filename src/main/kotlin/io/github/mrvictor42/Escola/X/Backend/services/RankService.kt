package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.ObjectAlreadyExistsException
import io.github.mrvictor42.Escola.X.Backend.model.RankRoom
import io.github.mrvictor42.Escola.X.Backend.repository.RankRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class RankService(private val rankRepository: RankRepository) {

    @Throws(ObjectAlreadyExistsException::class)
    fun save(rankRoom: RankRoom) : RankRoom {
        val exists : Boolean = rankRepository.existsByName(rankRoom.name)

        if(exists) {
            throw ObjectAlreadyExistsException("O ${ rankRoom.name } Já Foi Cadastrado!")
        } else {
            return rankRepository.save(rankRoom)
        }
    }

    fun saveAll(rankList : List<RankRoom>) : List<RankRoom> {
        return rankRepository.saveAll(rankList)
    }
}