package io.github.mrvictor42.Escola.X.backend.model

import io.github.mrvictor42.Escola.X.backend.model.generic.User
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.validator.constraints.br.CPF
import javax.persistence.Entity

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Teacher(
    override val role : String = "ROLE_TEACHER",
    var registry : String,
    @CPF(message = "{cpf.invalid}")
    var cpf : String
) : User() {
    constructor() : this (
        "ROLE_TEACHER", "", "02951294174"
    )
}