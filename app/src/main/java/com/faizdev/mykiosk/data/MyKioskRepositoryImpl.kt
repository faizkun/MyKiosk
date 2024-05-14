package com.faizdev.mykiosk.data


import android.util.Log
import com.faizdev.mykiosk.data.kotpref.Kotpref
import com.faizdev.mykiosk.data.local.BookmarkDao
import com.faizdev.mykiosk.data.local.entity.Bookmark
import com.faizdev.mykiosk.data.source.remote.StokData
import com.faizdev.mykiosk.data.source.remote.Users
import com.rmaprojects.apirequeststate.RequestState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.result.PostgrestResult
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresListDataFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.buildJsonObject
import org.slf4j.MDC.put
import javax.inject.Inject

class MyKioskRepositoryImpl @Inject constructor(
    private val client: SupabaseClient,
    private val bookmarkDao: BookmarkDao
) : MyKioskRepository {

    private val stockChannel = client.channel("myKiosk")

    override suspend fun getAllStock(): Result<Flow<List<StokData>>> {
        val data = stockChannel.postgresListDataFlow(
            schema = "public",
            table = "stock_data",
            primaryKey = StokData::id

        ).flowOn(Dispatchers.IO)

        stockChannel.subscribe()
        return Result.success(data)
    }


    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Flow<RequestState<Boolean>> = flow {
        emit(RequestState.Loading)
        try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = buildJsonObject {
                    put("username", username)
                }
            }
            val user = client.auth.currentUserOrNull()
            val publicUser = client.from("users")
                .select {
                    filter {
                        Users::id eq user?.id
                    }
                }.decodeSingle<Users>()
            Kotpref.apply {
                this.id = publicUser.id
                this.username = publicUser.username
            }
            emit(RequestState.Success(true))

        } catch (e: Exception) {
            emit(RequestState.Error(e.toString()))
            Log.d("REPOSITORY", e.toString())

        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Flow<RequestState<Boolean>> = flow {
        emit(RequestState.Loading)
        try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val user = client.auth.currentUserOrNull()
            val publicUser = client.from("users")
                .select {
                    filter {
                        Users::id eq user?.id
                    }
                }.decodeSingle<Users>()
            Kotpref.apply {
                this.id = publicUser.id
                this.username = publicUser.username
            }
            Log.d("ID USER", publicUser.id)
            emit(RequestState.Success(true))
        } catch (e: Exception) {
            emit(RequestState.Error(e.toString()))
        }
    }



    override suspend fun unsubscribeChannel() {
        stockChannel.unsubscribe()
        client.realtime.removeChannel(stockChannel)
    }


    override fun insertStock(stokData: StokData): Flow<RequestState<PostgrestResult>> {
        return flow {
            try {
                val result = client.postgrest["stock_data"].insert(stokData)
                emit(
                    RequestState.Success(
                        result
                    )
                )
            } catch (e: Exception) {
                emit(
                    RequestState.Error(e.message.toString())
                )

            }

        }
    }

    override fun updateStock(
        id: Int,
        name: String,
        stock: Int,
        desc: String

    ): Flow<RequestState<StokData>> {

        return flow {
            try {
                RequestState.Success(
                    client.from("stock_data").update(
                        update = {
                            set("name", name)
                            set("stock", stock)
                            set("description", desc)
                        }
                    ) {
                        filter {
                            StokData::id eq id
                        }

                    }
                )
            } catch (e: Exception) {
                RequestState.Error(e.message.toString())


            }
        }

    }

    override fun deleteStock(id: Int): Flow<RequestState<List<StokData>>> {
        return flow {
            emit(RequestState.Loading)
            try {
                emit(
                    RequestState.Success(
                        client.from("stock_data").delete {
                            filter {
                                eq("id", id)
                            }
                        }.decodeList<StokData>()
                    )
                )
            } catch (e: Exception) {
                RequestState.Error(e.message.toString())
            }
        }
    }

    override fun getBookmarkList(): Flow<List<Bookmark>> = flow {
        emitAll(bookmarkDao.getBookmarkList())
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        bookmarkDao.insertBookmark(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarkDao.deleteBookmark(bookmark)
    }


}

