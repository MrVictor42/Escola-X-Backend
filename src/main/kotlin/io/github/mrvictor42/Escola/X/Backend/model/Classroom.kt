package io.github.mrvictor42.Escola.X.Backend.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Classroom(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id : Long,
    @ManyToOne
    @JoinColumn(name = "rank_id", updatable = false)
    private val rank: Rank
)