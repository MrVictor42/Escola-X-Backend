package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.ObjectAlreadyExistsException
import io.github.mrvictor42.Escola.X.Backend.model.Student
import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import io.github.mrvictor42.Escola.X.Backend.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class UserService(
    private val userRepository: UserRepository<User>,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    @Throws(ObjectAlreadyExistsException::class)
    fun save(user : User) : User {
        val exists : Boolean = userRepository.existsByUsername(user.username)

        if(exists) {
            throw ObjectAlreadyExistsException("O Usuário ${ user.username } Já Foi Cadastrado!")
        } else {
            user.password = passwordEncoder.encode(user.password)

            return userRepository.save(user)
        }
    }

    fun countUser() : Long {
        return userRepository.count()
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userExists : Boolean = userRepository.existsByUsername(username)

        if(userExists) {
            val user : User = userRepository.findByUsername(username)
            val authorities : MutableList<SimpleGrantedAuthority> = mutableListOf()

            authorities.add(SimpleGrantedAuthority(user.role))

            return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
        } else {
            throw UsernameNotFoundException("Usuário Não Encontrado")
        }
    }
}