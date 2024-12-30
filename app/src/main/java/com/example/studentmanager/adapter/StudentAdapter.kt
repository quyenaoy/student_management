package com.example.studentmanager.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanager.R
import com.example.studentmanager.model.Student

class StudentAdapter (
    private val students: MutableList<Student>
): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    companion object {
        var selectedPos: Int = -1
    }

    inner class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val studentName: TextView = itemView.findViewById(R.id.text_student_name)
        private val studentId: TextView = itemView.findViewById(R.id.text_student_id)

        init {
            itemView.setOnLongClickListener {
                selectedPos = adapterPosition
                false
            }
        }

        fun bind(student: Student) {
            studentName.text = student.studentName
            studentId.text = student.studentId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_student_item,
            parent, false
        )
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    fun removeStudent(position: Int) {
        if (position < 0 || position >= students.size)
            return
        students.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateStudent(students: List<Student>) {
        this.students.clear()
        this.students.addAll(students)
        notifyDataSetChanged()
    }
}