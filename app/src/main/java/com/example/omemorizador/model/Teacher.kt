package com.example.omemorizador.model


class Teacher: Comparable<Teacher>, Cloneable {

    var url: String
    var nome: String
    var frasemarcante: String
    var foto: String

    constructor(url: String, nome: String, frasemarcante: String, foto: String) {
        this.url = url
        this.nome = nome
        this.frasemarcante = frasemarcante
        this.foto = foto
    }

    override fun toString(): String {
       return "${this.nome} - ${this.frasemarcante}"
    }

    override fun compareTo(other: Teacher): Int {
        return this.nome.compareTo(other.nome)
    }

    override fun clone(): Teacher {
        return super.clone() as Teacher
    }

    fun clonePublic(): Teacher {
        return clone()
    }

}