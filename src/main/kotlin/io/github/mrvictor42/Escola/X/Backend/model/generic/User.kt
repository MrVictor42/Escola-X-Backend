package io.github.mrvictor42.Escola.X.Backend.model.generic

import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Entity
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val registration : Long = 0
    @Column(nullable = false, length = 120, unique = true)
    @get: NotEmpty(message = "{username.required}")
    var username : String = ""
    @Column(nullable = false)
    @get: NotEmpty(message = "{password.required}")
    @get: Length(min = 6, message = "{password.shortLength}")
    var password : String = "password"
    @Column(nullable = false)
    @get: NotEmpty(message = "{name.required}")
    @get: Length(min = 3, message = "{name.shortLength}")
    var name : String = "name"
    @Column(nullable = false, unique = true)
    @get: Email(message = "{email.invalid}")
    @get: NotBlank(message = "{email.required}")
    var email : String = "email@email.com"
    @get: NotEmpty(message = "{phone.required}")
    var phone : String = ""
    @Lob
    var photo: ByteArray? = null
    open var role : String? = null
}