package io.mrvictor42.escolax.service

import io.mrvictor42.escolax.exception.UserAlreadyRegistered
import io.mrvictor42.escolax.exception.UserNotFoundException
import io.mrvictor42.escolax.model.Role
import io.mrvictor42.escolax.model.User
import io.mrvictor42.escolax.repository.RoleRepository
import io.mrvictor42.escolax.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.management.relation.RoleNotFoundException

@Service
@RequiredArgsConstructor
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    @Throws(UserAlreadyRegistered::class)
    fun save(user: User) : User {
        val exists : Boolean = userRepository.existsByUsername(user.username)

        if(exists) {
            throw UserAlreadyRegistered("O Usuário ${ user.username } Já Foi Cadastrado!")
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

        if(!userExists) {
            throw UsernameNotFoundException("Usuário Não Encontrado")
        } else {
            val user : User = userRepository.findByUsername(username)
            val authorities : MutableList<SimpleGrantedAuthority> = mutableListOf()

            user.roles.forEach { role ->
                authorities.add(SimpleGrantedAuthority(role.name))
            }

            return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
        }
    }

    fun countUser() : Long {
        return userRepository.count()
    }
}