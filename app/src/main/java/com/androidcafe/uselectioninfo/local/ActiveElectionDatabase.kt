package com.androidcafe.uselectioninfo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidcafe.uselectioninfo.data.Election

@Database(entities = [Election::class], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class ActiveElectionDatabase: RoomDatabase() {

    abstract val dao: IElectionDao

    companion object {

        @Volatile
        private var INSTANCE: ActiveElectionDatabase? = null

        fun getInstance(context: Context): ActiveElectionDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ActiveElectionDatabase::class.java,
                        "active_election_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

    suspend fun insertAll(elections: List<Election>) = dao.insertAll(elections)
    fun getAll() = dao.getAll()
}