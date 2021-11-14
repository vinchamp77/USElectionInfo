package com.androidcafe.uselectioninfo.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidcafe.uselectioninfo.data.Election

@Database(entities = [Election::class], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class ElectionDatabase: RoomDatabase() {

    abstract val dao: IElectionDao

    companion object {

        @Volatile
        private var INSTANCE: ElectionDatabase? = null

        fun getInstance(context: Context): ElectionDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ElectionDatabase::class.java,
                        "election_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

    fun insertAll(elections: List<Election>) = dao.insertAll(elections)
    fun getAll(): LiveData<List<Election>> = dao.getAll()
}