package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.model.User
import io.github.mrvictor42.Escola.X.Backend.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class UserService(private val userRepository: UserRepository) : UserDetailsService {

    fun countUser() : Long {
        return userRepository.count()
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userExists : Boolean = userRepository.existsByUsername(username)

        if(userExists) {
            val user : User = userRepository.findByUsername(username)
            val authorities : MutableList<SimpleGrantedAuthority> = mutableListOf()

            authorities.add(SimpleGrantedAuthority(user.permission))

            return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
        } else {
            throw UsernameNotFoundException("Usuário Não Encontrado")
        }
    }
}