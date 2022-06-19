package io.mrvictor42.escolax.model.dto

import lombok.Data

@Data
data class RoleUserDTO(
    val username : String,
    val password : String
)
