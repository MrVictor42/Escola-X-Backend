package io.github.mrvictor42.Escola.X.Backend.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Student (
    override val permission : String = "ROLE_STUDENT",
    val nameFather : String,
    val nameMother : String,
    val responsible : String,
    val birthDate : String
) : User()
