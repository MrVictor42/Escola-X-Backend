package io.github.mrvictor42.Escola.X.Backend.model

import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class Student : User() {
    override var role : String? = "ROLE_STUDENT"
    var nameFather : String = ""
    var nameMother : String = ""
    var responsible : String = ""
    var birthDate : String = ""
}
