package com.ddmeng.dribbbleclient.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ddmeng.dribbbleclient.data.model.Shot
import com.ddmeng.dribbbleclient.data.model.User

@Database(entities = [User::class, Shot::class],
        version = 1)
abstract class DribbbleClientDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun shotDao(): ShotDao

    companion object {
        @Volatile
        private var instance: DribbbleClientDatabase? = null
        private const val DATABASE_NAME = "dribbble_client_database"

        fun getInstance(context: Context): DribbbleClientDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DribbbleClientDatabase {
            return Room.databaseBuilder(context.applicationContext,
                    DribbbleClientDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration() // TODO consider proper db migration once released
                    .build()
        }
    }
}