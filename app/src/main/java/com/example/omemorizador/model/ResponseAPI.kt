package com.example.omemorizador.model

import com.example.omemorizador.model.Teacher

class ResponseAPI {

    var count: Int
    var next: String
    var previous: String
    var results: List<Teacher>

    constructor(count: Int, next: String, previous: String, results: List<Teacher>) {
        this.count = count
        this.next = next
        this.previous = previous
        this.results = results
    }
}