package io.github.mrvictor42.Escola.X.Backend.model

class Admin(
    override val role : String = "ROLE_ADMIN"
) : User() {
    constructor() : this(
        "ROLE_ADMIN"
    )
}