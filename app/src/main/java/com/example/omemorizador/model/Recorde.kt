package com.example.omemorizador.model

import java.io.Serializable

class Recorde: Serializable {

    var id: Int
    var nome: String
    var tempo: Int

    constructor(nome: String, tempo: Int) {
        this.id = -1
        this.nome = nome
        this.tempo = tempo
    }

    constructor(id: Int, nome: String, tempo: Int) {
        this.id = id
        this.nome = nome
        this.tempo = tempo
    }

    override fun toString(): String {
        return "${this.nome}: ${this.tempo} Segundos"
    }

}