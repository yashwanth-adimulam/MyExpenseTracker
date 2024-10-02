package com.example.myexpensetracker.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel : ViewModel() {
    protected val _navigationEvent =
        MutableSharedFlow<NavigationEvent>(replay = 0, extraBufferCapacity = 1)
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    abstract fun onEvent(event: UiEvent)
}

abstract class UiEvent