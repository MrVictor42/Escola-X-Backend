package io.mrvictor42.escolax.service

import io.mrvictor42.escolax.model.Role
import io.mrvictor42.escolax.repository.RoleRepository

class RoleService(private val roleRepository: RoleRepository) {

    fun save(role : Role) : Role {
        return roleRepository.save(role)
    }
}