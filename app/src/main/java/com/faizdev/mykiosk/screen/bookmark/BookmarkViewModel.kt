package com.faizdev.mykiosk.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faizdev.mykiosk.data.MyKioskRepository
import com.faizdev.mykiosk.data.local.entity.Bookmark
import com.rmaprojects.apirequeststate.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: MyKioskRepository
) : ViewModel() {
    private val _bookmarkState = MutableStateFlow<RequestState<List<Bookmark>>>(RequestState.Idle)

    val bookmarkState = _bookmarkState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )

    fun getBookmark() {
        viewModelScope.launch {
            _bookmarkState.emit(RequestState.Idle)
            try {
                repository.getBookmarkList().collect{
                    _bookmarkState.emit(RequestState.Success(it))
                }
            }  catch (e : Exception) {
                _bookmarkState.emit(RequestState.Error(e.message.toString()))
            }

        }
    }




    fun deleteBookmark(bookmark: Bookmark) {
      viewModelScope.launch {
           repository.deleteBookmark(bookmark)
        }
    }

        init {
            getBookmark()
        }

}