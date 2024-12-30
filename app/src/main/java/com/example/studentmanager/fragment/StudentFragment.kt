package com.example.studentmanager.fragment

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanager.R
import com.example.studentmanager.adapter.StudentAdapter
import com.example.studentmanager.data.StudentDatabase
import com.example.studentmanager.data.Students
import com.example.studentmanager.model.Student
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentFragment: Fragment() {
    private val studentAdapter = Students.adapter
    private lateinit var db: StudentDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = StudentDatabase.getInstance(requireContext())

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_students)
        recyclerView.run {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            registerForContextMenu(this)
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            val students = withContext(Dispatchers.IO) {db.studentDao().getAll()}
            studentAdapter.updateStudent(students)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_menu_layout, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = StudentAdapter.selectedPos

        val builder = requireContext().let {
            AlertDialog.Builder(it)
                .setTitle("Xóa sinh viên")
                .setMessage("Bạn có muốn xóa sinh viên này?")
                .setNegativeButton("Hủy") { dialog, _ -> dialog.dismiss() }
        }

        val dialog: AlertDialog = builder.setPositiveButton("Xóa") { dialog, _ ->
            val pos: Int = position
            val student: Student? = if (pos != RecyclerView.NO_POSITION) Students.list[pos] else null
            if (pos != RecyclerView.NO_POSITION) {
                lifecycleScope.launch {
                    student.let {
                        db.studentDao().deleteStudent(student!!)
                        studentAdapter.removeStudent(pos)
                    }
                }
                dialog.dismiss()
                this.view?.let {
                    Snackbar.make(it, "Sinh viên đã được xóa", Snackbar.LENGTH_LONG)
                        .setAction("Hoàn tác") {
                            Students.list.add(pos, student!!)
                            studentAdapter.notifyItemInserted(pos)
                            lifecycleScope.launch {
                                db.studentDao().insertStudent(student)
                            }
                    }.show()
                }
            }
        }.create()

        return when (item.itemId) {
            R.id.edit_details -> {
                val bundle = bundleOf(
                    "name" to Students.list[position].studentName,
                    "id" to Students.list[position].studentId,
                    "position" to position
                )
                findNavController().navigate(R.id.edit_student_details, bundle)
                true
            }
            R.id.delete_details -> {
                dialog.show()
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }
}