package com.faizdev.mykiosk.di

import android.app.Application
import com.faizdev.mykiosk.data.MyKioskRepository
import com.faizdev.mykiosk.data.MyKioskRepositoryImpl
import com.faizdev.mykiosk.data.local.BookmarkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.channel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            "https://kdgitqeejrcgogtiqatk.supabase.co",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtkZ2l0cWVlanJjZ29ndGlxYXRrIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcxMTQyMjUyMywiZXhwIjoyMDI2OTk4NTIzfQ.RGbHX2R3RpcQ98quN32FLzyYWV_vguxFVNR5XjBdPWc"
        ) {
            install(Auth)
            install(Postgrest)
            install(Realtime)

        }
    }

    @Provides
    @Singleton
    fun provideBookmarkDatabase(
        app: Application
    ): BookmarkDatabase {
        return BookmarkDatabase.getInstance(app)
    }


    @Provides
    @Singleton
    fun provideRepository(
        client: SupabaseClient,
        bookmarkDatabase: BookmarkDatabase
    ): MyKioskRepository {
        return MyKioskRepositoryImpl(client, bookmarkDatabase.getBookmarkDao())
    }
}



