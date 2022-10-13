package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.AvatarException
import io.github.mrvictor42.Escola.X.Backend.exception.ObjectAlreadyExistsException
import io.github.mrvictor42.Escola.X.Backend.exception.UserNotFoundException
import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import io.github.mrvictor42.Escola.X.Backend.repository.UserRepository
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.util.*
import javax.servlet.http.Part

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    @Throws(ObjectAlreadyExistsException::class)
    fun save(user: User, photo: Part?) : User {
        val exists : Boolean = userRepository.existsByUsername(user.username)

        if(exists) {
            throw ObjectAlreadyExistsException("O Usuário ${ user.username } Já Foi Cadastrado!")
        } else {
            if(photo != null) {
                try {
                    val inputStream = photo.inputStream
                    val bytes = ByteArray(photo.size.toInt())
                    IOUtils.readFully(inputStream, bytes)
                    user.photo = bytes
                    user.password = passwordEncoder.encode(user.password)

                    return userRepository.save(user)
                } catch (e: IOException) {
                    throw AvatarException("Não Foi Possível Salvar a Imagem de Usuário")
                }
            } else {
                user.password = passwordEncoder.encode(user.password)

                if(user.role == "" || user.role == null) {
                    user.role = "ROLE_USER"
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
        } else {
            throw UserNotFoundException("Usuário Não Encontrado")
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

    fun changePasswordAuthenticated(userID : Long, password : String) {
        val user : Optional<User> = userRepository.findById(userID)

        if(user.isPresent) {
            user.map { currentUser ->
                currentUser.password = passwordEncoder.encode(password)
                userRepository.save(currentUser)
            }
        } else {
            throw UserNotFoundException("Usuário Não Encontrado")
        }
    }

    fun countUser() : Long {
        return userRepository.count()
    }

    fun userList() : List<User> {
        return userRepository.findAll()
    }

    @Throws(ObjectAlreadyExistsException::class)
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
}