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
import java.util.Optional
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
                    user.photo = bytes
                    user.password = passwordEncoder.encode(user.password)

                    if(user.permission == "" || user.permission == null) {
                        user.permission = "ROLE_USER"
                    } else {
                        // Nothing to do
                    }

                    return userRepository.save(user)
                } catch (e: IOException) {
                    throw AvatarException("Não Foi Possível Salvar a Imagem de Usuário")
                }
            } else {
                user.password = passwordEncoder.encode(user.password)

                if(user.permission == "" || user.permission == null) {
                    user.permission = "ROLE_USER"
                } else {
                    // Nothing to do
                }

                return userRepository.save(user)
            }
        }
    }

    fun update(user: User) : User {
        val existsUser = userRepository.existsByUsername(user.username)

        if(existsUser) {
            return userRepository.save(user)
        } else {
            throw UserNotFoundException("Usuário Não Encontrado")
        }
    }

    @Throws(AvatarException::class)
    fun updateAvatar(userID : Long, photo: Part?) : Unit {
        val user: Optional<User> = userRepository.findById(userID)

        if (user.isPresent) {
            user.map { currentUser ->
                try {
                    if(photo == null) {
                        currentUser.photo = null
                    } else {
                        val inputStream = photo.inputStream
                        val bytes = ByteArray(photo.size.toInt())
                        IOUtils.readFully(inputStream, bytes)
                        currentUser.photo = bytes
                        userRepository.save(currentUser)
                        inputStream.close()
                    }
                } catch (error: IOException) {
                    throw AvatarException("Não Foi Possível Atualizar o Avatar do Usuário")
                }
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