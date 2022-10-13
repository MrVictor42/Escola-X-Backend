package io.github.mrvictor42.Escola.X.Backend.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id : Long = 0
    @Column(unique = true)
    var name : String = ""
    @OneToMany
    val teachers : MutableList<Teacher> = mutableListOf()
}