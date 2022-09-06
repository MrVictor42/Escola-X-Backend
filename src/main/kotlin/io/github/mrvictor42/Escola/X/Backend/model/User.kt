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
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val registration : Long?,
    @Column(nullable = false, length = 120, unique = true)
    @get: NotEmpty(message = "{username.required}")
    var username : String,
    @Column(nullable = false)
    @get: NotEmpty(message = "{password.required}")
    @get: Length(min = 6, message = "{password.shortLength}")
    var password : String,
    @Column(nullable = false)
    @get: NotEmpty(message = "{name.required}")
    @get: Length(min = 3, message = "{name.shortLength}")
    var name : String,
    @Column(nullable = false, unique = true)
    @get: Email(message = "{email.invalid}")
    @get: NotBlank(message = "{email.required}")
    var email : String,
    @get: NotEmpty(message = "{phone.required}")
    var phone : String,
    @Lob
    var photo: ByteArray? = null,
    open val role : String? = null
) {
    constructor() : this(0, "", "", "", "", "", null, "")
}