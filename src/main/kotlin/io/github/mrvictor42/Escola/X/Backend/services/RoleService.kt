package io.github.mrvictor42.Escola.X.Backend.services

import io.github.mrvictor42.Escola.X.Backend.exception.RoleAlreadyRegisteredException
import io.github.mrvictor42.Escola.X.Backend.model.Role
import io.github.mrvictor42.Escola.X.Backend.repository.RoleRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class RoleService(private val roleRepository: RoleRepository) {

    fun save(role : Role) : Role {
        val exists : Boolean = roleRepository.existsByName(role.name)

        if(exists) {
            throw RoleAlreadyRegisteredException("Role Já Cadastrada")
        } else {
            return roleRepository.save(role)
        }
    }

    fun rolesList() : List<Role> {
        return roleRepository.findAll()
    }
}