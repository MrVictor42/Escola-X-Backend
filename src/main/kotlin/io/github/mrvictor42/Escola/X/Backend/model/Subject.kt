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
class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id : Long,
    @Column(nullable = false, length = 120)
    @get: NotEmpty(message = "{name.subject.required}")
    var name : String,
)