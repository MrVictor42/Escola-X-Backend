package io.github.mrvictor42.Escola.X.Backend.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long?,
    @Column(unique = true, nullable = false)
    @get: NotEmpty(message = "{role.required}")
    val name : String
)