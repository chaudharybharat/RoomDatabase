package com.example.roomdatabase.db



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//note:database name don't name like database else you put Database class name you not get entities annotation
@Database(entities = [(Student::class)], version = 1, exportSchema = false)
abstract class DatabaseDB: RoomDatabase()  {

    companion object{
      private var INSTANT :DatabaseDB?=null
        fun getDatabase(context :Context) :DatabaseDB{
            if(INSTANT==null){
                INSTANT= Room.databaseBuilder(context.applicationContext,DatabaseDB::class.java,"database").allowMainThreadQueries().build()
            }
            return INSTANT as DatabaseDB
        }

    }
    abstract fun daoStudent() : StudentDao
}