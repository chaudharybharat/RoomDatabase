package com.example.roomdatabase

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.roomdatabase.db.DatabaseDB
import com.example.roomdatabase.db.Student
import com.example.roomdatabase.db.StudentDao
import kotlinx.android.synthetic.main.activity_student_details.*

class StudentDetailsActivity : AppCompatActivity() {


    private var studentDao: StudentDao? = null
    private var viewModel: StudentListViewModel? = null

    private var currentContact: Int? = null
    private var student: Student? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        var db: DatabaseDB = DatabaseDB.getDatabase(this)

        studentDao = db.daoStudent()

        viewModel = ViewModelProviders.of(this).get(StudentListViewModel::class.java)
        currentContact = intent.getIntExtra("id", -1)
        if (currentContact != -1) {
            setTitle(R.string.edit_student_title)
            student = studentDao!!.getStudentById(currentContact!!)
            name_edit_text.setText(student!!.name)
            number_edit_text.setText(student!!.age)
        } else {
            setTitle(R.string.add_student_title)
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.done_item -> {
                if (currentContact == -1) {
                    saveContact()
                    Toast.makeText(this, getString(R.string.save_student), Toast.LENGTH_SHORT).show()
                } else {
                    updateContact()
                    Toast.makeText(this, getString(R.string.update_student), Toast.LENGTH_SHORT).show()
                }

                finish()
            }
            R.id.delete_item -> {
                deleteContact()
                Toast.makeText(this, getString(R.string.delete_student), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (currentContact == -1) {
            menu.findItem(R.id.delete_item).isVisible = false
        }
        return true
    }

    private fun saveContact() {
        var name = name_edit_text.text.toString()
        var age = number_edit_text.text.toString()
        var contact = Student(0, name, age)
        viewModel!!.addContact(contact)
    }

    private fun deleteContact() {
        studentDao!!.deleteStudent(student!!)
    }

    private fun updateContact() {
        var name= name_edit_text.text.toString()
        var age = number_edit_text.text.toString()
        var student = Student(student!!.id, name, age)
        studentDao!!.updateStudent(student)
    }
}
