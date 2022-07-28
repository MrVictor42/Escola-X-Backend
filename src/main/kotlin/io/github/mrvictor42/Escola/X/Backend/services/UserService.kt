package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.AvatarException
import io.github.mrvictor42.Escola.X.Backend.exception.UserAlreadyRegisteredException
import io.github.mrvictor42.Escola.X.Backend.exception.UserNotFoundException
import io.github.mrvictor42.Escola.X.Backend.model.User
import io.github.mrvictor42.Escola.X.Backend.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import javax.management.relation.RoleNotFoundException
import javax.servlet.http.Part

@Service
@RequiredArgsConstructor
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    @Throws(UserAlreadyRegisteredException::class)
    fun save(user: User, avatar: Part?) : User {
        val exists : Boolean = userRepository.existsByUsername(user.username)

        if(exists) {
            throw UserAlreadyRegisteredException("O Usuário ${ user.username } Já Foi Cadastrado!")
        } else {
            if(avatar != null) {
                try {
                    val inputStream = avatar.inputStream
                    val bytes = ByteArray(avatar.size.toInt())
                    IOUtils.readFully(inputStream, bytes)
                    user.avatar = bytes
                    user.password = passwordEncoder.encode(user.password)

                    return userRepository.save(user)
                } catch (e: IOException) {
                    throw AvatarException("Não Foi Possível Salvar a Imagem de Usuário")
                }
            } else {
                user.password = passwordEncoder.encode(user.password)

                return userRepository.save(user)
            }
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

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userExists : Boolean = userRepository.existsByUsername(username)

        if(userExists) {
            val user : User = userRepository.findByUsername(username)
            val authorities : MutableList<SimpleGrantedAuthority> = mutableListOf()

            authorities.add(SimpleGrantedAuthority("ROLE_USER"))

            return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
        } else {
            throw UsernameNotFoundException("Usuário Não Encontrado")
        }
    }

    fun countUser() : Long {
        return userRepository.count()
    }
}