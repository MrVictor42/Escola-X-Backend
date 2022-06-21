package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.UserAlreadyRegisteredException
import io.github.mrvictor42.Escola.X.Backend.exception.UserNotFoundException
import io.github.mrvictor42.Escola.X.Backend.model.Role
import io.github.mrvictor42.Escola.X.Backend.model.User
import io.github.mrvictor42.Escola.X.Backend.repository.RoleRepository
import io.github.mrvictor42.Escola.X.Backend.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.management.relation.RoleNotFoundException

@Service
@RequiredArgsConstructor
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    @Throws(UserAlreadyRegisteredException::class)
    fun save(user: User) : User {
        val exists : Boolean = userRepository.existsByUsername(user.username)

        if(exists) {
            throw UserAlreadyRegisteredException("O Usuário ${ user.username } Já Foi Cadastrado!")
        } else {
            user.password = passwordEncoder.encode(user.password)
            return userRepository.save(user)
        }
    }

    @Throws(UserNotFoundException::class)
    fun getCurrentUser(username: String) : User {
        val exists : Boolean = userRepository.existsByUsername(username)

        if(exists) {
            return userRepository.findByUsername(username)
        } else {
            throw UserNotFoundException("Usuário Não Encontrado")
        }
    }

    fun userList() : List<User> {
        return userRepository.findAll()
    }

    @Throws(UserNotFoundException::class)
    fun addRoleToUser(username: String, roleName: String) {
        val userExists : Boolean = userRepository.existsByUsername(username)
        val roleExists : Boolean = roleRepository.existsByName(roleName)

        if(!userExists) {
            throw UserNotFoundException("Usuário Não Encontrado")
        }
        if(!roleExists) {
            throw RoleNotFoundException("Role/Função Não Encontrada")
        }

        val user: User = userRepository.findByUsername(username)
        val role: Role = roleRepository.findByName(roleName)

        user.roles.add(role)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userExists : Boolean = userRepository.existsByUsername(username)

        if(userExists) {
            val user : User = userRepository.findByUsername(username)
            val authorities : MutableList<SimpleGrantedAuthority> = mutableListOf()

            user.roles.forEach { role ->
                authorities.add(SimpleGrantedAuthority(role.name))
            }

            return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
        } else {
            throw UsernameNotFoundException("Usuário Não Encontrado")
        }
    }

    fun countUser() : Long {
        return userRepository.count()
    }
}