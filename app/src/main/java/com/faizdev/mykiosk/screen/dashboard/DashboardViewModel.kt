package com.faizdev.mykiosk.screen.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faizdev.mykiosk.data.MyKioskRepository
import com.faizdev.mykiosk.data.local.entity.Bookmark
import com.faizdev.mykiosk.data.source.remote.StokData
import com.rmaprojects.apirequeststate.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor (
    private val repository: MyKioskRepository
): ViewModel(){

    fun insertBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.insertBookmark(bookmark)
        }
    }


    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()



    private val _getAllStock =
        MutableStateFlow<RequestState<List<StokData>>>(
            RequestState.Loading
        )


    val getAllStock =
        _getAllStock.asStateFlow().stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(500),
            RequestState.Loading
        )


    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun connectToRealtime(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllStock()
                .onSuccess { flow ->
                    flow.onEach {
                        _getAllStock.emit(RequestState.Success(it))
                    }.collect()
                }
                .onFailure {
                    _getAllStock.emit(RequestState.Error(it.message.toString()))
                }
        }
    }

    fun leaveRealtimeChannel() = viewModelScope.launch {
        repository.unsubscribeChannel()
    }


    fun insertStock(stokData: StokData){
        viewModelScope.launch {
            repository.insertStock(stokData).collectLatest {

            }

        }
    }

    fun updateStock(id : Int, nama : String, stok : Int, desc: String,){
        viewModelScope.launch {
            repository.updateStock(
                id,
                nama,
                stok,
                desc,

            ).collect {

                }
            }
        }




    fun deleteStock (id : Int){
        viewModelScope.launch {
            repository.deleteStock(id).collectLatest {
                if (it.isSuccess()){

                }
            }
        }
    }


}
