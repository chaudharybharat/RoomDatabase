package com.example.roomdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.db.Student

/**
 * Created by ThinkSoft on 20/12/2017.
 */
class StudentAdapter(student_list: ArrayList<Student>, listener: OnItemClickListener) : RecyclerView.Adapter<StudentAdapter.RecyclerViewHolder>() {




    private var listStudents: List<Student> = student_list

    private var listenerContact: OnItemClickListener = listener

    interface OnItemClickListener {
        fun onItemClick(student: Student)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.row_student_list, parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var currentstudent: Student = listStudents[position]

        var nameContact = currentstudent.name
        var numberContact = currentstudent.age

        holder!!.mName.text = nameContact
        holder!!.age.text = numberContact

        holder.bind(currentstudent, listenerContact)
    }
    override fun getItemCount(): Int {
        return listStudents.size
    }


    fun addContacts(listStudent: List<Student>) {
        this.listStudents = listStudent
        notifyDataSetChanged()
    }


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mName = itemView.findViewById<TextView>(R.id.name)!!
        var age = itemView.findViewById<TextView>(R.id.age)!!

        fun bind(student: Student, listener: OnItemClickListener) {
            itemView.setOnClickListener {
                listener.onItemClick(student)
            }
        }

    }
}