package com.faizdev.mykiosk.data


import com.faizdev.mykiosk.data.local.entity.Bookmark
import com.faizdev.mykiosk.data.source.remote.StokData
import com.rmaprojects.apirequeststate.RequestState
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.flow.Flow


interface MyKioskRepository {

    suspend fun register(
        username : String,
        email : String,
        password : String
    ) : Flow<RequestState<Boolean>>

    suspend fun login(
        email: String,
        password: String
    ) : Flow<RequestState<Boolean>>

    suspend fun unsubscribeChannel()

    suspend fun getAllStock(): Result<Flow<List<StokData>>>

    fun insertStock(stokData: StokData): Flow<RequestState<PostgrestResult>>

    fun updateStock(id: Int, name : String, stock: Int, desc: String): Flow<RequestState<StokData>>

    fun deleteStock(id: Int): Flow<RequestState<List<StokData>>>

    //Bookmark
    fun getBookmarkList(): Flow<List<Bookmark>>

    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun deleteBookmark(bookmark: Bookmark)
}