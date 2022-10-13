package io.github.mrvictor42.Escola.X.Backend.model

import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import javax.persistence.Entity

@Entity
class Admin : User() {
    override var role : String? = "ROLE_ADMIN"
}