package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.ObjectAlreadyExistsException
import io.github.mrvictor42.Escola.X.Backend.model.Teacher
import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import io.github.mrvictor42.Escola.X.Backend.repository.TeacherRepository
import io.github.mrvictor42.Escola.X.Backend.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class TeacherService(
    private val teacherRepository: TeacherRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    @Throws(ObjectAlreadyExistsException::class)
    fun save(teacher : Teacher) : Teacher {
        val exists : Boolean = teacherRepository.existsByUsername(teacher.username)

        if(exists) {
            throw ObjectAlreadyExistsException("O Professor ${ teacher.username } Já Foi Cadastrado!")
        } else {
            teacher.password = passwordEncoder.encode(teacher.password)

            return teacherRepository.save(teacher)
        }
    }
}