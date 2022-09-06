package io.github.mrvictor42.Escola.X.Backend.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Student (
    override val role : String = "ROLE_STUDENT",
    var nameFather : String,
    var nameMother : String,
    var responsible : String,
    var birthDate : String
) : User() {
    constructor() : this (
        "ROLE_STUDENT", "", "", "", ""
    )
}
