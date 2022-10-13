package io.github.mrvictor42.Escola.X.Backend.model

import io.github.mrvictor42.Escola.X.Backend.model.generic.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotEmpty

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class Teacher : User() {
    override var role : String? = "ROLE_TEACHER"
    @Column(unique = true)
    @get: NotEmpty(message = "{registry.required}")
    var registry : String = ""
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = true)
    val subject = Subject()
}