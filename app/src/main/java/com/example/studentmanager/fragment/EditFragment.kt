package com.example.studentmanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanager.R
import com.example.studentmanager.data.StudentDatabase
import com.example.studentmanager.data.Students
import com.example.studentmanager.model.Student
import kotlinx.coroutines.launch

class EditFragment: Fragment() {
    private var name: String = ""
    private var id: String = ""
    private var pos: Int = -1

    private val studentAdapter = Students.adapter
    private lateinit var db: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name") ?: name
            id = it.getString("id") ?: id
            pos = it.getInt("pos")
        }
        db = StudentDatabase.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (name.isNotBlank()) {
            view.findViewById<EditText>(R.id.edit_student_name).setText(name)
        }

        if (id.isNotBlank()) {
            view.findViewById<EditText>(R.id.edit_student_id).setText(id)
        }

        view.findViewById<Button>(R.id.btn_cancel_in_edit_student).setOnClickListener{
            findNavController().popBackStack()
        }

        view.findViewById<Button>(R.id.btn_edit_in_edit_student).setOnClickListener{
            val newName = view.findViewById<EditText>(R.id.edit_student_name).text.toString()
            val newId = view.findViewById<EditText>(R.id.edit_student_id).text.toString()
            val newStudent = Student(studentName = newName, studentId = newId)
            if (pos != RecyclerView.NO_POSITION) {
                Students.list[pos] = newStudent
                studentAdapter.notifyItemChanged(pos)
                lifecycleScope.launch {
                    db.studentDao().updateStudent(newStudent)
                }
            }
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show()
        }
    }
}