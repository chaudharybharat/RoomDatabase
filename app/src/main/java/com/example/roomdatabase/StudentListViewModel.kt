package com.example.roomdatabase

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.roomdatabase.db.DatabaseDB
import com.example.roomdatabase.db.Student

/**
 * Created by ThinkSoft on 21/12/2017.
 */
class StudentListViewModel(application: Application) : AndroidViewModel(application) {

    var listContact: LiveData<List<Student>>
    private val appDb: DatabaseDB

    init {
        appDb = DatabaseDB.getDatabase(this.getApplication())
        listContact = appDb.daoStudent().getAllStudentList()
    }

    fun getListStudent(): LiveData<List<Student>> {
        return listContact
    }

    fun addContact(student: Student) {
        addAsynTask(appDb).execute(student)
    }


    class addAsynTask(db: DatabaseDB) : AsyncTask<Student, Void, Void>() {
        private var contactDb = db
        override fun doInBackground(vararg params: Student): Void? {
            contactDb.daoStudent().insertStudent(params[0])
            return null
        }

    }

}