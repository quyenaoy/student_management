package com.example.studentmanager.adapter

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.studentmanager.model.Student

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    suspend fun getAll(): List<Student>

    @Query("SELECT * FROM students WHERE student_id = :id")
    suspend fun getStudentById(id: String): Student?

    @Query("SELECT * FROM students WHERE student_name = :name")
    suspend fun getStudentByName(name: String): Student?

    @Insert
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student): Int

    @Delete
    suspend fun deleteStudent(student: Student): Int

    @Query("DELETE FROM students")
    suspend fun deleteAllStudents(): Int

    @Transaction
    suspend fun insertOrUpdateStudent(student: Student) {
        val id = insertStudent(student)
        if (id == -1L) {
            updateStudent(student)
        }
    }
}