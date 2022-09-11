package io.github.mrvictor42.Escola.X.backend.services

import io.github.mrvictor42.Escola.X.backend.exception.ObjectAlreadyExistsException
import io.github.mrvictor42.Escola.X.backend.model.Student
import io.github.mrvictor42.Escola.X.backend.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class StudentService(
    private val userRepository: UserRepository<Student>,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    @Throws(ObjectAlreadyExistsException::class)
    fun save(student : Student) : Student {
        val exists : Boolean = userRepository.existsByUsername(student.username)

        if(exists) {
            throw ObjectAlreadyExistsException("O Estudante ${ student.username } Já Foi Cadastrado!")
        } else {
            student.password = passwordEncoder.encode(student.password)

            return userRepository.save(student)
        }
    }
}