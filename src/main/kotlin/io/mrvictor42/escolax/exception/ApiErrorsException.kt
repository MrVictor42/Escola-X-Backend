package io.mrvictor42.escolax.exception

class ApiErrorsException {
    var errors : List<String> = ArrayList<String>()

    constructor(errors : List<String>) {
        this.errors = errors
    }

    constructor(message : String) {
        errors = listOf(message)
    }
}