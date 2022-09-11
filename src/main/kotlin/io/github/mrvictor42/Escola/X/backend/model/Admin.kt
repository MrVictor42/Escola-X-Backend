package io.github.mrvictor42.Escola.X.backend.model

import io.github.mrvictor42.Escola.X.backend.model.generic.User
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.Entity

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Admin : User() {

    override val role : String = "ROLE_ADMIN"
}