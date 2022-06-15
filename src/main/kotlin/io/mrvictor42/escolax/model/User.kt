package io.mrvictor42.escolax.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id : Long = 0
    @Column(nullable = false, length = 120)
    private var username : String = ""
    private var password : String = ""
    private var name : String = ""
    private var email : String = ""
    private var cpf : String = ""
}