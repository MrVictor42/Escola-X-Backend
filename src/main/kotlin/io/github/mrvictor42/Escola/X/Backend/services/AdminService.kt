package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.ObjectAlreadyExistsException
import io.github.mrvictor42.Escola.X.Backend.model.Admin
import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import io.github.mrvictor42.Escola.X.Backend.repository.AdminRepository
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
class AdminService(
    private val adminRepository: AdminRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    @Throws(ObjectAlreadyExistsException::class)
    fun save(admin: Admin) : Admin {
        val exists : Boolean = adminRepository.existsByUsername(admin.username)

        if(exists) {
            throw ObjectAlreadyExistsException("O Usuário ${ admin.username } Já Foi Cadastrado!")
        } else {
            admin.password = passwordEncoder.encode(admin.password)

            return adminRepository.save(admin)
        }
    }

    fun countUser() : Long {
        return adminRepository.count()
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userExists : Boolean = adminRepository.existsByUsername(username)

        if(userExists) {
            val user : User = adminRepository.findByUsername(username)
            val authorities : MutableList<SimpleGrantedAuthority> = mutableListOf()

            authorities.add(SimpleGrantedAuthority(user.role))

            return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
        } else {
            throw UsernameNotFoundException("Usuário Não Encontrado")
        }
    }
}