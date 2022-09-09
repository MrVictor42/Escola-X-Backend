package io.github.mrvictor42.Escola.X.Backend.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Rank(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id : Long,
    @Column(nullable = false)
    val rank : Int,
    @OneToMany(mappedBy = "rank", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val classrooms : MutableList<Classroom> = mutableListOf()
)