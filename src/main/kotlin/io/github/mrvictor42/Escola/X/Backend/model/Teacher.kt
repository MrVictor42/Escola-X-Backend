package io.github.mrvictor42.Escola.X.Backend.model

import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import org.hibernate.validator.constraints.br.CPF
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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