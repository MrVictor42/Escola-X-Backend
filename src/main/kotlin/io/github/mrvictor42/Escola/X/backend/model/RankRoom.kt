package io.github.mrvictor42.Escola.X.backend.model

import io.github.mrvictor42.Escola.X.backend.model.generic.Room
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.Entity


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class RankRoom : Room() {

}