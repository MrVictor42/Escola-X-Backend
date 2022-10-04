package io.github.mrvictor42.Escola.X.Backend.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long = 0
}