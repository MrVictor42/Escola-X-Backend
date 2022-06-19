package io.mrvictor42.escolax.service

import io.mrvictor42.escolax.exception.RoleFoundException
import io.mrvictor42.escolax.model.Role
import io.mrvictor42.escolax.repository.RoleRepository
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class RoleService(private val roleRepository: RoleRepository) {

    fun save(role : Role) : Role {
        val exists : Boolean = roleRepository.existsByName(role.name)

        if(exists) {
            throw RoleFoundException("Role Já Cadastrada")
        } else {
            return roleRepository.save(role)
        }
    }

    fun rolesList() : List<Role> {
        return roleRepository.findAll()
    }
}