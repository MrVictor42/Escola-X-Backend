package io.github.mrvictor42.Escola.X.backend.services

import io.github.mrvictor42.Escola.X.backend.exception.ObjectAlreadyExistsException
import io.github.mrvictor42.Escola.X.backend.model.Teacher
import io.github.mrvictor42.Escola.X.backend.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class TeacherService(
    private val userRepository: UserRepository<Teacher>,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    @Throws(ObjectAlreadyExistsException::class)
    fun save(teacher : Teacher) : Teacher {
        val exists : Boolean = userRepository.existsByUsername(teacher.username)

        if(exists) {
            throw ObjectAlreadyExistsException("O Professor ${ teacher.username } Já Foi Cadastrado!")
        } else {
            teacher.password = passwordEncoder.encode(teacher.password)

            return userRepository.save(teacher)
        }
    }
}