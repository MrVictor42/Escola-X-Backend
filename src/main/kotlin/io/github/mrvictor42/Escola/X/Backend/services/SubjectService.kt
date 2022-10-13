package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.ObjectAlreadyExistsException
import io.github.mrvictor42.Escola.X.Backend.model.Subject
import io.github.mrvictor42.Escola.X.Backend.repository.SubjectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SubjectService(private val subjectRepository: SubjectRepository) {

    @Throws(ObjectAlreadyExistsException::class)
    fun save(subject: Subject) : Subject {
        val exists : Boolean = subjectRepository.existsByName(subject.name)

        if(exists) {
            throw ObjectAlreadyExistsException("A Matéria ${ subject.name } Já Foi Cadastrada!")
        } else {
            return subjectRepository.save(subject)
        }
    }
}