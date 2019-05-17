package com.example.oagendador


import android.content.ContentValues
import android.content.Context
import com.example.omemorizador.model.Recorde
import java.util.*

class RecordeDAO {
    private lateinit var banco: JogoDBHelper
    private lateinit var fields:Array<String>

    constructor(context: Context){
        this.banco = JogoDBHelper(context)
        this.fields = arrayOf("id", "nome", "tempo")
    }

    // create
    fun create(recorde: Recorde){
        val cv = ContentValues()
        cv.put("nome", recorde.nome)
        cv.put("numero", recorde.tempo)
        this.banco.writableDatabase.insert("Recordes",null, cv)
    }

    // find All
    fun read(): ArrayList<Recorde>{
        val lista = ArrayList<Recorde>()

        val c = this.banco.readableDatabase.query("Recordes", this.fields, null, null, null, null, "tempo DESC")

        c.moveToFirst()

        if (c.count > 0){
            do {
                val id = c.getInt(c.getColumnIndex("id"))
                val nome = c.getString(c.getColumnIndex("nome"))
                val tempo = c.getInt(c.getColumnIndex("tempo"))
                lista.add(Recorde(id, nome, tempo))
            }while(c.moveToNext())
        }

        return lista
    }


    // find One
    fun read(id: Int): Recorde?{
        val where = "id = ?"
        val paramWhere = arrayOf(id.toString())

        val c = this.banco.readableDatabase.query(
            "Recordes",
            this.fields,
            where,
            paramWhere,
            null,
            null,
            null
        )
        c.moveToFirst()

        if (c.count > 0){
            val id = c.getInt(c.getColumnIndex("id"))
            val nome = c.getString(c.getColumnIndex("nome"))
            val tempo = c.getInt(c.getColumnIndex("tempo"))
            return Recorde(id, nome, tempo)
        }

        return null
    }

    // update
    fun update(recorde: Recorde){
        val where = "id = ?"
        val pwhere = arrayOf(recorde.id.toString())
        val cv = ContentValues()
        cv.put("id", recorde.id)
        cv.put("nome", recorde.nome)
        cv.put("tempo", recorde.tempo)

        this.banco.writableDatabase.update("recordes", cv, where, pwhere)
    }

    // delete
    fun delete(id: Int){
        val where = "id = ?"
        val pwhere = arrayOf(id.toString())

        this.banco.writableDatabase.delete("recordes", where, pwhere)
    }
}