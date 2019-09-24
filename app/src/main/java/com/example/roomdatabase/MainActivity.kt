package com.example.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.db.DatabaseDB
import com.example.roomdatabase.db.Student
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), StudentAdapter.OnItemClickListener {


    private var contactRecyclerView: RecyclerView? = null
    private var adaptor: StudentAdapter? = null

    private var viewModel: StudentListViewModel? = null

    private var db: DatabaseDB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DatabaseDB.getDatabase(this)

        contactRecyclerView = findViewById(R.id.recycler_view)
        adaptor = StudentAdapter(arrayListOf(), this)

        contactRecyclerView!!.layoutManager = LinearLayoutManager(this)
        contactRecyclerView!!.adapter = adaptor

        viewModel = ViewModelProviders.of(this).get(StudentListViewModel::class.java)

        viewModel!!.getListStudent().observe(this, Observer { studentlist ->
            adaptor!!.addContacts(studentlist!!)
        })
        fab.setOnClickListener {
            var intent = Intent(applicationContext, StudentDetailsActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onItemClick(student: Student) {
        var intent = Intent(applicationContext, StudentDetailsActivity::class.java)
        intent.putExtra("id", student.id)
        startActivity(intent)
    }
}
