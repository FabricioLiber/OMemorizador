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


class GridTeachersAdapter: BaseAdapter {

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

        var foto = view.findViewById<ImageView>(R.id.list_teachers_imagem)

        Picasso.with(context)
            .load("https://res.cloudinary.com/deqmrmqui/image/upload/v1558056566/icognita_rvba83.jpg")
            .into(foto)

        return foto

    }

}