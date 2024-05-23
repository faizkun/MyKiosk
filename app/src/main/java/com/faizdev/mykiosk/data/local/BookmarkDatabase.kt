package com.faizdev.mykiosk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.faizdev.mykiosk.data.local.entity.Bookmark

@Database(version = 1, entities = [Bookmark::class], )
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun getBookmarkDao() : BookmarkDao
    companion object {
        @Volatile
        private var INSTANCE : BookmarkDatabase? = null
        fun getInstance(context: Context) : BookmarkDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    BookmarkDatabase::class.java,
                    "bookmark.db"
                ).fallbackToDestructiveMigration().build()
            }
        }
    }

}