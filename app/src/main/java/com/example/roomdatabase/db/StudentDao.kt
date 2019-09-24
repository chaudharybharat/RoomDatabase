package com.example.roomdatabase.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface  StudentDao {

    @Query("select * from student")
    fun getAllStudentList() : LiveData<List<Student>>

    @Query("select * from student where id in (:student_id)")
    fun getStudentById(student_id :Int) :Student

    @Query("delete from student")
    fun deleteAllStudent()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student :Student)

    @Update()
    fun updateStudent(student: Student)

    @Delete()
    fun deleteStudent(student: Student)

}