package io.github.mrvictor42.Escola.X.Backend.model

import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class Admin : User() {

    override var role : String? = "ROLE_ADMIN"
}