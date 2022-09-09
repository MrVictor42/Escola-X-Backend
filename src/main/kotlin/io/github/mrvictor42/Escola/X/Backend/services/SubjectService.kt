package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.SubjectAlreadyExists
import io.github.mrvictor42.Escola.X.Backend.model.Subject
import io.github.mrvictor42.Escola.X.Backend.repository.SubjectRepository

class SubjectService(private val subjectRepository: SubjectRepository) {

    @Throws(SubjectAlreadyExists::class)
    fun save(subject: Subject) : Subject {
        val exists : Boolean = subjectRepository.existsByName(subject.name)

        if(exists) {
            throw SubjectAlreadyExists("O Matéria ${ subject.name } Já Foi Cadastrada!")
        } else {
            return subjectRepository.save(subject)
        }
    }
}