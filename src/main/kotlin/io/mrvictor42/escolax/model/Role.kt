package io.mrvictor42.escolax.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id : Long = 0
    private var name : String = ""
}