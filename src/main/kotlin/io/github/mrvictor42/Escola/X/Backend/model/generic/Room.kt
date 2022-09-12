package io.github.mrvictor42.Escola.X.Backend.model.generic

import javax.persistence.*

@MappedSuperclass
open class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id : Long = 0
    @Column
    var name : String = ""
}