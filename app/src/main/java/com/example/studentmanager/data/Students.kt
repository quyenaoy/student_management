package com.example.studentmanager.data

import com.example.studentmanager.model.Student
import android.database.sqlite.SQLiteDatabase
import com.example.studentmanager.adapter.StudentAdapter

object Students {
    lateinit var db: SQLiteDatabase

    val students: List<Student> = listOf(
        Student("Nguyễn Văn An", "SV001"),
        Student("Trần Thị Bảo", "SV002"),
        Student("Lê Hoàng Cường", "SV003"),
        Student("Phạm Thị Dung", "SV004"),
        Student("Đỗ Minh Đức", "SV005"),
        Student("Vũ Thị Hoa", "SV006"),
        Student("Hoàng Văn Hải", "SV007"),
        Student("Bùi Thị Hạnh", "SV008"),
        Student("Đinh Văn Hùng", "SV009"),
        Student("Nguyễn Thị Linh", "SV010"),
        Student("Phạm Văn Long", "SV011"),
        Student("Trần Thị Mai", "SV012"),
        Student("Lê Thị Ngọc", "SV013"),
        Student("Vũ Văn Nam", "SV014"),
        Student("Hoàng Thị Phương", "SV015"),
        Student("Đỗ Văn Quân", "SV016"),
        Student("Nguyễn Thị Thu", "SV017"),
        Student("Trần Văn Tài", "SV018"),
        Student("Phạm Thị Tuyết", "SV019"),
        Student("Lê Văn Vũ", "SV020")
    )

    val list: MutableList<Student> = mutableListOf()

    val adapter = StudentAdapter(list)
}