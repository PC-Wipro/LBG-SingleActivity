package com.lbg.project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lbg.project.data.database.dao.FavouriteDao
import com.lbg.project.domain.entities.FavImageEntity
import com.lbg.project.utils.Constants.Companion.DATABASE_NAME

@Database(
    entities = [
        FavImageEntity::class
            ],
    version = 1,
    exportSchema = false)
abstract class LBGDatabase : RoomDatabase() {
    abstract fun favImageDao(): FavouriteDao
    companion object {
        @Volatile
        private var INSTANCE: LBGDatabase? = null

        fun getInstance(context: Context): LBGDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): LBGDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                LBGDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}