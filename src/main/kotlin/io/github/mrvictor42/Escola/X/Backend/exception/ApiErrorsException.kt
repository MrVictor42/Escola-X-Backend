package io.github.mrvictor42.Escola.X.Backend.exception

class ApiErrorsException {
    var errors : List<String?> = ArrayList<String>()

    constructor(errors : List<String?>) {
        this.errors = errors
    }

    constructor(message : String) {
        errors = listOf(message)
    }
}