package com.example.roomdatabase

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.dbexporterlibrary.ExportDbUtil
import com.android.dbexporterlibrary.ExporterListener
import com.example.roomdatabase.db.DatabaseDB
import com.example.roomdatabase.db.Student
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest.permission
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.*


class MainActivity : AppCompatActivity(), StudentAdapter.OnItemClickListener, ExporterListener {

    var type=""


    private var contactRecyclerView: RecyclerView? = null
    private var adaptor: StudentAdapter? = null

    private var viewModel: StudentListViewModel? = null
    private var exportDbUtil: ExportDbUtil? = null

    private var db: DatabaseDB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exportDbUtil = ExportDbUtil(this, "database", "roomdataDir", this)
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
    fun onExportDb(view : View){
        val store_read_permistion = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (store_read_permistion != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)
        } else {
            //Do your work
            type="Export"
            exportDbUtil!!.exportDb("/data/com.example.roomdatabase/databases/")

        }

    }
    fun onImportDb(view : View){
        val store_read_permistion = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        val store_write_permistion = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (store_read_permistion == PackageManager.PERMISSION_GRANTED) {


        if (store_write_permistion == PackageManager.PERMISSION_GRANTED) {
            type = "Import"
            if (exportDbUtil!!.isBackupExist("roomdataDir")) {
                exportDbUtil!!.importDBFile("/data/com.example.roomdatabase/databases/")
            } else {
                Toast.makeText(this, "no Backup", Toast.LENGTH_SHORT).show()

            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
            //Do your work
        }
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)

        }

    }

    override fun success(s: String) {
        Toast.makeText(this,"DB successfully ${type}",Toast.LENGTH_LONG).show()
        Log.e("test","==success full ===>"+s)
        if(type.equals("Import")){
            val allStudentList = db!!.daoStudent().getAllStudentList()
            allStudentList.observe(this, Observer { studentlist ->
                adaptor!!.addContacts(studentlist!!)
            })
        }
    }

    override fun fail(message: String, exception: String) {
        Log.e("test","==fail full ===>"+message+"exception=>"+exception)
        Toast.makeText(this,"DB fail ${type}",Toast.LENGTH_LONG).show()

    }

}
