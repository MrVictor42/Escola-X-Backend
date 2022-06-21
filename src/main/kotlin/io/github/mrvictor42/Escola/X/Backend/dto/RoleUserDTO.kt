package io.github.mrvictor42.Escola.X.Backend.dto

import lombok.Data

@Data
data class RoleUserDTO(
    val username : String,
    val password : String
)