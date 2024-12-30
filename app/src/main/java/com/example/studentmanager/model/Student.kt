package com.example.studentmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @ColumnInfo(name = "student_name") val studentName: String,
    @ColumnInfo(name = "student_id") val studentId: String,

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)