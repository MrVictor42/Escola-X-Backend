package io.mrvictor42.escolax.service

import io.mrvictor42.escolax.model.User
import io.mrvictor42.escolax.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class UserService(private val userRepository: UserRepository) {

    fun save(user: User) : User {
        return userRepository.save(user)
    }

    fun userList() : List<User> {
        return userRepository.findAll()
    }
}