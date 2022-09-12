package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.ObjectAlreadyExistsException
import io.github.mrvictor42.Escola.X.Backend.model.Student
import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import io.github.mrvictor42.Escola.X.Backend.repository.StudentRepository
import io.github.mrvictor42.Escola.X.Backend.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class StudentService(
    private val studentRepository: StudentRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    @Throws(ObjectAlreadyExistsException::class)
    fun save(student : Student) : Student {
        val exists : Boolean = studentRepository.existsByUsername(student.username)

        if(exists) {
            throw ObjectAlreadyExistsException("O Estudante ${ student.username } Já Foi Cadastrado!")
        } else {
            student.password = passwordEncoder.encode(student.password)

            return studentRepository.save(student)
        }
    }
}