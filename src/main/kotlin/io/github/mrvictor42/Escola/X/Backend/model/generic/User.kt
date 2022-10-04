package io.github.mrvictor42.Escola.X.Backend.model.generic

import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@Entity
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val registration : Long = 0
    @Column(nullable = false, length = 50, unique = true)
    @get: NotEmpty(message = "{username.required}")
    var username : String = ""
    @Column(nullable = false)
    @get: NotEmpty(message = "{password.required}")
    @get: Length(min = 6, message = "{password.shortLength}")
    var password : String = "password"
    @Column(nullable = false)
    @get: Length(min = 3, message = "{name.shortLength}")
    @get: NotEmpty(message = "{name.required}")
    var name : String = ""
    @Column(nullable = false, unique = true)
    @get: Email(message = "{email.invalid}")
    @get: NotEmpty(message = "{email.required}")
    var email : String = ""
    @get: NotEmpty(message = "{phone.required}")
    var phone : String = ""
    @Lob
    var photo: ByteArray? = null
    open var role : String? = null
}