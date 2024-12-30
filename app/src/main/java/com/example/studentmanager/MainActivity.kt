package com.example.studentmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.studentmanager.data.StudentDatabase
import com.example.studentmanager.data.Students
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var root: ConstraintLayout
    private lateinit var db: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        root = findViewById(R.id.main)
        db = StudentDatabase.getInstance(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                fetchData()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_option_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                findNavController(R.id.fragment_view).navigate(R.id.editFragment, bundleOf("name" to "Add"))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            db.studentDao().deleteAllStudents()
            db.close()
        }
    }

    private suspend fun fetchData() {
        Students.students.forEach{
            db.studentDao().insertOrUpdateStudent(it)
        }
        Students.list.clear()
        Students.list.addAll(db.studentDao().getAll())
    }
}