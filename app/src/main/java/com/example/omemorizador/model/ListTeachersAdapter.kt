package com.example.omemorizador.model

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.omemorizador.R
import com.squareup.picasso.Picasso


class ListTeachersAdapter: BaseAdapter {

    private var teachers: List<Teacher>
    private var activity: Activity
    private var context: Context

    constructor(activity: Activity, teachers: List<Teacher>, context: Context) {
        this.teachers = teachers
        this.activity = activity
        this.context = context
    }

    override fun getCount(): Int {
        return this.teachers.size
    }

    override fun getItem(position: Int): Any {
        return this.teachers.get(position)
    }


    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = activity.layoutInflater.inflate(R.layout.list_teachers, parent, false)
        val teacher: Teacher = this.teachers.get(position)

        var nome = view.findViewById<TextView>(R.id.list_teachers_nome)
        var fraseMarcante = view.findViewById<TextView>(R.id.list_teachers_frasemarcante)
        var foto = view.findViewById<ImageView>(R.id.list_teachers_imagem)

        nome.text = teacher.nome
        var frase = ""
        if (!teacher.frasemarcante.equals(""))
            frase = "\" ${teacher.frasemarcante} \""
        fraseMarcante.text = frase

        Picasso.with(context)
            .load(teacher.foto)
            .into(foto)
        return view

    }

}