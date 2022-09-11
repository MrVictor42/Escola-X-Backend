package io.github.mrvictor42.Escola.X.backend.model.generic

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
open class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id : Long = 0
    @Column
    var name : String = ""
}