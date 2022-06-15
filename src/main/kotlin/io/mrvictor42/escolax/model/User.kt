package io.mrvictor42.escolax.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.br.CPF
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long,
    @Column(nullable = false, length = 120, unique = true)
    @get: NotEmpty(message = "{username.required}")
    val username : String,
    @Column(nullable = false)
    @get: NotEmpty(message = "{password.required}")
    @get: Length(min = 6, message = "{password.shortLength}")
    val password : String,
    @Column(nullable = false)
    @get: NotEmpty(message = "{name.required}")
    @get: Length(min = 3, message = "{name.shortLength}")
    val name : String,
    @Column(nullable = false, unique = true)
    @get: Email(message = "{email.invalid}")
    @get: NotBlank(message = "{email.required}")
    val email : String,
    @get: CPF(message = "{cpf.required}")
    @get: NotNull(message = "{cpf.invalid}")
    val cpf : String,
    @ManyToMany(fetch = FetchType.EAGER)
    val roles : MutableList<Role> = mutableListOf()
)